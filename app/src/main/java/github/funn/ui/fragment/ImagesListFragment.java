package github.funn.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import github.customlibrary.adapter.ListViewDataAdapter;
import github.customlibrary.adapter.ViewHolderBase;
import github.customlibrary.adapter.ViewHolderCreator;
import github.customlibrary.eventbus.EventCenter;
import github.customlibrary.netstatus.NetUtil;
import github.customlibrary.utils.LogUtil;
import github.customlibrary.wigdets.LoadMoreListView;
import github.customlibrary.wigdets.XSwipeRefreshLayout;
import github.funn.R;
import github.funn.adapter.ImageRecycleAdapter;
import github.funn.bean.ItemEntity;
import github.funn.bean.NewsEntity;
import github.funn.common.ApiConstants;
import github.funn.common.Constants;
import github.funn.common.OnCommonPageSelectedListener;
import github.funn.common.UriHelper;
import github.funn.listeners.OnItemClickListener;
import github.funn.listeners.OnRecyclerViewScrollListener;
import github.funn.presenter.NewsListPresenter;
import github.funn.presenter.impl.NewsListPresenterImpl;
import github.funn.ui.activity.GifDetailActivity;
import github.funn.ui.activity.ImagesDetailActivity;
import github.funn.ui.activity.base.BaseFragment;
import github.funn.utils.FToash;
import github.funn.utils.ShareUtil;
import github.funn.utils.StringUtil;
import github.funn.view.NewsListView;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/3.
 */

public class ImagesListFragment extends BaseFragment implements NewsListView ,OnCommonPageSelectedListener, SwipeRefreshLayout.OnRefreshListener,OnItemClickListener<ItemEntity> {

    private String TAG=this.getClass().getSimpleName();

    @InjectView(R.id.image_recycle_view)
    RecyclerView mRecyclerView;

    @InjectView(R.id.fragment_images_list_swipe_layout)
    XSwipeRefreshLayout mSwipeRefreshLayout;

