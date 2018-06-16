package io.github.onlynight.exchange.utils;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public final class JarLoaderUtil {

    /**
     * URLClassLoader的addURL方法
     */
    private static Method addURLMethod = initAddMethod();
    private static URLClassLoader systemClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();

    /**
     * 初始化方法
     */
    private static Method initAddMethod() {
        try {
            Method add = URLClassLoader.class
                    .getDeclaredMethod("addURL", URL.class);
            add.setAccessible(true);
            return add;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 循环遍历目录，找出所有的JAR包
     */
    private static void loopFiles(File file, List<File> files) {
        if (file.isDirectory()) {
            File[] tmps = file.listFiles();
            if (tmps != null) {
                for (File tmp : tmps) {
                    loopFiles(tmp, files);
                }
            }
        } else {
            if (file.getAbsolutePath().endsWith(".jar") || file.getAbsolutePath().endsWith(".zip")) {
                files.add(file);
            }
        }
    }

    /**
     * <pre>
     * 加载JAR文件
     * </pre>
     *
     * @param file
     */
    public static void loadJarFile(File file) {
        try {
            addURLMethod.invoke(systemClassLoader, file.toURI().toURL());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <pre>
     * 从一个目录加载所有JAR文件
     * </pre>
     *
     * @param path
     */
    public static void loadJarPath(String path) {
        List<File> files = new ArrayList<>();
        File lib = new File(path);
        loopFiles(lib, files);
        for (File file : files) {
            loadJarFile(file);
        }
    }
}
