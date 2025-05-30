package cn.com.shadowless.basepopview.base;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.viewbinding.ViewBinding;

import com.lxj.xpopup.core.BottomPopupView;

import java.lang.reflect.InvocationTargetException;

import cn.com.shadowless.basepopview.R;
import cn.com.shadowless.basepopview.event.PopPublicEvent;

/**
 * 底部弹窗
 *
 * @param <VB> the type 绑定视图
 * @author sHadowLess
 */
public abstract class BaseBottomPopView<VB extends ViewBinding> extends BottomPopupView implements
        PopPublicEvent.InitViewBinding<VB>, PopPublicEvent.InitViewClick, PopPublicEvent.InitEvent {

    /**
     * 绑定视图
     */
    private VB bind = null;

    /**
     * 构造
     *
     * @param context the 上下文
     */
    public BaseBottomPopView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return getContext().getResources().getIdentifier(
                this.getLayoutNameByBindingClass(initViewBindingGenericsClass(this)),
                DEF_TYPE,
                getContext().getPackageName()
        );
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        try {
            bind = inflateView(this, getPopupImplView());
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException("视图无法反射初始化，若动态布局请检查setBindViewClass是否传入或重写inflateView手动实现ViewBinding创建" + Log.getStackTraceString(e));
        }
        if (isDefaultBackground()) {
            getPopupImplView().setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.bg_base_pop_bottom_shape));
        }
        initObject();
        initView();
        initViewListener();
        initDataListener();
        initData();
    }

    /**
     * 获取绑定视图控件
     *
     * @return the bind view
     */
    protected VB getBindView() {
        return bind;
    }
}
