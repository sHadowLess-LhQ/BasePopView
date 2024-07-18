package cn.com.shadowless.basepopview.utils;

import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * The type View binding utils.
 *
 * @author sHadowLess
 */
public class ViewBindingUtils {
    /**
     * Instantiates a new View binding utils.
     */
    private ViewBindingUtils() {

    }

    /**
     * Inflate t.
     *
     * @param <T>       the type parameter
     * @param className the class name
     * @param view      the view
     * @return the t
     * @throws ClassNotFoundException    the class not found exception
     * @throws InvocationTargetException the invocation target exception
     * @throws IllegalAccessException    the illegal access exception
     * @throws NoSuchMethodException     the no such method exception
     */
    public static <T> T inflate(String className, View view) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Class<?> bindingClass = Class.forName(className);
        Method inflateMethod = bindingClass.getMethod("bind", View.class);
        return (T) inflateMethod.invoke(null, view);
    }

    /**
     * Gets layout name by binding class.
     *
     * @param cls the cls
     * @return the layout name by binding class
     */
    public static String getLayoutNameByBindingClass(Class<?> cls) {
        StringBuilder builder = new StringBuilder();
        boolean isFirst = true;
        String name = cls.getSimpleName();
        for (int i = 0; i < name.length(); i++) {
            char temp = name.charAt(i);
            if (Character.isUpperCase(temp)) {
                if (!isFirst) {
                    builder.append("_");
                }
                isFirst = false;
            }
            builder.append(temp);
        }
        String layoutName = builder.toString();
        layoutName = layoutName.substring(0, layoutName.lastIndexOf("_")).toLowerCase();
        return layoutName;
    }
}
