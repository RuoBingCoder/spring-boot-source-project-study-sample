package com.seemice.curator.listener;

import com.seemice.curator.conntect.ZkConnect;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;

/**
 * @author: JianLei
 * @date: 2020/8/30 12:55 下午
 * @description:
 * <p>CuratorListener监听，此监听主要针对background通知和错误通知
 * 。使用此监听器之后，调用inBackground方法会异步获得监听，而对于节点的创建或修改则不会触发监听事件
 * </p>
 */

public class CuratorListenerDemo {
  public static void main(String[] args) {
      CuratorFramework client = ZkConnect.getInstance();
      String path = "/p1";
      try {
          org.apache.curator.framework.api.CuratorListener listener = (client1, event) -> System.out.println("监听事件触发，event内容为：" + event);
          client.getCuratorListenable().addListener(listener);
          // 异步获取节点数据
          client.getData().inBackground().forPath(path);
          // 变更节点内容
          client.setData().forPath(path,"123".getBytes());

          Thread.sleep(Integer.MAX_VALUE);
      } catch (Exception e) {
          e.printStackTrace();
          client.close();
      } finally {
          client.close();
      }

  }
}
