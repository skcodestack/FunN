package github.funn.interactor.impl;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import github.funn.R;
import github.funn.interactor.SplashInteractor;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/3.
 */

public class SplashInteractorImpl implements SplashInteractor {

    @Override
    public int getBackgroundImageResID() {
        return R.drawable.splash_load;
    }

    @Override
    public String getCopyright(Context context) {
        return context.getResources().getString(R.string.splash_copyright);
    }

    @Override
    public String getVersionName(Context context) {
        String versionName = null;
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return String.format(context.getResources().getString(R.string.splash_version), versionName);
    }

    @Override
    public Animation getBackgroundImageAnimation(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.splash);
    }
}
