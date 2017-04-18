package github.funn.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import github.customlibrary.base.BaseAppCompatActivity;
import github.customlibrary.base.BaseLazyFragment;
import github.customlibrary.eventbus.EventCenter;
import github.customlibrary.netstatus.NetUtil;
import github.customlibrary.wigdets.XViewPager;
import github.funn.R;
import github.funn.presenter.impl.HomePresenterImpl;
import github.funn.ui.activity.base.BaseActivity;
import github.funn.utils.FToash;
import github.funn.view.HomeView;

public class HomeActivity extends BaseActivity implements HomeView{

    private static long DOUBLE_CLICK_TIME = 0L;

    @InjectView(R.id.home_container)
    FrameLayout fl_container;
    private HomePresenterImpl mHomePresenter;

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return true;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return ButterKnife.findById(this, R.id.home_container);
    }

    @Override
    public void initializeViews(BaseLazyFragment fragment) {
        Log.e("Tag","HomeActivity=====initializeViews");
        if(fragment!=null){
//            fl_container.addView(fragment.getView());
            fragment.setUserVisibleHint(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.home_container,fragment).commit();
            Log.e("Tag","HomeActivity=====getSupportFragmentManager");
        }
    }

    @Override
    protected void initViewsAndEvents() {
        setToobarTitle(R.string.app_name,false);
        mHomePresenter = new HomePresenterImpl(this, this);
        mHomePresenter.initialized();
    }

    @Override
    protected void onNetworkConnected(NetUtil.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }
    /**
     * 菜单
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int  itemid=item.getItemId();
        switch (itemid){
            case R.id.action_person:
                readyGo(SettingActivity.class);
                break;

        }
        return true;
    }
    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - DOUBLE_CLICK_TIME) > 2000) {
                showToast(getString(R.string.double_click_exit));
                DOUBLE_CLICK_TIME = System.currentTimeMillis();
            } else {
                getBaseApplication().exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
