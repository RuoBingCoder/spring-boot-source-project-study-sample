package com.github.chat.enums;

import lombok.Getter;

/**
 * @author jianlei.shi
 * @date: 2021/3/14 6:04 下午
 * @description: IMP
 */
@Getter
public enum IMP {
    SYSTEM("SYSTEM"),
    LOGIN("LOGIN"),
    LOGIN_OUT("LOGIN_OUT"),
    CHAT("CHAT"),
    FLOWERS("FLOWERS")
    ;


    private final String name;

    IMP(String name) {
        this.name = name;
    }

    public static  boolean isImpl(String content){
        return content.matches("^\\[(SYSTEM|LOGIN|LOGIN_OUT|CHAT|FLOWERS)\\]");


    }
}
