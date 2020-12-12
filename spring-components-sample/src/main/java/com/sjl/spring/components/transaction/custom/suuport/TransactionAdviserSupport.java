package com.sjl.spring.components.transaction.custom.suuport;

import com.sjl.spring.components.transaction.custom.annotation.EasyRuleBaseTransactionAttributes;
import com.sjl.spring.components.transaction.custom.interceptor.EasyDefaultTransactionAttribute;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;

/**
 * @author: JianLei
 * @date: 2020/11/8 11:37 上午
 * @description: TransactionAdviserSupport
 */

public class TransactionAdviserSupport extends EasyDefaultTransactionAttribute {

    private Class<?> clazz;
    private Object object;
    private DataSourceTransactionManager transactionManager;
    private TransactionStatus status;

    public TransactionAdviserSupport(Class<?> clazz, Object object) {
        this.clazz = clazz;
        this.object = object;
    }

    public TransactionAdviserSupport() {
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public DataSourceTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public TransactionAdviserSupport(DataSourceTransactionManager transactionManager, TransactionStatus status) {
        this.transactionManager = transactionManager;
        this.status = status;
    }

    protected void commitAfterTransaction(DataSourceTransactionManager manager, TransactionStatus status) {
        if (manager != null && status != null) {
            manager.commit(status);
        }
    }

    protected void rollbackAfterTransaction(DataSourceTransactionManager manager, TransactionStatus status, Throwable ex, EasyRuleBaseTransactionAttributes ruleBaseTransactionAttributes) {
        if (ruleBaseTransactionAttributes != null && ruleBaseTransactionAttributes.getRollbacks().length > 0) {
            Class<? extends Throwable>[] exceptions = ruleBaseTransactionAttributes.getRollbacks();
            for (Class<? extends Throwable> exception : exceptions) {
                if (!ex.getClass().isAssignableFrom(exception)) {
                    throw new IllegalStateException("rollback exception class type error!" + exception.getName());
                }
                if (ex.getClass().getName().contains(exception.getName())) {
                    manager.rollback(status);
                }
            }
        }
        manager.rollback(status);

    }

}
