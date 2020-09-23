package com.seemice.curator.crud;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.util.LinkedList;
import java.util.List;

/**
 * @author: JianLei
 * @date: 2020/9/5 6:58 下午
 * @description: 使用Curator实现对zk的crud
 */
public class CuratorPathCrud {

  /**
   * 删除节点
   *
   * @param curatorFramework
   * @throws Exception
   */
  public static void deletePath(CuratorFramework curatorFramework) throws Exception {
    // 带版本号删除,防止并发
    Stat stat = new Stat();
    String version = new String(curatorFramework.getData().storingStatIn(stat).forPath(""));
    // 递归删除节点
    curatorFramework
        .delete()
        .deletingChildrenIfNeeded()
        .withVersion(stat.getAversion())
        .forPath("/test/create");
  }

  /**
   * 做权限处理
   *
   * @param curatorFramework
   */
  public static void createPathAuth(CuratorFramework curatorFramework) throws Exception {
    List<ACL> acls = new LinkedList<>();
    // scheme指的是用户例如: root/super/.... id:是指对用户密码 然后加密
    ACL acl =
        new ACL(
            ZooDefs.Perms.READ,
            new Id("digest", DigestAuthenticationProvider.generateDigest("admin:admin")));
    acls.add(acl);
    //修改权限
    curatorFramework.setACL().withACL(acls).forPath("");
    curatorFramework.create().withMode(CreateMode.PERSISTENT).withACL(acls).forPath("/auth/test");
  }
}
