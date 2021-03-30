package com.github.dubbo.consumer;

import com.github.dubbo.DemoService;
import com.github.dubbo.HelloService;
import com.github.dubbo.pojo.LayoutQueryParam;
import com.github.dubbo.utils.DubboUtils;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class DubboConsumerBootstrap implements CommandLineRunner {


    /**
     * @see org.apache.dubbo.config.spring.ReferenceBean
     * 演示服务 最终注入的是<code>ReferenceBean extends ReferenceConfig</code> #init create proxy
     * <pre>Invoker</pre>
     * @see org.apache.dubbo.remoting.transport.netty4.NettyServerHandler#channelRead(ChannelHandlerContext, Object)
     * @see DubboProtocol#requestHandler callback
     * <refer>
     * @see DubboProtocol#refer(Class, URL)
     * @see DubboProtocol#protocolBindingRefer(Class, URL)
     * @see DubboProtocol#getClients(URL)
     * @see org.apache.dubbo.remoting.transport.netty4.NettyClient
     * </refer>
     */
    @Reference(version = "1.0.0")
    private DemoService demoService;

    @Reference(version = "1.0.0")
    private HelloService helloService;

    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerBootstrap.class).close();
    }
/*

    @Bean
    public ApplicationRunner runner() {
        return args -> logger.info(demoService.sayHello("Provider"));
    }
*/

    /**
     * 运行
     *
     * @param args arg游戏
     * @return
     * @author jianlei.shi
     * @date 2021-03-08 10:33:01
     */
    @Override
    public void run(String... args) throws Exception {
        final DemoService demoService = DubboUtils.invoke(DemoService.class, "1.0.0", 30000, "zookeeper://127.0.0.1:2181");
//        final String hello_rpc = demoService.sayHello("hello rpc");
//        log.info("RPC invoke res :{}",hello_rpc);

        LayoutQueryParam queryParam=new LayoutQueryParam();
        queryParam.setId(300L);
        queryParam.setAddress("beijing");
        queryParam.setName("张三");
        final String query = demoService.query(queryParam);
        log.info("##################query params is:{}",query);
    }


}
