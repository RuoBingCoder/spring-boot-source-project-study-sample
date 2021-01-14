package com.github.zk.conntect;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author: JianLei
 * @date: 2020/8/29 7:40 下午
 * @description:
 */
public class ZkConnect {

  public static final String ZK_ADDRES = "39.108.137.115:2181";
  public static final String NODE_PATH = "/curator-dubbo/router";
  private static CuratorFramework curator = null;

  public static synchronized CuratorFramework getInstance() {
    if (curator == null) {
      curator =
          CuratorFrameworkFactory.builder()
              .connectString(ZK_ADDRES)
              .sessionTimeoutMs(5000)
              .retryPolicy(new ExponentialBackoffRetry(1000, 3))
              .build();

      curator.start();
    }
    return curator;
  }

  public static void main(String[] args) throws Exception {
    CuratorFramework curator = getInstance();

    // 创建节点  若创建节点的父节点不存在会先创建父节点再创建子节点
    // CreateMode.EPHEMERAL 临时节点  CreateMode.PERSISTENT_WITH_TTL当断开连接自动被删除
    //    curator.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
    //            .forPath(NODE_PATH,ZK_ADDRES.getBytes());
    NodeCache nodeCache = new NodeCache(curator, NODE_PATH, false);
    nodeCache.start(true);
    nodeCache
        .getListenable()
        .addListener(
            () -> {
              ChildData currentData = nodeCache.getCurrentData();
              String data = currentData == null ? "" : new String(currentData.getData());
              System.out.println("=====>数据发生变化new Data 是:" + data);
            });

    curator.setData().forPath(NODE_PATH, "我是新数据zk".getBytes());
    Thread.sleep(1000);
    curator.delete().deletingChildrenIfNeeded().forPath(ZK_ADDRES);
    Thread.sleep(10000);
    nodeCache.close();
    curator.close();
  }
}
