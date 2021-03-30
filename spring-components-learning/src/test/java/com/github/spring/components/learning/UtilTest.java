package com.github.spring.components.learning;

import com.github.spring.components.learning.lighthttp.annotation.LightHttpClient;
import com.github.spring.components.learning.lighthttp.service.LightHttpOperateService;
import com.github.utils.AnnotationUtils;
import org.springframework.core.annotation.AnnotationAttributes;

/**
 * @author jianlei.shi
 * @date 2021/3/8 3:44 下午
 * @description UtilTest
 */

public class UtilTest {
    public static void main(String[] args) {
        final LightHttpClient annotation = LightHttpOperateService.class.getAnnotation(LightHttpClient.class);
        final AnnotationAttributes attributes = AnnotationUtils.getAnnotationAttributes(annotation);
        final String baseUrl = attributes.getString("baseUrl");
        System.out.println(baseUrl);
    }
}
