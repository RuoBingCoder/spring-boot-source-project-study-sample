package com.github.simple.core.context;

import java.util.EventObject;

/**
 * @author jianlei.shi
 * @date 2021/1/18 12:58 下午
 * @description: SimpleApplicationEvent
 */
public abstract class SimpleApplicationEvent extends EventObject {

    /** use serialVersionUID from Spring 1.2 for interoperability. */
    private static final long serialVersionUID = 7099057708183571937L;

    /** System time when the event happened. */
    private final long timestamp;


    /**
     * Create a new {@code ApplicationEvent}.
     * @param source the object on which the event initially occurred or with
     * which the event is associated (never {@code null})
     */
    public SimpleApplicationEvent(Object source) {
        super(source);
        this.timestamp = System.currentTimeMillis();
    }


    /**
     * Return the system time in milliseconds when the event occurred.
     */
    public final long getTimestamp() {
        return this.timestamp;
    }





}
