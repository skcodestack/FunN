package github.funn.ui.activity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import github.funn.bean.NewsCategoryEntity;
import github.funn.ui.fragment.GifListFragment;
import github.funn.ui.fragment.ImagesListFragment;
import github.funn.utils.StringUtil;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/3.
 */

public class NewsContainerPagerAdapter extends FragmentPagerAdapter {
    List<NewsCategoryEntity> mCategoryList=null;
    public NewsContainerPagerAdapter(FragmentManager fm,List<NewsCategoryEntity> categoryList) {
        super(fm);
        this.mCategoryList=categoryList;
    }

    @Override
    public Fragment getItem(int position) {
        if(StringUtil.compare("hot",mCategoryList.get(position).getPath()) || StringUtil.compare("pure",mCategoryList.get(position).getPath())){
            return new ImagesListFragment();
        }
        return new GifListFragment();
    }

    @Override
    public int getCount() {
        return  (null != mCategoryList) ? mCategoryList.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (null != mCategoryList && mCategoryList.get(position)!=null) ? mCategoryList.get(position).getName(): null;
    }
}
