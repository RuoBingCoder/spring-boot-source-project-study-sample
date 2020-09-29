package com.sjl.custom.aop;

import com.sjl.custom.aop.service.CglibService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomAopSpringBootSampleApplicationTests {

  @Test
  void contextLoads() {


  }

  /*public int[] moveBack(int[] nums,int target){
    if (nums.length<=0 ||nums.length ==1){
      return nums;
    }
    int[] newNums = new int[nums.length];
    for (int i = 0; i < target; i++) {
      newNums[i] = nums[newNums.length-target+i];

    }
    for (int j = target-1; j < nums.length-target; j++) {
      newNums[j]=nums[j];
    }
  }*/

  public static void main(String[] args) {
   /* ProxyFactory cglibProxy = new ProxyFactory();
    CglibService service=new CglibService();
    CglibService proxy = cglibProxy.createJDKProxy(CglibService.class);
    proxy.testCglibService();*/


  }
}
