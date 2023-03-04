package com.qypone.demo.classloader;

import java.lang.reflect.Field;
import java.net.URLClassLoader;
import java.util.ArrayList;

/**
 * 不适用与jdk11
 */
@Deprecated
public class JvmClassLoaderPath {
  public static void main(String[] args) {

    ClassLoader classLoader = JvmClassLoaderPath.class.getClassLoader().getParent();
    print("扩展类加载器",classLoader);

    ClassLoader classLoader2 = JvmClassLoaderPath.class.getClassLoader();
    print("应用类加载器",classLoader2);
  }

  private static void print(String name, ClassLoader classLoader) {
    if (classLoader == null) {
      System.out.println(name + "classloader is null");
    } else {
      System.out.println(name + "classloader:" + classLoader.toString());
      printUrl(classLoader);
    }
  }

  private static void printUrl(ClassLoader classLoader) {
    Object ucp  = insightField(classLoader, "ucp");
    Object path = insightField(ucp, "path");
    ArrayList ps = (ArrayList) path;
    for (Object p : ps) {
      System.out.println("==>"+p.toString());
    }
  }

  private static Object insightField(Object obj, String fieldName) {
    Field field = null;
    try {
      if (obj instanceof URLClassLoader) {
        field = URLClassLoader.class.getDeclaredField(fieldName);
      } else {
        field = obj.getClass().getDeclaredField(fieldName);
      }
      field.setAccessible(true);
      return field.get(obj);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
