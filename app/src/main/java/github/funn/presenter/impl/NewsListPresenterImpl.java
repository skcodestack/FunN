package github.funn.presenter.impl;

import android.content.Context;

import github.funn.R;
import github.funn.bean.BaseEntity;
import github.funn.bean.ItemEntity;
import github.funn.bean.NewsEntity;
import github.funn.common.Constants;
import github.funn.interactor.CommonListInteractor;
import github.funn.interactor.impl.NewsListInteractorImpl;
import github.funn.listeners.BaseMultiLoadedListener;
import github.funn.presenter.NewsListPresenter;
import github.funn.view.NewsListView;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/6.
 */

public class NewsListPresenterImpl implements NewsListPresenter,BaseMultiLoadedListener<NewsEntity> {
    private Context mContext = null;
    private NewsListView mNewsListView = null;
    private CommonListInteractor mCommonListInteractor = null;
    public  NewsListPresenterImpl(Context context, NewsListView newsListView) {
        mContext = context;
        mNewsListView = newsListView;
        mCommonListInteractor = new NewsListInteractorImpl(this);
    }
    @Override
    public void loadListData(String requestTag, int event_tag, String key, int page, boolean isSwipeRefresh) {
        mNewsListView.hideLoadingPager();
        if (!isSwipeRefresh) {
            mNewsListView.showLoadingPager(mContext.getString(R.string.common_loading_message));
        }
        mCommonListInteractor.getCommonListData(isSwipeRefresh,requestTag,event_tag,key,page);

    }

    @Override
    public void onItemClickListener(int position, ItemEntity entity, int x, int y, int width, int height) {
        mNewsListView.navigateToNewsDetail(position, entity,x,y,width,height);
    }



    //获取数据成功
    @Override
    public void onSuccess(int event_tag, NewsEntity data) {
        mNewsListView.hideLoadingPager();
        if (event_tag == Constants.EVENT_REFRESH_DATA) {
            mNewsListView.refreshListData(data);
        } else if (event_tag == Constants.EVENT_LOAD_MORE_DATA) {
            mNewsListView.addMoreListData(data);
        }
    }

    @Override
    public void onError(int event_tag,String msg) {
        mNewsListView.hideLoadingPager();
        mNewsListView.showErrorPager(msg);
    }

    @Override
    public void onException(int event_tag,String msg) {
        mNewsListView.hideLoadingPager();
        if(event_tag == Constants.EVENT_REFRESH_DATA){
//            mNewsListView.showErrorPager(msg);
            mNewsListView.refreshListData(null);
        }else if (event_tag == Constants.EVENT_LOAD_MORE_DATA){
            mNewsListView.addMoreListData(null);
        }


    }
}
