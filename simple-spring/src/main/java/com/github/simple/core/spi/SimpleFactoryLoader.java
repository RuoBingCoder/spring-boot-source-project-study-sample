package com.github.simple.core.spi;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * @author jianlei.shi
 * @date 2021/3/5 1:48 下午
 * @description SPI机制实现自动配置
 */
@Slf4j
public class SimpleFactoryLoader {

    private static final String DIR = "META-INF/simple-ioc.factories";
    private static final String AUTO_CONFIGURATION_PREFIX = "com.github.simple.ioc.autoconfigure.EnableAutoConfiguration";
    private final ClassLoader classLoader;
    private static final ClassLoader DEFAULT_CLASS_LOADER = SimpleFactoryLoader.class.getClassLoader();

    public SimpleFactoryLoader() {
        this(ClassLoader.getSystemClassLoader());
    }

    public SimpleFactoryLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    private ClassLoader getClassLoader() {
        return this.getClass().getClassLoader();
    }

    public Set<Class<?>> loadClasses() {
        Set<Class<?>> classSet = new HashSet<>();
        try {
            final Enumeration<URL> urls = classLoader.getResources(DIR);
            while (urls.hasMoreElements()) {
                final URL url = urls.nextElement();
                loadDirectory(url, classSet);
            }
        } catch (Exception e) {
            log.error("loadClasses error ,msg:", e);
            throw new RuntimeException("loadClasses error msg: 【 " + e.getMessage() + "】");
        }
        return classSet;
    }

    private void loadDirectory(URL url, Set<Class<?>> classSet) {
        try {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.length() > 0 && line.startsWith(AUTO_CONFIGURATION_PREFIX)) {
                        try {
                            String name;
                            int i = line.indexOf('=');
                            if (i > 0) {
                                name = line.substring(0, i).trim();
                                line = line.substring(i + 1).trim();
                                if (line.contains(",")) {
                                    final String[] lines = line.split(",");
                                    for (String s : lines) {
                                        Class<?> aClass = Class.forName(s.trim(), true, getClassLoader());
                                        classSet.add(aClass);
                                    }
                                    return;
                                }
                                if (name.length() > 0 && line.length() > 0) {
                                    Class<?> aClass = Class.forName(line, true, getClassLoader());
                                    classSet.add(aClass);
                                    return;
                                }
                            }

                        } catch (Throwable t) {
                            throw new IllegalStateException("");
                        }
                    }


                }
            } catch (Exception e) {
                log.error("reader line error ,msg:", e);
                throw new RuntimeException("reader line  error msg: 【 " + e.getMessage() + "】");

            }
        } catch (Exception e) {
            log.error("loadDirectory error msg ,msg:", e);
            throw new RuntimeException("loadDirectory error msg: 【 " + e.getMessage() + "】");

        }
    }

    public static void main(String[] args) {
        SimpleFactoryLoader loader = new SimpleFactoryLoader();
        final Set<Class<?>> classSet = loader.loadClasses();
        classSet.forEach(s -> System.out.println(s.getName()));
        System.out.println(ClassLoader.getSystemClassLoader());
    }
}
