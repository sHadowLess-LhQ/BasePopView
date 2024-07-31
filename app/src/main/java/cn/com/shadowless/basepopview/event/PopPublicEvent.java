package cn.com.shadowless.basepopview.event;

import android.text.TextUtils;
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
 * @author sHadowLess
 */
public interface PopPublicEvent {

    /**
     * The interface Init view binding.
     *
     * @param <VB> the type parameter
     */
    interface InitViewBinding<VB extends ViewBinding> {
        /**
         * The constant DEF_TYPE.
         */
        String DEF_TYPE = "layout";

        /**
         * Sets compare generic super class name.
         *
         * @return the compare generic super class name
         */
        default String setCompareGenericSuperClassName() {
            return null;
        }

        /**
         * Init generics class class.
         *
         * @param o the o
         * @return the class
         */
        default Class<VB> initGenericsClass(Object o) {
            Type superClass = o.getClass().getGenericSuperclass();
            ParameterizedType parameterized = (ParameterizedType) superClass;
            Type[] types = parameterized.getActualTypeArguments();
            String compareName = setCompareGenericSuperClassName();
            if (TextUtils.isEmpty(compareName)) {
                compareName = "Binding";
            }
            for (Type type : types) {
                Class<?> genericsCls = (Class<?>) type;
                if (!genericsCls.getSimpleName().contains(compareName)) {
                    continue;
                }
                if (genericsCls == ViewBinding.class) {
                    genericsCls = setBindViewClass();
                }
                return (Class<VB>) genericsCls;
            }
            throw new RuntimeException("传入的泛型未找到与ViewBinding相关的泛型超类，请检查参数或手动初始化ViewBinding");
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
         * Inflate view vb.
         *
         * @param o    the o
         * @param view the view
         * @return the vb
         * @throws InvocationTargetException the invocation target exception
         * @throws IllegalAccessException    the illegal access exception
         * @throws NoSuchMethodException     the no such method exception
         */
        default VB inflateView(Object o, View view) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
            Method inflateMethod = initGenericsClass(o).getMethod("bind", View.class);
            return (VB) inflateMethod.invoke(null, view);
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
    }


    /**
     * The interface Init view click.
     */
    interface InitViewClick extends View.OnClickListener {
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
    }

    /**
     * The interface Init event.
     */
    interface InitEvent {
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
}
