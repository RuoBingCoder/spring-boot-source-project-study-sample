package com.github.enable.spi;

import com.github.enable.service.SpiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: JianLei
 * @date: 2020/9/16 2:11 下午
 * @description: spi扩展类加载
 */
@Slf4j
public class ExtensionLoader<T> {
  private static final ConcurrentMap<Class<?>, ExtensionLoader<?>> EXTENSION_LOADERS =
      new ConcurrentHashMap();
  private static final Map<Class<?>, Object> EXTENSION_INSTANCES = new ConcurrentHashMap<>();
  private static final String SERVICES_DIRECTORY = "META-INF/rocket/";
  private final Holder<Map<String, Class<?>>> cachedClasses = new Holder();
  private final Class<?> type;
  private final ConcurrentMap<String, Holder<Object>> cachedInstances = new ConcurrentHashMap<>();
  public <T> ExtensionLoader(Class<T> type) {
    this.type = type;
  }

  public static <T> ExtensionLoader getExtensionLoader(Class<T> type) {
    if (type == null) {
      throw new IllegalArgumentException("Extension type == null");
    } else if (!type.isInterface()) {
      throw new IllegalArgumentException("Extension type(" + type + ") is not interface!");
    } else if (!withExtensionAnnotation(type)) {
      throw new IllegalArgumentException(
          "Extension type(" + type + ") is not extension, because WITHOUT @");
    } else {
      ExtensionLoader loader = EXTENSION_LOADERS.get(type);
      if (loader == null) {
        EXTENSION_LOADERS.putIfAbsent(type, new ExtensionLoader(type));
        loader = EXTENSION_LOADERS.get(type);
      }
      return loader;
    }
  }

  private static <T> boolean withExtensionAnnotation(Class<T> type) {
    return type.isAnnotationPresent(SPI.class);
  }

  public T getExtension(String name) {
    if (StringUtils.isEmpty(name)) {
      throw new IllegalArgumentException("Extension name == null");
    }

     Holder<Object> holder = getOrCreateHolder(name);
    Object instance = holder.get();
    if (instance == null) {
      synchronized (holder) {
        instance = holder.get();
        if (instance == null) {
          instance = createExtension(name);
          holder.set(instance);
        }
      }
    }
    return (T) instance;
  }
  private Holder<Object> getOrCreateHolder(String name) {
    Holder<Object> holder = cachedInstances.get(name);
    if (holder == null) {
      cachedInstances.putIfAbsent(name, new Holder<>());
      holder = cachedInstances.get(name);
    }
    return holder;
  }

  private T createExtension(String name) {
    Class<?> clazz = getExtensionClasses().get(name);
    if (clazz == null) {
      throw new NullPointerException(name);
    }
    try {
      T instance = (T) EXTENSION_INSTANCES.get(clazz);
      if (instance == null) {
        EXTENSION_INSTANCES.putIfAbsent(clazz, clazz.newInstance());
        return  (T) EXTENSION_INSTANCES.get(clazz);

      }

    } catch (Exception e) {
      log.error("createExtension exception!");
    }
    return null;
    }
    private Map<String, Class<?>> getExtensionClasses() {
      Map<String, Class<?>> classes = cachedClasses.get();
      if (classes == null) {
        synchronized (cachedClasses) {
          classes = cachedClasses.get();
          if (classes == null) {
            classes = new HashMap<>();
            loadDirectory(classes,SERVICES_DIRECTORY,this.type.getName());
            cachedClasses.set(classes);
          }
        }
      }
      return classes;
    }



  private ClassLoader findClassLoader() {
    return ExtensionLoader.class.getClassLoader();
  }



    private void loadDirectory(Map<String, Class<?>> extensionClasses, String dir, String type) {
      String fileName = dir + type;
      try {
        Enumeration<java.net.URL> urls = null;
        ClassLoader classLoader = findClassLoader();
        if (classLoader != null) {
          urls = classLoader.getResources(fileName);
        } else {
          urls = ClassLoader.getSystemResources(fileName);
        }

        if (urls != null) {
          while (urls.hasMoreElements()) {
            java.net.URL resourceURL = urls.nextElement();
            loadResource(extensionClasses, classLoader, resourceURL);
          }
        }
      } catch (Throwable t) {
        log.error("加载扩展类接口出现异常 class: " +
                type + ", 文件是 : " + fileName + ").", t);
      }
    }
  private void loadResource(Map<String, Class<?>> extensionClasses, ClassLoader classLoader,
                            java.net.URL resourceURL) {
    try {
      try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceURL.openStream(), StandardCharsets.UTF_8))) {
        String line;
        while ((line = reader.readLine()) != null) {
          final int ci = line.indexOf('#');
          if (ci >= 0) {
            line = line.substring(0, ci);
          }
          line = line.trim();
          if (line.length() > 0) {
            try {
              String name = null;
              int i = line.indexOf('=');
              if (i > 0) {
                name = line.substring(0, i).trim();
                line = line.substring(i + 1).trim();
              }
              if (name.length() > 0 &&line.length()>0) {
                Class<?> aClass = Class.forName(line,true,classLoader);
                log.info("loadResource load class interface is:{}",aClass.getName());
                extensionClasses.put(name,aClass);


              }
            } catch (Throwable t) {
              throw  new IllegalStateException("加载扩展类接口出现异常 (interface: " + type + ", class line: " + line + ") in " + resourceURL + ", cause: " + t.getMessage(), t);
            }
          }
        }
      }
    } catch (Throwable t) {
      log.error("Exception occurred when loading extension class (interface: " +
              type + ", class file: " + resourceURL + ") in " + resourceURL, t);
    }
  }

  public static void main(String[] args) throws IllegalAccessException, InstantiationException {
    ExtensionLoader<SpiService> loader = ExtensionLoader.getExtensionLoader(SpiService.class);
    SpiService spiService = loader.getExtension("test1");
    System.out.println(spiService.testSpiService("哈哈哈"));
  }
}
