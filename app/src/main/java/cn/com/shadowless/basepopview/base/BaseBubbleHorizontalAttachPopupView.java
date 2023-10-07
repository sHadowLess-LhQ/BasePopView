package cn.com.shadowless.basepopview.base;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewbinding.ViewBinding;

import com.lxj.xpopup.core.BubbleHorizontalAttachPopupView;

import cn.com.shadowless.basepopview.utils.ClickUtils;
import cn.com.shadowless.basepopview.R;
import cn.com.shadowless.basepopview.utils.ViewBindingUtils;

/**
 * 水平气泡弹窗
 *
 * @param <VB> the type 绑定视图
 * @author sHadowLess
 */
public abstract class BaseBubbleHorizontalAttachPopupView<VB extends ViewBinding> extends BubbleHorizontalAttachPopupView implements LifecycleEventObserver, View.OnClickListener {

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
        return setLayoutId();
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        bind = inflateView();
        if (bind == null) {
            throw new RuntimeException("视图无法反射初始化，请检查setBindViewClassName传是否入绝对路径或重写自实现inflateView方法");
        }
        initView();
        if (isDefaultBackground()) {
            getPopupImplView().setBackground(AppCompatResources.getDrawable(context, R.drawable.bg_base_pop_radius_shape));
        }
    }

    @Override
    protected void onShow() {
        super.onShow();
        initListener();
    }

    @Override
    public void onClick(View v) {
        if (!ClickUtils.isFastClick()) {
            click(v);
        }
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            source.getLifecycle().removeObserver(this);
            this.dismiss();
        }
    }

    /**
     * Sets observer lifecycle.
     *
     * @param lifecycle the lifecycle
     */
    public void setNeedObserveLifecycle(Lifecycle lifecycle) {
        lifecycle.addObserver(this);
    }

    /**
     * Inflate view vb.
     *
     * @return the vb
     */
    protected VB inflateView() {
        try {
            return (VB) ViewBindingUtils.inflate(setBindViewClassName(), getPopupImplView());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
     * 设置布局编号
     *
     * @return the layout id
     */
    protected abstract int setLayoutId();

    /**
     * Sets bind view class name.
     *
     * @return the bind view class name
     */
    @NonNull
    protected abstract String setBindViewClassName();

    /**
     * 是否默认背景颜色
     *
     * @return the boolean
     */
    protected abstract boolean isDefaultBackground();

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 初始化监听
     */
    protected abstract void initListener();

    /**
     * 点击
     *
     * @param v the v
     */
    protected abstract void click(@NonNull View v);

}
