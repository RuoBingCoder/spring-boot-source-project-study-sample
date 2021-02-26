package common.annotation;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * @author jianlei.shi
 * @date 2021/2/22 11:03 上午
 * @description SourceClass
 */

public class SourceClass {

    private final Set<Annotation> annotationsMetaAnnotations = new HashSet<>();

    private Class<?> mainClass;


}
