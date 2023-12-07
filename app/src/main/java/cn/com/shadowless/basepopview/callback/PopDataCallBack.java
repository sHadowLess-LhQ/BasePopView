package cn.com.shadowless.basepopview.callback;

/**
 * The interface Pop data call back.
 *
 * @author sHadowLess
 */
public interface PopDataCallBack<T> {

    void success(T data);

    void fail(Throwable e);
}
