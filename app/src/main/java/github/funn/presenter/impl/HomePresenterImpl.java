package github.funn.presenter.impl;

import android.content.Context;
import android.util.Log;

import github.funn.interactor.HomeInteractor;
import github.funn.interactor.impl.HomeInteractorImpl;
import github.funn.presenter.Presenter;
import github.funn.view.HomeView;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/3.
 */

public class HomePresenterImpl implements Presenter {

    private Context mContext = null;
    private HomeView mHomeView = null;
    private HomeInteractor mHomeInteractor = null;

    public HomePresenterImpl(Context context, HomeView homeView){
        if (null == homeView) {
            throw new IllegalArgumentException("Constructor's parameters must not be Null");
        }

        mContext = context;
        mHomeView = homeView;
        mHomeInteractor = new HomeInteractorImpl();
    }

    @Override
    public void initialized() {

        mHomeView.initializeViews(mHomeInteractor.getPagerFragments());
    }
}
