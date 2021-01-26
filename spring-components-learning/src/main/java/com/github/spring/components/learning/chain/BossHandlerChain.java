package com.github.spring.components.learning.chain;

import java.util.Random;

/**
 * @author jianlei.shi
 * @date 2020/12/30 5:43 下午
 * @description BossHandlerChain
 */

public class BossHandlerChain extends AbsHandlerChain{
    protected BossHandlerChain(String name) {
        super(name);
    }

    @Override
    protected Boolean handlerReq(LeaveRequest leaveRequest) {
        boolean result = (new Random().nextInt(10)) > 3; // 随机数大于3则为批准，否则不批准
        String log = "经理<%s> 审批 <%s> 的请假申请，请假天数： <%d> ，审批结果：<%s> ";
        System.out.printf((log) + "%n", this.name, leaveRequest.getName(), leaveRequest.getNumOfDays(), result ? "批准" : "不批准");

        if (!result) {  // 不批准
            return false;
        } else if (leaveRequest.getNumOfDays() < 7) { // 批准且天数小于7
            return true;
        }
        return handlerChain.handlerReq(leaveRequest);
    }
}
