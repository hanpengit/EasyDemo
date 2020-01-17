package com.hunder.easylib.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by hp on 2020/1/10.
 */

public class BaseRxPresenter<T extends BaseView> implements BasePresenter<T> {

    protected T mView;
    private CompositeDisposable mDisposables;

    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        cancelAll();
    }

    public void add(Disposable disposable) {
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
        if (disposable != null) {
            mDisposables.add(disposable);
        }
    }

    public void cancel(Disposable disposable) {
        if (mDisposables != null) {
            mDisposables.delete(disposable);
        }
    }

    public void cancelAll() {
        if (mDisposables != null) {
            mDisposables.clear();
        }
    }

}
