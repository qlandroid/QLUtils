package com.ql.view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author ql
 *         创建时间:2017/11/28
 *         描述:
 */

public class LayoutUtils {

    public static void bind(Object object) {
        Class<?> aClass = object.getClass();

        if (!aClass.isAnnotationPresent(Layout.class)) {
            return;
        }

        Layout annotation = aClass.getAnnotation(Layout.class);
        int layoutRes = annotation.value();
        if (layoutRes != -1) {
            try {
                Method setContentView = aClass.getMethod("setContentView", int.class);
                setContentView.invoke(object, layoutRes);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
