package cn.com.shadowless.basepopview;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewbinding.ViewBinding;

import com.lxj.xpopup.core.DrawerPopupView;

/**
 * 抽屉弹窗
 *
 * @param <VB> the type parameter
 * @author sHadowLess
 */
public abstract class BaseDrawerPopupView<VB extends ViewBinding> extends DrawerPopupView implements LifecycleEventObserver, View.OnClickListener {

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
    public BaseDrawerPopupView(@NonNull Context context) {
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
        bind = setBindView(getPopupImplView());
        initView();
        if (isDefaultBackground()) {
            getPopupImplView().setBackground(AppCompatResources.getDrawable(context, R.drawable.bg_base_pop_full_shape));
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
     * 设置绑定视图
     *
     * @param v the v
     * @return the bind view
     */
    @NonNull
    protected abstract VB setBindView(View v);

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