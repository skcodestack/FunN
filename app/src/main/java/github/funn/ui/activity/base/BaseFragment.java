package github.funn.ui.activity.base;

import android.view.View;

import github.customlibrary.base.BaseLazyFragment;
import github.customlibrary.eventbus.EventCenter;
import github.funn.view.base.BaseView;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/3.
 */

public abstract class BaseFragment extends BaseLazyFragment implements BaseView {

    @Override
    public void showErrorPager(String msg) {
        toggleShowError(true, msg, null);
    }

    @Override
    public void showExceptionPager(String msg) {
        toggleShowError(true, msg, null);
    }

    @Override
    public void showNetErrorPager() {
        toggleNetworkError(true, null);
    }

    @Override
    public void showLoadingPager(String msg) {
        toggleShowLoading(true, null);
    }

    @Override
    public void hideLoadingPager() {
        toggleShowLoading(false, null);
    }

}
