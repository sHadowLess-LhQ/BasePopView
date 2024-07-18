package cn.com.shadowless.basepopview.base;

import android.view.View;


/**
 * The interface Anti shaking on click listener.
 *
 * @author sHadowLess
 */
public interface AntiShakingOnClickListener extends View.OnClickListener {


    @Override
    default void onClick(View v) {
        if (!isFastClick()) {
            antiShakingClick(v);
        }
    }

    /**
     * Is fast click boolean.
     *
     * @return the boolean
     */
    default boolean isFastClick() {
        long currentTime = System.currentTimeMillis();
        long timeInterval = currentTime - PopCons.lastClickTime;
        if (0 < timeInterval && timeInterval < PopCons.TIME) {
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
    void antiShakingClick(View v);
}
