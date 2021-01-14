package com.github.spi.test;

/**
 * @author: JianLei
 * @date: 2020/8/26 8:01 下午
 * @description:
 */
public class Hello {

  // abcba
  public static boolean isASymmetricString(String str) {
    int j = str.length() - 1;
    int i = 0;
    while (i <= j) {
      if (str.charAt(i) == str.charAt(j)) {
        --j;
        i++;
      } else {
        return false;
      }
    }
    return true;
  }

  public static void main(String[] args) {
    System.out.println(isASymmetricString("abcba"));
  }
}
