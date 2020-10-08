package com.sjl.tomcat.core.ioc.context;

import cn.hutool.core.lang.Assert;
import com.sjl.tomcat.core.exception.EasyTomcatException;
import com.sjl.tomcat.core.ioc.factory.EasyBeanFactory;
import com.sjl.tomcat.core.utils.ClassScannerUtil;
import com.sjl.tomcat.core.utils.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: JianLei
 * @date: 2020/10/7 9:32 下午
 * @description: ioc容器
 */
@Slf4j
public class EasyApplicationContext extends AbsEasyApplicationContext implements EasyBeanFactory {

  private static final Map<String, Object> beanFactory = new ConcurrentHashMap<>();
  private final String scannerPackageName;


  public EasyApplicationContext(String scannerPackageName) throws IllegalAccessException, InstantiationException {
    this.scannerPackageName = scannerPackageName;
    initContext();
  }

  @Override
  public Object getBean(String beanName) throws Exception {
    return beanFactory.get(beanName);
  }

  @Override
  public Object getBean(Class<?> beanClass) throws Exception {
    if (beanClass == null) {
      throw new EasyTomcatException("class not null!");
    }
    String className = ClassScannerUtil.caseFirstClassName(beanClass.getSimpleName());
    try {
      return beanFactory.get(className);
    } catch (Exception e) {
      log.error("factory获取bean 出现异常" , e);
    }
    return null;
  }

  public void initContext() throws InstantiationException, IllegalAccessException {
    log.info("开始扫描包包路径为:{}" ,scannerPackageName);
    // 扫描包
    Assert.notNull(scannerPackageName,"扫描包路径不能为空!");
    ClassScannerUtil.scan(scannerPackageName, beanFactory);
    System.out.println(
        "                                                             ,----,                                                                                                          \n"
            + "                                                           ,/   .`|                                                                                                          \n"
            + "    ,---,.                                               ,`   .'  :                  ____                          ___                ,---,.                         ___     \n"
            + "  ,'  .' |                                             ;    ;     /                ,'  , `.                      ,--.'|_            ,'  .'  \\                      ,--.'|_   \n"
            + ",---.'   |                                           .'___,/    ,'  ,---.       ,-+-,.' _ |                      |  | :,'         ,---.' .' |   ,---.     ,---.    |  | :,'  \n"
            + "|   |   .'               .--.--.                     |    :     |  '   ,'\\   ,-+-. ;   , ||                      :  : ' :         |   |  |: |  '   ,'\\   '   ,'\\   :  : ' :  \n"
            + ":   :  |-,   ,--.--.    /  /    '       .--,         ;    |.';  ; /   /   | ,--.'|'   |  || ,---.     ,--.--.  .;__,'  /          :   :  :  / /   /   | /   /   |.;__,'  /   \n"
            + ":   |  ;/|  /       \\  |  :  /`./     /_ ./|         `----'  |  |.   ; ,. :|   |  ,', |  |,/     \\   /       \\ |  |   |           :   |    ; .   ; ,. :.   ; ,. :|  |   |    \n"
            + "|   :   .' .--.  .-. | |  :  ;_    , ' , ' :             '   :  ;'   | |: :|   | /  | |--'/    / '  .--.  .-. |:__,'| :           |   :     \\'   | |: :'   | |: ::__,'| :    \n"
            + "|   |  |-,  \\__\\/: . .  \\  \\    `./___/ \\: |             |   |  ''   | .; :|   : |  | ,  .    ' /    \\__\\/: . .  '  : |__         |   |   . |'   | .; :'   | .; :  '  : |__  \n"
            + "'   :  ;/|  ,\" .--.; |   `----.   \\.  \\  ' |             '   :  ||   :    ||   : |  |/   '   ; :__   ,\" .--.; |  |  | '.'|        '   :  '; ||   :    ||   :    |  |  | '.'| \n"
            + "|   |    \\ /  /  ,.  |  /  /`--'  / \\  ;   :             ;   |.'  \\   \\  / |   | |`-'    '   | '.'| /  /  ,.  |  ;  :    ;        |   |  | ;  \\   \\  /  \\   \\  /   ;  :    ; \n"
            + "|   :   .';  :   .'   \\'--'.     /   \\  \\  ;             '---'     `----'  |   ;/        |   :    :;  :   .'   \\ |  ,   /         |   :   /    `----'    `----'    |  ,   /  \n"
            + "|   | ,'  |  ,     .-./  `--'---'     :  \\  \\                              '---'          \\   \\  / |  ,     .-./  ---`-'          |   | ,'                          ---`-'   \n"
            + "`----'     `--`---'                    \\  ' ;                                              `----'   `--`---'                      `----'                                     \n"
            + "                                        `--`                                                                                                                                 \n");
    log.info("===================easy ioc 容器初始化完成! 容器bean size is:{}=======================",beanFactory.size());
    // mvc

  }

  public Map<String, Object> getBeanFactory() {
    return beanFactory;
  }
}
