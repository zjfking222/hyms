package com.hy.utils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 反射机制提供的Model转化为DTO的工具类
 *
 * 需要控制DTO类与实体类中参数名与类型相同
 */

public class DTOUtil {
    private static Object populate(Object src, Object target) {
        Method[] srcMethods = src.getClass().getMethods();
        Method[] targetMethods = target.getClass().getMethods();
        for (Method m : srcMethods) {
            String srcName = m.getName();
            if (srcName.startsWith("get")) {
                try {
                    Object result = m.invoke(src);
                    for (Method mm : targetMethods) {
                        String targetName = mm.getName();
                        if (targetName.startsWith("set") && targetName.substring(3, targetName.length())
                                .equals(srcName.substring(3, srcName.length()))) {
                            mm.invoke(target, result);
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
        return target;
    }
    /**
     * 反射机制提供的Model转化为DTO方法
     *
     * @param src 来源列表
     * @param target 目标列表
     * @param targetClass 目标类
     */
    public static <S, T> List<T> populateList(List<S> src, List<T> target, Class<?> targetClass) {
        for (int i = 0; i < src.size(); i++) {
            try {
                Object object = targetClass.newInstance();
                target.add((T) object);
                populate(src.get(i), object);
            } catch (Exception e) {
                continue;
            }
        }
        return target;
    }
}
