package com.github.custom.spring.xml.beans;

/**
 * @author: JianLei
 * @date: 2020/9/10 3:30 下午
 * @description:
 */

public class CustomBean {

  private String id;
  private String name;
  private String className;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  @Override
  public String toString() {
    return "CustomBean{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", className='" + className + '\'' +
            '}';
  }
}
