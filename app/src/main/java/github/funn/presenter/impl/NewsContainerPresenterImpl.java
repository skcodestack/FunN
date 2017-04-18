package github.funn.presenter.impl;

import android.content.Context;

import github.funn.interactor.CommonContainerInteractor;
import github.funn.interactor.impl.NewsCommonContainerInteractor;
import github.funn.presenter.Presenter;
import github.funn.view.CommonContainerView;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/3.
 */

public class NewsContainerPresenterImpl implements Presenter {

    private Context mContext;
    private CommonContainerInteractor mCommonContainerInteractor;
    private CommonContainerView mCommonContainerView;

    public NewsContainerPresenterImpl(Context context, CommonContainerView commonContainerView){
        mContext = context;
        mCommonContainerView = commonContainerView;
        mCommonContainerInteractor = new NewsCommonContainerInteractor();
    }

    @Override
    public void initialized() {
        mCommonContainerView.initializePagerViews(mCommonContainerInteractor.getCommonCategoryList(mContext));
    }
}
