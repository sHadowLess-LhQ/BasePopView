package cn.com.shadowless.basepopview.event;

import android.view.View;

import androidx.viewbinding.ViewBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cn.com.shadowless.basepopview.base.PopCons;

/**
 * The interface Public event.
 *
 * @param <VB> the type parameter
 * @author sHadowLess
 */
public interface PopPublicEvent<VB extends ViewBinding> extends View.OnClickListener {

    /**
     * The constant DEF_TYPE.
     */
    String DEF_TYPE = "layout";

    /**
     * Init generics class class.
     *
     * @param o the o
     * @return the class
     */
    default Class<VB> initGenericsClass(Object o) {
        Type superClass = o.getClass().getGenericSuperclass();
        ParameterizedType parameterized = (ParameterizedType) superClass;
        return (Class<VB>) parameterized.getActualTypeArguments()[0];
    }

    /**
     * Is fast click boolean.
     *
     * @param time the time
     * @return the boolean
     */
    default boolean isFastClick(int time) {
        long currentTime = System.currentTimeMillis();
        long timeInterval = currentTime - PopCons.lastClickTime;
        if (0 < timeInterval && timeInterval < time) {
            return true;
        }
        PopCons.lastClickTime = currentTime;
        return false;
    }

    /**
     * Inflate t.
     *
     * @param <T>    the type parameter
     * @param tClass the t class
     * @param view   the view
     * @return the t
     * @throws InvocationTargetException the invocation target exception
     * @throws IllegalAccessException    the illegal access exception
     * @throws NoSuchMethodException     the no such method exception
     */
    default <T> T inflate(Class<T> tClass, View view) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method inflateMethod = tClass.getMethod("bind", View.class);
        return (T) inflateMethod.invoke(null, view);
    }

    /**
     * Gets layout name by binding class.
     *
     * @param cls the cls
     * @return the layout name by binding class
     */
    default String getLayoutNameByBindingClass(Class<?> cls) {
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

    /**
     * Inflate view vb.
     *
     * @param view the view
     * @return the vb
     * @throws InvocationTargetException the invocation target exception
     * @throws IllegalAccessException    the illegal access exception
     * @throws NoSuchMethodException     the no such method exception
     */
    default VB inflateView(View view) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return this.inflate(initGenericsClass(), view);
    }

    /**
     * 设置绑定视图
     *
     * @return the 视图
     */
    default Class<VB> setBindViewClass() {
        return null;
    }

    /**
     * Init generics class class.
     *
     * @return the class
     */
    default Class<VB> initGenericsClass() {
        Class<VB> genericsCls = this.initGenericsClass(this);
        if (genericsCls == ViewBinding.class) {
            genericsCls = setBindViewClass();
        }
        return genericsCls;
    }

    /**
     * Click.
     *
     * @param v the v
     */
    default void antiShakingClick(View v) {

    }

    @Override
    default void onClick(View v) {
        if (!isFastClick(PopCons.TIME)) {
            antiShakingClick(v);
        }
    }

    /**
     * 是否默认背景颜色
     *
     * @return the boolean
     */
    boolean isDefaultBackground();

    /**
     * Init object.
     */
    void initObject();

    /**
     * 初始化成功视图
     */
    void initView();

    /**
     * 初始化视图监听
     */
    void initViewListener();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 绑定数据到视图
     */
    void initDataListener();
}
