package com.sjl.custom.aop.service.impl;

import com.sjl.custom.aop.service.FileService;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author: JianLei
 * @date: 2020/11/24 下午7:13
 * @description: FileServiceImpl
 */

@Component("com.sjl.custom.aop.service.impl.FileServiceImpl")
public class FileServiceImpl implements FileService {
    @Override
    public void readFile(File file) {
        System.out.println("==>>"+file.getAbsolutePath());
    }
}

