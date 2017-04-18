package github.funn.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.steamcrafted.materialiconlib.MaterialIconView;

import butterknife.InjectView;
import github.customlibrary.base.BaseAppCompatActivity;
import github.customlibrary.eventbus.EventCenter;
import github.customlibrary.netstatus.NetUtil;
import github.funn.R;
import github.funn.ui.activity.base.BaseActivity;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/4/17.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.icon_left)
    MaterialIconView backView;

    @InjectView(R.id.user_name)
    TextView userName;

    @InjectView(R.id.user_email)
    TextView userEmail;

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.setting;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        backView.setOnClickListener(this);
        userName.setText("匿名用户");
        userEmail.setText(" ");

    }

    @Override
    protected void onNetworkConnected(NetUtil.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
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
    public void onClick(View v) {
        if(v.getId()==R.id.icon_left){
            finish();
        }
    }
}
