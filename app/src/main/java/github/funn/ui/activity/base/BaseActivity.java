package github.funn.ui.activity.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import github.customlibrary.base.BaseAppCompatActivity;
import github.customlibrary.eventbus.EventCenter;
import github.customlibrary.netstatus.NetUtil;
import github.funn.FunNApplication;
import github.funn.R;
import github.funn.utils.FToash;
import github.funn.view.base.BaseView;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/3.
 */

public abstract class BaseActivity extends BaseAppCompatActivity implements BaseView{

    protected Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isApplyKitKatTranslucency()) {
            setSystemBarTintDrawable(getResources().getDrawable(R.drawable.sr_primary));
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mToolbar = ButterKnife.findById(this, R.id.common_toolbar);
        if (null != mToolbar) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void setToobarTitle(String title,boolean ishomeButtonEnable){

        if(mToolbar!=null) {
            mToolbar.setTitle(title);
            getSupportActionBar().setHomeButtonEnabled(ishomeButtonEnable);
            getSupportActionBar().setDisplayHomeAsUpEnabled(ishomeButtonEnable);
        }
    }

    protected void setToobarTitle(int id,boolean ishomeButtonEnable){

        if(mToolbar!=null) {
            mToolbar.setTitle(id);
            getSupportActionBar().setHomeButtonEnabled(ishomeButtonEnable);
            getSupportActionBar().setDisplayHomeAsUpEnabled(ishomeButtonEnable);
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
    protected FunNApplication getBaseApplication(){

        return  (FunNApplication)getApplication();
    }

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

    /**
     * KitKat   api:4.4   >19
     * @return
     */
    protected abstract boolean isApplyKitKatTranslucency();
}
