package com.github.spring.components.chain;

/**
 * @author jianlei.shi
 * @date 2020/12/30 5:49 下午
 * @description Main
 */

public class Main {

    public  int tc(int i) {
        try {
            if (i>=4){
                return i;
            };
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
        }
        return i;

    }

    public static void main(String[] args) {
      /*  BossHandlerChain bossHandlerChain = new BossHandlerChain("老板");
        ManagerHandlerChain managerHandlerChain=new ManagerHandlerChain("经理");

        bossHandlerChain.setHandlerChain(managerHandlerChain);

        Boolean handlerReq1 = bossHandlerChain.handlerReq(new LeaveRequest("小旋锋", 1));
        System.out.println("-->>handlerReq-1 is:"+handlerReq1);
        Boolean handlerReq2 = bossHandlerChain.handlerReq(new LeaveRequest("小旋锋", 8));
        System.out.println("-->>handlerReq-2 is:"+handlerReq2);
        Boolean handlerReq3 = bossHandlerChain.handlerReq(new LeaveRequest("小旋锋", 8));
        System.out.println("-->>handlerReq-3 is:"+handlerReq3);*/
        Main main = new Main();
        System.out.println(main.tc(5));

    }
}
