package com.sjl.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: JianLei
 * @date: 2020/9/2 5:27 下午
 * @description:
 */
public class RoundRobinTest {

  public static void main(String[] args) {
    String ip1 = "192.168.1.25";
    List<String> addr = new ArrayList<>();
    addr.add("192.168.1.23");
    addr.add("192.168.1.24");
    addr.add("192.168.1.25");
    addr.add("192.168.1.26");

    System.out.println(addr.get((ip1.hashCode() % addr.size())-1));
  }
}
