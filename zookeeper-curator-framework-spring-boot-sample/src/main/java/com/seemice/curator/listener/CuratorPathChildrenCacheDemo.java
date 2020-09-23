package com.seemice.curator.listener;

import com.seemice.curator.conntect.ZkConnect;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.zookeeper.CreateMode;

import java.util.Arrays;
import java.util.List;

/**
 * @author: JianLei
 * @date: 2020/8/30 1:18 下午
 * @description: <p>PathChildrenCache用于监听数据节点子节点的变化情况。当前版本总共提供了7个构造方法，
 * PathChildrenCache不会对二级子节点进行监听，只会对子节点进行监听。
 * 其中2个已经不建议使用了</p>
 */

public class CuratorPathChildrenCacheDemo {
  public static void main(String[] args) throws Exception {
      CuratorFramework client = ZkConnect.getInstance();
      String parentPath = "/p1";

      PathChildrenCache pathChildrenCache = new PathChildrenCache(client,parentPath,true);
      pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
      pathChildrenCache.getListenable().addListener((client1, event) -> System.out.println("事件类型："  + event.getType() + "；操作节点：" + event.getData().getPath()+"更新后的值是:"+ new String(event.getData().getData())));

      String path = "/p1/c1";
//      client.create().withMode(CreateMode.PERSISTENT).forPath(path,"127.0.0.1".getBytes());

      Thread.sleep(1000); // 此处需留意，如果没有线程睡眠则无法触发监听事件
      if (client.checkExists().forPath(path)!=null){
          List<String> ip = client.getChildren().forPath(path);
          if (!ip.contains("127.0.0.2")){
              client.setData().forPath(path,"127.0.0.2".getBytes());
          }
      }
//      client.create().withMode(CreateMode.PERSISTENT).forPath(path,"127.0.0.2".getBytes());

      Thread.sleep(1000); // 此处需留意，如果没有线程睡眠则无法触发监听事件
//      client.delete().forPath(path);
//
//      Thread.sleep(15000);

  }
  }
