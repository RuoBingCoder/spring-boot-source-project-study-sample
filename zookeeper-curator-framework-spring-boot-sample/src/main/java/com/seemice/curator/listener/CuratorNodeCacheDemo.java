package com.seemice.curator.listener;

import com.seemice.curator.conntect.ZkConnect;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;

/**
 * @author: JianLei
 * @date: 2020/8/30 1:12 下午
 * @description:
 */
public class CuratorNodeCacheDemo {
  public static void main(String[] args) throws Exception {
    CuratorFramework client = ZkConnect.getInstance();
    String path = "/p1";
    final NodeCache nodeCache = new NodeCache(client, path);
    nodeCache.start();
    nodeCache
        .getListenable()
        .addListener(
            () -> {
              System.out.println("-----------------begin--------------------");
              System.out.println("监听事件触发");
              System.out.println("重新获得节点内容为：" + new String(nodeCache.getCurrentData().getData()));
              System.out.println("-----------------end--------------------");
            });
    client.setData().forPath(path, "456".getBytes());
    Thread.sleep(5000);
    client.setData().forPath(path, "789".getBytes());
    Thread.sleep(5000);
    client.setData().forPath(path, "123".getBytes());
    Thread.sleep(5000);
    client.setData().forPath(path, "222".getBytes());
    Thread.sleep(5000);
    client.setData().forPath(path, "333".getBytes());
    Thread.sleep(5000);
    client.setData().forPath(path, "444".getBytes());
    Thread.sleep(150000);
  }
}
