package com.sjl.spring.components.transaction.custom.handle;

import com.sjl.spring.components.transaction.custom.annotation.EasyTransactional;
import com.sjl.spring.components.transaction.custom.annotation.EasyRuleBaseTransactionAttributes;
import com.sjl.spring.components.transaction.custom.suuport.TransactionAdviserSupport;
import com.sjl.spring.components.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: JianLei
 * @date: 2020/11/8 11:50 上午
 * @description: 事务方法处理类
 */
@Slf4j
public class MethodInvocation extends TransactionAdviserSupport implements Invocation {
    private final Method method;
    private final Object[] args;

    public MethodInvocation(Method method, Object[] args) {
        this.method = method;
        this.args = args;
    }

    @Override
    public Object proceed() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Class<?> proxyClass = method.getDeclaringClass();
        Object bean = SpringUtil.getBeanByType(proxyClass);
        Method declaredMethod = bean.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
        if (isTransactionMethod(declaredMethod)) {
            EasyRuleBaseTransactionAttributes transactionAttributes = createTransactionAttributes(declaredMethod);
            //处理事务
            DataSourceTransactionManager manager = null;
            DefaultTransactionDefinition transactionDefinition;
            TransactionStatus status = null;
            try {
                log.info("开始处理事务获取DataSourceTransactionManager");
                manager = SpringUtil.getBeanByType(DataSourceTransactionManager.class);
                log.info("开始处理事务获取DataSourceTransactionManager,结果是:{}", manager.toString());
                transactionDefinition = new DefaultTransactionDefinition(transactionAttributes.getPropagate());
                status = manager.getTransaction(transactionDefinition);
                Object result = method.invoke(args);
                super.commitAfterTransaction(manager, status);
                log.info("事务提交完毕!");
                return result;
            } catch (Throwable e) {
                log.error("提交事务异常开始回滚!", e);
                assert manager != null;
                assert false;
                if (super.rollbackOn(e)) {
                    super.rollbackAfterTransaction(manager, status, e, null);
                } else {
                    super.rollbackAfterTransaction(manager, status, e, transactionAttributes);
                }
                throw new RuntimeException("提交事务异常!");
            }

        } else {
            return method.invoke(args);
        }
    }

    private EasyRuleBaseTransactionAttributes createTransactionAttributes(Method method) {
        method.setAccessible(true);
        return EasyRuleBaseTransactionAttributes.EasyRuleBaseTransactionAttributesBuilder
                .anEasyRuleBaseTransactionAttributes().withPropagate(getTransactionAnnotationsInfo(method).propagate())
                .withRollbacks(getTransactionAnnotationsInfo(method).rollbackFor()).build();

    }

    private boolean isTransactionMethod(Method method) {
        EasyTransactional annotation = method.getAnnotation(EasyTransactional.class);
        return annotation != null;
    }

    private EasyTransactional getTransactionAnnotationsInfo(Method method) {
        return method.getAnnotation(EasyTransactional.class);
    }
}
