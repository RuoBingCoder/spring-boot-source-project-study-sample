package com.github.spring.components.lightweight.test.sample;

/**
 * @author jianlei.shi
 * @date 2021/4/8 8:41 下午
 * @description MainTest
 */

public class MainTest {

    public static void main(String[] args) {
        /*for (int i=0;;++i) {
            System.out.println("i=>"+i);
        }*/
        //10->1010  1010
        //13->1101->0110 =6
        // & 0111
        int x = 10 | 13 >> 1;
        int y = 10 & 13;
        int z = ~4; //100 +1 取反
        int k = 13 >>> 1;
        System.out.println(x);
        System.out.println(y);
        System.out.println(z);
        System.out.println(k);

    }
}
