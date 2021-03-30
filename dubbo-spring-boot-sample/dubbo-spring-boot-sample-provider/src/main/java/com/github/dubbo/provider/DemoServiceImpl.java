package com.github.dubbo.provider;

import com.alibaba.fastjson.JSONObject;
import com.github.dubbo.pojo.LayoutQueryParam;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.annotation.Service;
import com.github.dubbo.DemoService;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 演示服务impl
 *
 * @author shijianlei
 * @date 2021-03-22 18:47:25
 * export -> create connect start netty server!
 * @see org.apache.dubbo.registry.integration.RegistryProtocol#export(Invoker) 先注册服务
 * @see org.apache.dubbo.registry.integration.RegistryProtocol#doLocalExport(Invoker, URL)
 * @see org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol#export 导出第一个服务先创建Server,加入缓存,再有其他服务则reset,不会重新创建
 * @see org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol#openServer(URL)
 * @see org.apache.dubbo.remoting.transport.netty4.NettyServer
 */
@Service(version = "1.0.0")
public class DemoServiceImpl implements DemoService {
    private static final Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);

    @Override
    public String sayHello(String name) {
        logger.info("Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }

    @Override
    public String query(LayoutQueryParam queryParam) {
        logger.info("queryParam is:{}", JSONObject.toJSONString(queryParam));
        return JSONObject.toJSONString(queryParam);
    }

}
