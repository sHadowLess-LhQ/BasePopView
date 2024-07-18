package cn.com.shadowless.basepopview.base;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import androidx.annotation.NonNull;

import androidx.viewbinding.ViewBinding;

import com.lxj.xpopup.core.BubbleHorizontalAttachPopupView;

import cn.com.shadowless.basepopview.utils.ViewBindingUtils;


/**
 * 水平气泡弹窗
 *
 * @param <VB> the type 绑定视图
 * @author sHadowLess
 */
public abstract class BaseBubbleHorizontalAttachPopupView<VB extends ViewBinding> extends BubbleHorizontalAttachPopupView implements AntiShakingOnClickListener {

    /**
     * 绑定视图
     */
    private VB bind = null;
    /**
     * 上下文
     */
    private final Context context;

    /**
     * 构造
     *
     * @param context the 上下文
     */
    public BaseBubbleHorizontalAttachPopupView(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected int getImplLayoutId() {
        return context.getResources().getIdentifier(ViewBindingUtils.getLayoutNameByBindingClass(setBindViewClass()), "layout", context.getPackageName());
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        bind = inflateView();
        if (isDefaultBackground()) {
            this.setBubbleBgColor(Color.WHITE);
        }
        initObject();
        initView();
        initViewListener();
        initData();
        initDataListener();
    }

    /**
     * Inflate view vb.
     *
     * @return the vb
     */
    protected VB inflateView() {
        try {
            return ViewBindingUtils.inflate(setBindViewClass().getName(), getPopupImplView());
        } catch (Exception e) {
            throw new RuntimeException("视图无法反射初始化，请检查setBindViewClassName是否传入绝对路径或重写自实现inflateView方法捕捉堆栈" + Log.getStackTraceString(e));
        }
    }

    /**
     * 获取绑定视图控件
     *
     * @return the bind view
     */
    protected VB getBindView() {
        return bind;
    }

    /**
     * Sets bind view class name.
     *
     * @return the bind view class name
     */
    @NonNull
    protected abstract Class<VB> setBindViewClass();

    /**
     * 是否默认背景颜色
     *
     * @return the boolean
     */
    protected abstract boolean isDefaultBackground();

    /**
     * Init object.
     */
    protected abstract void initObject();

    /**
     * 初始化成功视图
     */
    protected abstract void initView();

    /**
     * 初始化视图监听
     */
    protected abstract void initViewListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();


    /**
     * 绑定数据到视图
     */
    protected abstract void initDataListener();

}
