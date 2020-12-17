package com.github.simple.core.resource;

import com.github.simple.core.utils.ClassUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author: jianlei.shi
 * @date: 2020/12/16 7:15 下午
 * @description: SimpleClassPathResource
 */

public class SimpleClassPathResource implements SimpleResource{

    private final String fileName;
    private final InputStream inputStream;
    public SimpleClassPathResource(String fileName) {
        this.fileName = fileName;
        this.inputStream= ClassUtils.getDefaultClassLoader().getResourceAsStream(fileName);


    }

    @Override
    public String getFilename() {
        return fileName;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return inputStream;
    }
}
