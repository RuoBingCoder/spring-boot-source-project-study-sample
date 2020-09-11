package com.sjl.bean.life.cycle.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: JianLei
 * @date: 2020/9/11 4:45 下午
 * @description:
 */
@Component
@Data
public class Cat {

    @Autowired
    private Pig pig;

    public Pig getPig() {
        return pig;
    }
}
