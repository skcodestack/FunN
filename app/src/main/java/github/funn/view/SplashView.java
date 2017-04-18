package github.funn.view;

import android.view.animation.Animation;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/2.
 */

public interface SplashView {

    void  animateBackgroundImage(Animation animation);
    void initializeViews(String versionName,String copyright,int backgroundResId);
    void initializeUmengConfig();
    void navigateToHomePage();
}
