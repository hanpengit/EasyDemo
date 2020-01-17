package com.hunder.easydemo.mvp.presenter;

import com.hunder.easydemo.MainActivity;
import com.hunder.easydemo.mvp.contract.MainContract;
import com.hunder.easylib.base.BaseRxPresenter;
import com.hunder.easylib.entity.Home;
import com.hunder.easylib.http.Observer;
import com.hunder.easylib.http.RetrofitManager;
import com.hunder.easylib.http.RxThreadUtils;

import io.reactivex.annotations.NonNull;

/**
 * Created by hp on 2020/1/10.
 */

public class MainPresenter extends BaseRxPresenter<MainActivity> implements MainContract.Presenter {

    public MainPresenter(MainActivity view) {
        attachView(view);
    }

    @Override
    public void loadData() {
        RetrofitManager.getApiService().getHome()
                .compose(RxThreadUtils.<Home>observableToMain())
                .subscribe(new Observer<Home>(this) {
                    @Override
                    public void onSuccess(@NonNull Home home) {
                        mView.showData(home);
                    }

                    @Override
                    public void onFail(@NonNull Throwable e) {
                        mView.showError(e.getMessage());
                    }
                });
    }

}
