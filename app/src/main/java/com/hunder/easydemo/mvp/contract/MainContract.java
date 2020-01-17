package com.hunder.easydemo.mvp.contract;

import com.hunder.easylib.base.BaseView;
import com.hunder.easylib.entity.Home;

/**
 * Created by hp on 2020/1/10.
 */

public class MainContract {

    public interface View extends BaseView<Home> {

    }

    public interface Presenter {
        void loadData();
    }

}
