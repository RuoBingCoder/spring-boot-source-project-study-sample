package com.github.chat.protocol;

import lombok.Data;

/**
 * @author jianlei.shi
 * @date 2021/3/14 6:09 下午
 * @description IMMessage
 */
@Data
public class IMMessage  {
    private String addr; ///地址
    private String cmd; //命令类型 (SYSTEM|LOGIN|LOGIN_OUT|CHAT|FLOWERS)
    private Long time; //发送时间
    private Integer online; //当前在线人数
    private String sender;
    private String receiver;
    private String content;
    private String terminal;

    public IMMessage(String cmd, Long time, Integer online, String content) {
        this.cmd =cmd;
        this.time = time;
        this.online=online;
        this.content = content;
    }

    public IMMessage(String cmd, Long time, String terminal, String sender) {
        this.cmd = cmd;
        this.time = time;
        this.terminal = terminal;
        this.sender = sender;
    }

    public IMMessage(String addr, String cmd, Long time, Integer online, String sender, String receiver, String content, String terminal) {
        this.addr = addr;
        this.cmd = cmd;
        this.time = time;
        this.online = online;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.terminal = terminal;
    }

    public IMMessage() {
    }
}
