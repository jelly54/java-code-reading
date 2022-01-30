package com.jelly.basic.proxy.util;

import com.jelly.basic.proxy.base.Bus;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author zhangguodong
 * @since 2022/1/30 14:51
 */
public class JDKProxyUtils {

    public static void generateProxyClassFile(Class<?> clazz, String proxyName) {
        byte[] classFileBytes = ProxyGenerator.generateProxyClass(proxyName, clazz.getInterfaces());
        String paths = "./src/com/jelly/basic/proxy/util/";

        try (FileOutputStream fos = new FileOutputStream(paths + proxyName + ".class")) {
            fos.write(classFileBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JDKProxyUtils.generateProxyClassFile(Bus.class, "BusJDKProxy");
    }
}
