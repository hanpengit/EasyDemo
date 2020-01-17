package com.hunder.easylib.base;

/**
 * Created by hp on 2020/1/10.
 */

public interface BaseView<T> {
    void showData(T data);
    //void showRefreshData(T data);
    //void showLoadMoreData(T data);
    void showLoading(boolean show);
    void showError(String errorMsg);
}
