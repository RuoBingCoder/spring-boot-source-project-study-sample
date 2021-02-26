package config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author jianlei.shi
 * @date 2021/2/21 12:35 下午
 * @description ThreadPoolPropertiesConfig
 */
@ConfigurationProperties(prefix = "simple.thread.pool")
@Setter
@Getter
public class ThreadPoolPropertiesConfig {


    //    @Value("${simple.thread.pool.corePoolSize}")
    private  Integer corePoolSize;

    //    @Value("${simple.thread.pool.maxPoolSize}")
    private  Integer maxPoolSize;

    //    @Value("${simple.thread.pool.keepAliveTime}")
    private  Long keepAliveTime;

    private Long initialDelay;

    private Long period;

    private Long delay;


}
