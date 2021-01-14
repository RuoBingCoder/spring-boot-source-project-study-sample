package com.github.spring.components.compile.support;

/**
 * @author jianlei.shi
 * @date 2021/1/13 3:45 下午
 * @description: Compiler
 */
public interface Compiler {

    /**
     * 编译
     *
     * @param code        代码
     * @param classLoader 类装入器
     * @return {@link Class<?>}
     */
    Class<?> compile(String code, ClassLoader classLoader);
}
