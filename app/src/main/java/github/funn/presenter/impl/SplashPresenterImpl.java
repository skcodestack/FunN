package github.funn.presenter.impl;

import android.content.Context;
import android.view.animation.Animation;

import github.funn.interactor.SplashInteractor;
import github.funn.interactor.impl.SplashInteractorImpl;
import github.funn.presenter.Presenter;
import github.funn.view.SplashView;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/3.
 */

public class SplashPresenterImpl implements Presenter {

    private Context mContext = null;
    private SplashView mSplashView = null;
    private SplashInteractor mSplashInteractor = null;
    public SplashPresenterImpl(Context context, SplashView splashView){
        if (null == splashView) {
            throw new IllegalArgumentException("Constructor's parameters must not be Null");
        }

        mContext = context;
        mSplashView = splashView;
        mSplashInteractor = new SplashInteractorImpl();
    }

    @Override
    public void initialized() {
        mSplashView.initializeUmengConfig();
        mSplashView.initializeViews(mSplashInteractor.getVersionName(mContext),
                mSplashInteractor.getCopyright(mContext),
                mSplashInteractor.getBackgroundImageResID());

        Animation animation = mSplashInteractor.getBackgroundImageAnimation(mContext);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mSplashView.navigateToHomePage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mSplashView.animateBackgroundImage(animation);
    }
}
