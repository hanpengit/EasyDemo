package com.hunder.easylib.base;

/**
 * Created by zhangxuan on 2016/11/11.
 */

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);

    void detachView();
}
