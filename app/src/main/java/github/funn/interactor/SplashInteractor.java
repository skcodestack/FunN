package github.funn.interactor;

import android.content.Context;
import android.view.animation.Animation;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/3.
 */

public interface SplashInteractor {

    int getBackgroundImageResID();

    String getCopyright(Context context);

    String getVersionName(Context context);

    Animation getBackgroundImageAnimation(Context context);
}