    /**
     * the page number
     */
    private int mCurrentPage = 1;
    private String mCurrentCategory;
    NewsListPresenter mNewsListPresenter;
    private ImageRecycleAdapter recyclerViewAdapter;
    private OnRecyclerViewScrollListener scrollListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentCategory=getResources().getStringArray(R.array.news_category_list_path)[0];
    }

    @Override
    protected void onFirstUserVisible() {
        mNewsListPresenter= new NewsListPresenterImpl(mContext,this);
        if(NetUtil.isNetworkAvailable(mContext)){
            if(null != mSwipeRefreshLayout){
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mNewsListPresenter.loadListData(TAG_LOG, Constants.EVENT_REFRESH_DATA,mCurrentCategory,mCurrentPage,false);
                    }
                }, ApiConstants.Integers.PAGE_LAZY_LOAD_DELAY_TIME_MS);
            }
        }else{
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mNewsListPresenter.loadListData(TAG_LOG, Constants.EVENT_REFRESH_DATA,mCurrentCategory,mCurrentPage,false);
                }
            });
        }

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return mSwipeRefreshLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerViewAdapter = new ImageRecycleAdapter(this);

        mRecyclerView.setAdapter(recyclerViewAdapter);
        scrollListener = new OnRecyclerViewScrollListener<ItemEntity>(){

            @Override
            public void onStart() {
                Log.e(TAG,"===>onStart");
                recyclerViewAdapter.setFooterView(R.layout.item_footer);

                if (recyclerViewAdapter.hasHeader()){
                    mRecyclerView.smoothScrollToPosition(recyclerViewAdapter.getItemCount()+1);
                }else{
                    mRecyclerView.smoothScrollToPosition(recyclerViewAdapter.getItemCount());
                }
            }

            @Override
            public void onLoadMore() {
                Log.e(TAG,"===>onLoadMore");
                mCurrentPage++;
                mNewsListPresenter.loadListData(TAG_LOG, Constants.EVENT_LOAD_MORE_DATA, mCurrentCategory, mCurrentPage, true);

            }

            @Override
            public void onFinish(List<ItemEntity> list) {
                if(list!=null) {
                    recyclerViewAdapter.getList().addAll(list);
                    recyclerViewAdapter.notifyDataSetChanged();
                }
                setLoadingMore(false);
                recyclerViewAdapter.setFooterView(0);
            }
        };

        mRecyclerView.addOnScrollListener(scrollListener);

        mSwipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }




    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_image_list;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    //刷新列表数据
    @Override
    public void refreshListData(NewsEntity responseNewsListEntity) {
        Log.e(TAG,"===>refreshListData");
        if (null != mSwipeRefreshLayout) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if(responseNewsListEntity==null ){

            if(null != recyclerViewAdapter && recyclerViewAdapter.getList().size()>0){
                FToash.getInstant().showNormalToash("刷新数据失败！");
                return;
            }else {
                showErrorPager("");
                return ;
            }

        }

        if(!StringUtil.compare(Constants.SUCCESS_CODE,responseNewsListEntity.getCode())){
            if(null != recyclerViewAdapter && recyclerViewAdapter.getList().size()>0){
                FToash.getInstant().showNormalToash("刷新数据失败！");
                return;
            }else {
                showErrorPager("");
                return ;
            }
        }

        if (null != responseNewsListEntity) {
            if (null != recyclerViewAdapter) {
                LogUtil.e(TAG,"==>refreshListData"+responseNewsListEntity);
                recyclerViewAdapter.getList().clear();
                recyclerViewAdapter.getList().addAll(responseNewsListEntity.getResult());
                recyclerViewAdapter.notifyDataSetChanged();
            }

        }
    }
    //加载列表数据
    @Override
    public void addMoreListData(NewsEntity responseNewsListEntity) {
        Log.e(TAG,"===>addMoreListData");
        if(scrollListener!=null){
            scrollListener.onFinish(null);
        }
        if(scrollListener!=null && responseNewsListEntity!=null && responseNewsListEntity.getResult()!=null){
            scrollListener.onFinish(responseNewsListEntity.getResult());
        }
    }
    //跳转
    @Override
    public void navigateToNewsDetail(int position, ItemEntity entity, int x, int y, int width, int height) {
        Bundle extras = new Bundle();
        extras.putString(ImagesDetailActivity.INTENT_IMAGE_URL_TAG, entity.getImage_url());
        extras.putInt(ImagesDetailActivity.INTENT_IMAGE_X_TAG, x);
        extras.putInt(ImagesDetailActivity.INTENT_IMAGE_Y_TAG, y);
        extras.putInt(ImagesDetailActivity.INTENT_IMAGE_W_TAG, width);
        extras.putInt(ImagesDetailActivity.INTENT_IMAGE_H_TAG, height);
        readyGo(ImagesDetailActivity.class, extras);
        getActivity().overridePendingTransition(0, 0);
    }
    //页选择
    @Override
    public void onPageSelected(int position, String keywords) {
        mCurrentCategory=keywords;
    }

    @Override
    public void onRefresh() {
        mCurrentPage = 1;
        mNewsListPresenter.loadListData(TAG_LOG, Constants.EVENT_REFRESH_DATA, mCurrentCategory, mCurrentPage,
                true);
    }



    @Override
    public void showErrorPager(String msg) {
        super.showErrorPager(msg);
        if (null != mSwipeRefreshLayout) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        toggleShowError(true, msg, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewsListPresenter.loadListData(TAG_LOG, Constants.EVENT_REFRESH_DATA, mCurrentCategory,
                        mCurrentPage, false);
            }
        });
    }


    @Override
    public void onClick(View view, ItemEntity date) {
        Rect frame = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int[] location = new int[2];
        view.getLocationOnScreen(location);
        location[1] += statusBarHeight;

        int width = view.getWidth();
        int height = view.getHeight();

        if (null != recyclerViewAdapter) {
            if (date!=null) {
                mNewsListPresenter.onItemClickListener(0,
                        date,
                        location[0],
                        location[1],
                        width,
                        height);
            }
        }
    }
}
