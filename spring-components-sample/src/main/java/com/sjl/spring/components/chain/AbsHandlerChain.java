package com.sjl.spring.components.chain;

import lombok.Data;

/**
 * @author jianlei.shi
 * @date 2020/12/30 5:41 下午
 * @description HandlerChain
 */
@Data
public abstract class AbsHandlerChain {

    protected String name;
    protected AbsHandlerChain handlerChain;

    protected AbsHandlerChain(String name) {
        this.name = name;
    }

    protected  abstract Boolean handlerReq(LeaveRequest leaveRequest);
}
