package github.funn.ui.fragment;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import java.util.List;

import butterknife.InjectView;
import github.customlibrary.pagertab.PagerSlidingTabStrip;
import github.customlibrary.eventbus.EventCenter;
import github.customlibrary.wigdets.XViewPager;
import github.funn.R;
import github.funn.bean.NewsCategoryEntity;
import github.funn.common.OnCommonPageSelectedListener;
import github.funn.presenter.Presenter;
import github.funn.presenter.impl.NewsContainerPresenterImpl;
import github.funn.ui.activity.adapter.NewsContainerPagerAdapter;
import github.funn.ui.activity.base.BaseFragment;
import github.funn.utils.StringUtil;
import github.funn.view.CommonContainerView;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/3.
 */

public class NewsContainerFragment extends BaseFragment implements CommonContainerView{

    @InjectView(R.id.fragment_images_pager)
    XViewPager mViewPager;

    @InjectView(R.id.pager_tab_strip)
    PagerSlidingTabStrip mSmartTabLayout;

    Presenter mNewsContainerPresenter=null;
    @Override
    protected void onFirstUserVisible() {
        Log.e("Tag","=====onFirstUserVisible");

        mNewsContainerPresenter=new NewsContainerPresenterImpl(mContext,this);
        mNewsContainerPresenter.initialized();
    }

    @Override
    protected void onUserVisible() {

    }
    @Override
    public void initializePagerViews(final  List<NewsCategoryEntity> categoryList) {
        Log.e("Tag","=====initializePagerViews");
        //showToast("=====initializePagerViews"+categoryList.size());
        if(categoryList!=null && !categoryList.isEmpty()){
            Log.e("Tag","size==>"+categoryList.size());
            mViewPager.setOffscreenPageLimit(categoryList.size());
            mViewPager.setAdapter(new NewsContainerPagerAdapter(getSupportFragmentManager(),categoryList));
            mSmartTabLayout.setViewPager(mViewPager);
            mSmartTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                    Object baseObj = mViewPager.getAdapter().instantiateItem(mViewPager, position);
                    BaseFragment fragment= (BaseFragment) baseObj;
                    fragment.setUserVisibleHint(true);
                    OnCommonPageSelectedListener pageSelectedListener= (OnCommonPageSelectedListener) baseObj;
                    pageSelectedListener.onPageSelected(position, categoryList.get(position).getPath());

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }
    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        Log.e("Tag","=====initViewsAndEvents");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_news;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }


}
