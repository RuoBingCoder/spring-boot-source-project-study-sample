package com.github.spring.circular.dependency.service;

import com.github.spring.circular.dependency.pojo.LogModel;
import org.springframework.stereotype.Service;

/**
 * @author: JianLei
 * @date: 2020/11/27 上午10:49
 * @description: TxService
 */
@Service
public class TxService {

    public String insert(LogModel logModel){
        return logModel.toString();
    }
}
