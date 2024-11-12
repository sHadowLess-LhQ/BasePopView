package cn.com.shadowless.basepopview.base;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.viewbinding.ViewBinding;

import com.lxj.xpopup.core.AttachPopupView;

import java.lang.reflect.InvocationTargetException;

import cn.com.shadowless.basepopview.R;
import cn.com.shadowless.basepopview.event.PopPublicEvent;


/**
 * 垂直依附弹窗
 *
 * @param <VB> the type parameter
 * @author sHadowLess
 */
public abstract class BaseVerticalAttachPopView<VB extends ViewBinding> extends AttachPopupView implements
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
    public BaseVerticalAttachPopView(@NonNull Context context) {
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
            getPopupImplView().setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.bg_base_pop_radius_shape));
        }
        initObject();
        initView();
        initViewListener();
        initData();
        initDataListener();
    }

    @Override
    public void doAttach() {
        ViewGroup.LayoutParams params = getPopupContentView().getLayoutParams();
        int oldWidth = params.width;
        int oldHeight = params.height;
        super.doAttach();
        if (!isFixWidth()) {
            params.width = oldWidth;
        }
        if (!isFixHeight()) {
            params.height = oldHeight;
        }
        getPopupContentView().setLayoutParams(params);
    }

    /**
     * Is fix height boolean.
     *
     * @return the boolean
     */
    protected boolean isFixHeight() {
        return true;
    }

    /**
     * Is fix width boolean.
     *
     * @return the boolean
     */
    protected boolean isFixWidth() {
        return true;
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