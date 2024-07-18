package cn.com.shadowless.basepopview.base;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.viewbinding.ViewBinding;
import com.lxj.xpopup.core.BottomPopupView;

import cn.com.shadowless.basepopview.R;
import cn.com.shadowless.basepopview.utils.ViewBindingUtils;


/**
 * 底部弹窗
 *
 * @param <VB> the type 绑定视图
 * @author sHadowLess
 */
public abstract class BaseBottomPopView<VB extends ViewBinding> extends BottomPopupView implements AntiShakingOnClickListener {

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
    public BaseBottomPopView(@NonNull Context context) {
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
        if (bind == null) {
            throw new RuntimeException("视图反射初始化失败，请检查setBindViewClassName是否传入绝对路径或重写自实现inflateView方法捕捉堆栈");
        }
        if (isDefaultBackground()) {
            getPopupImplView().setBackground(AppCompatResources.getDrawable(context, R.drawable.bg_base_pop_bottom_shape));
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
