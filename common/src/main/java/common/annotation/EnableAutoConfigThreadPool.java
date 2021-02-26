package common.annotation;

import config.ThreadPoolConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author jianlei.shi
 * @date 2021/2/21 12:42 下午
 * @description: 配置了该注解调用静态方法
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ThreadPoolConfig.class)
public @interface EnableAutoConfigThreadPool {


}
