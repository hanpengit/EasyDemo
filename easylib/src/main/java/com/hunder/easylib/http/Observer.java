package com.hunder.easylib.http;

import com.hunder.easylib.base.BaseRxPresenter;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by hp on 2020/1/9.
 */

public abstract class Observer<T> implements io.reactivex.Observer<T> {

    private BaseRxPresenter mPresenter;

    public Observer(BaseRxPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onNext(@NonNull T t) {
        if (t == null) {
            onFail(new Throwable("null response"));
        } else {
            onSuccess(t);
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        onFail(e);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        // 添加订阅关系
        onDispose(d);
    }

    @Override
    public void onComplete() {
        onFinish();
    }

    public void onFinish() {

    }

    public void onDispose(@NonNull Disposable d) {
        if (mPresenter != null) {
            mPresenter.add(d);
        }
    }

    public abstract void onSuccess(@NonNull T t);

    public abstract void onFail(@NonNull Throwable e);

}
