package com.github.simple.core.resource;

import com.github.simple.core.constant.SimpleIOCConstant;
import com.github.simple.core.exception.SimpleIOCBaseException;
import com.github.simple.core.utils.ClassUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author: jianlei.shi
 * @date: 2020/12/16 7:15 下午
 * @description: SimpleClassPathResource
 */

public class SimpleClassPathResource implements SimpleResource {

    private final String fileName;
    private final InputStream inputStream;
    /**
     * 文件属性 yml | yaml | properties
     * default properties
     */
    private Boolean fileAttributes = false;

    public SimpleClassPathResource(String fileName) {
        this.fileName = fileName;
        this.inputStream = getInputStream(fileName);


    }

    private InputStream getInputStream(String fileName) {
        InputStream resourceAsStream = getResourceAsStream(fileName + SimpleIOCConstant.PROPERTIES_SUFFIX);
        if (resourceAsStream == null) {
            InputStream is = getResourceAsStream(fileName + SimpleIOCConstant.YAML_SUFFIX) == null ? getResourceAsStream(fileName + SimpleIOCConstant.YML_SUFFIX) : getResourceAsStream(fileName + SimpleIOCConstant.YAML_SUFFIX);
            if (is == null) {
                throw new SimpleIOCBaseException("application  file not such!");
            }
            fileAttributes = true;
            return is;

        }
        return resourceAsStream;

    }

    private InputStream getResourceAsStream(String fullName) {
        return ClassUtils.getDefaultClassLoader().getResourceAsStream(fullName);
    }


    @Override
    public String getFilename() {
        return fileName;
    }

    @Override
    public Boolean fileAttribute() {
        return fileAttributes;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return inputStream;
    }


}
