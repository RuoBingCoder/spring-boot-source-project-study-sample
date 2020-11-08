package com.sjl.zk.listener;

import com.sjl.zk.conntect.ZkConnect;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.Watcher;

/**
 * @author: JianLei
 * @date: 2020/8/30 12:57 下午
 * @description:
 * <p>利用Watcher来对节点进行监听操作，但此监听操作只能监听一次，与原生API并无太大差异。
 * 如有典型业务场景需要使用可考虑，但一般情况不推荐使用</p>
 */

public class CuratorUsingWatch {

  public static void main(String[] args) {
      CuratorFramework client = ZkConnect.getInstance();
      String path = "/p1";

      try {
          byte[] content = client.getData().usingWatcher((Watcher) watchedEvent -> System.out.println("监听器watchedEvent：" + watchedEvent)).forPath(path);

          System.out.println("监听节点内容：" + new String(content));

          // 第一次变更节点数据
          client.setData().forPath(path,"new content".getBytes());

          // 第二次变更节点数据
          client.setData().forPath(path,"second content".getBytes());

          Thread.sleep(Integer.MAX_VALUE);
      } catch (Exception e) {
          e.printStackTrace();
          client.close();
      } finally {
          client.close();
      }

  }
  }
