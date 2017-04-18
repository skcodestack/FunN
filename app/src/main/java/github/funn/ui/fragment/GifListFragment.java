package github.funn.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import github.customlibrary.adapter.ListViewDataAdapter;
import github.customlibrary.adapter.ViewHolderBase;
import github.customlibrary.adapter.ViewHolderCreator;
import github.customlibrary.eventbus.EventCenter;
import github.customlibrary.netstatus.NetUtil;
import github.customlibrary.pla.PLAImageView;
import github.customlibrary.smartlayout.SmartTabLayout;
import github.customlibrary.utils.CommonUtils;
import github.customlibrary.utils.LogUtil;
import github.customlibrary.wigdets.LoadMoreListView;
import github.customlibrary.wigdets.XSwipeRefreshLayout;
import github.customlibrary.wigdets.XViewPager;
import github.funn.R;
import github.funn.bean.BaseEntity;
import github.funn.bean.ItemEntity;
import github.funn.bean.NewsEntity;
import github.funn.common.ApiConstants;
import github.funn.common.Constants;
import github.funn.common.OnCommonPageSelectedListener;
import github.funn.common.UriHelper;
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

public class GifListFragment extends BaseFragment implements NewsListView ,OnCommonPageSelectedListener, LoadMoreListView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private String TAG=this.getClass().getSimpleName();

    @InjectView(R.id.fragment_news_list_list_view)
    LoadMoreListView mListView;

    @InjectView(R.id.fragment_news_list_swipe_layout)
    XSwipeRefreshLayout mSwipeRefreshLayout;

    /**
     * the page number
     */
    private int mCurrentPage = 1;
    private String mCurrentCategory;
    private ListViewDataAdapter mListViewAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentCategory=getResources().getStringArray(R.array.news_category_list_path)[0];
    }
    NewsListPresenter mNewsListPresenter;
    @Override
    protected void onFirstUserVisible() {
//        mSwipeRefreshLayout.setEnabled(false);
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


        mListViewAdapter = new ListViewDataAdapter<ItemEntity>(new ViewHolderCreator<ItemEntity>() {
            @Override
            public ViewHolderBase<ItemEntity> createViewHolder(int position) {

                return new ViewHolderBase<ItemEntity>() {
                    ImageView img;
                    TextView mContent;
                    TextView gif_hit;
                    RelativeLayout share_btn;
                    View share_line=null;
                    TextView  publish_time;
                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View inflate = layoutInflater.inflate(R.layout.list_item_news_card, null);
                        mContent= ButterKnife.findById(inflate,R.id.content);
                        img= ButterKnife.findById(inflate,R.id.img);
                        gif_hit=ButterKnife.findById(inflate,R.id.gif_hint);
                        share_btn= ButterKnife.findById(inflate,R.id.share_btn);
                        share_line=ButterKnife.findById(inflate,R.id.share_line);
                        publish_time=ButterKnife.findById(inflate,R.id.publish_time);
                        return inflate;
                    }

                    @Override
                    public void showData(final int position, final ItemEntity itemData) {
                        if(itemData!=null ){
                            Log.e(TAG,"===>"+itemData.getUpdated());
                            if(!StringUtil.isEmpty(itemData.getUpdated())){
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                try {
                                    Date date = format.parse(itemData.getUpdated());
                                    publish_time.setText("发布于 "+format.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    Log.e(TAG,"showData===>"+e);
                                }

                            }



                            if(StringUtil.isEmpty(itemData.getContent())) {
                                mContent.setText(StringUtil.getStr(itemData.getTitle()));
                            }else {
                                share_btn.setVisibility(View.VISIBLE);
                                share_line.setVisibility(View.VISIBLE);
                                share_btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Uri uri=null;
//                                    if(!StringUtil.isEmpty(image_url)){
//                                        uri= Uri.parse(image_url);
//                                    }
                                        ShareUtil.share(getActivity(),mContent.getText().toString(), uri);
                                    }
                                });
                                mContent.setText(StringUtil.getStr(itemData.getContent()));
                            }
                            final String image_url = itemData.getImage_url();

                            if(!StringUtil.isEmpty(image_url)) {
                                img.setVisibility(View.VISIBLE);
                                img.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String image_url1 = itemData.getImage_url();
                                        if (!image_url.contains(".gif")) {
                                            navigateToImagePage(v, position, itemData);
                                        } else {
                                            navigateToGifPage(v, position, itemData);
                                        }
                                    }
                                });
                                if (image_url.contains(".gif")) {
                                    gif_hit.setVisibility(View.VISIBLE);
                                }
                                ImageLoader.getInstance().displayImage(image_url, img, new ImageLoadingListener() {
                                    @Override
                                    public void onLoadingStarted(String imageUri, View view) {

                                    }

                                    @Override
                                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                                    }

                                    @Override
                                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                        int width = loadedImage.getWidth();
                                        int height = loadedImage.getHeight();
                                        ImageView iv = (ImageView) view;
                                        int max_width = iv.getWidth();
//                                        int maxHeight = iv.getMaxHeight();
                                        ViewGroup.LayoutParams layoutParams = iv.getLayoutParams();
                                        layoutParams.height = (int) (max_width * (height * 1f / width));
//                                        iv.setMaxHeight(maxHeight);
                                        iv.setLayoutParams(layoutParams);
                                    }

                                    @Override
                                    public void onLoadingCancelled(String imageUri, View view) {

                                    }
                                });
                            }


                        }
                    }
                };
            }
        });
        mListView.setOnItemClickListener(this);
        mListView.setOnLoadMoreListener(this);
        mListView.setAdapter(mListViewAdapter);
        mListView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(),true,true));

        mSwipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void  navigateToGifPage(View view,int position,ItemEntity itemEntity){
        Bundle extras = new Bundle();
        extras.putString(GifDetailActivity.INTENT_GIF_URL_TAG, itemEntity.getImage_url());
        readyGo(GifDetailActivity.class, extras);
        getActivity().overridePendingTransition(0, 0);
    }
    /**
     * 跳转
     */
    private void navigateToImagePage(View view,int position,ItemEntity itemEntity) {
        Rect frame = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int[] location = new int[2];
        view.getLocationOnScreen(location);
        location[1] += statusBarHeight;

        int width = view.getWidth();
        int height = view.getHeight();

        if (null != mListViewAdapter) {
            if (position >= 0 && position < mListViewAdapter.getDataList().size()) {
//                mNewsListPresenter.onItemClickListener(position,
//                        itemEntity,
//                        location[0],
//                        location[1],
//                        width,
//                        height);
                Bundle extras = new Bundle();
                extras.putString(ImagesDetailActivity.INTENT_IMAGE_URL_TAG, itemEntity.getImage_url());
                extras.putInt(ImagesDetailActivity.INTENT_IMAGE_X_TAG, location[0]);
                extras.putInt(ImagesDetailActivity.INTENT_IMAGE_Y_TAG, location[1]);
                extras.putInt(ImagesDetailActivity.INTENT_IMAGE_W_TAG, width);
                extras.putInt(ImagesDetailActivity.INTENT_IMAGE_H_TAG, height);
                readyGo(ImagesDetailActivity.class, extras);
                getActivity().overridePendingTransition(0, 0);
            }
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_news_list;
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
        //mSwipeRefreshLayout.setEnabled(true);
        //判断数据是否  成功 为空 错误


        if (null != mSwipeRefreshLayout) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if(responseNewsListEntity==null ){

            if(null != mListViewAdapter && mListViewAdapter.getDataList().size()>0){
                FToash.getInstant().showNormalToash("刷新数据失败！");
                return;
            }else {
                showErrorPager("");
                return ;
            }

        }

        if(!StringUtil.compare(Constants.SUCCESS_CODE,responseNewsListEntity.getCode())){
            if(null != mListViewAdapter && mListViewAdapter.getDataList().size()>0){
                FToash.getInstant().showNormalToash("刷新数据失败！");
                return;
            }else {
                showErrorPager("");
                return ;
            }
        }
        if (null != responseNewsListEntity) {
            if (null != mListViewAdapter) {
                LogUtil.e(TAG,"==>refreshListData"+responseNewsListEntity);
                mListViewAdapter.getDataList().clear();
                mListViewAdapter.getDataList().addAll(responseNewsListEntity.getResult());
                mListViewAdapter.notifyDataSetChanged();
            }

            if (null != mListView) {
                if (UriHelper.getInstance().calculateTotalPages(responseNewsListEntity.getTotalNum()) > mCurrentPage) {
                    mListView.setCanLoadMore(true);
                } else {
                    mListView.setCanLoadMore(false);
                }
            }
        }
    }
    //加载列表数据
    @Override
    public void addMoreListData(NewsEntity responseNewsListEntity) {
        if (null != mListView) {
            mListView.onLoadMoreComplete();
        }
        if(responseNewsListEntity==null){
            FToash.getInstant().showNormalToash("加载更多数据失败！");
            return;
        }
        if(!StringUtil.compare(Constants.SUCCESS_CODE,responseNewsListEntity.getCode())){
            FToash.getInstant().showNormalToash("刷新数据失败！");
            return;
        }
        if (null != responseNewsListEntity) {
            if (null != mListViewAdapter) {
                mListViewAdapter.getDataList().addAll(responseNewsListEntity.getResult());
                mListViewAdapter.notifyDataSetChanged();
            }

            if (null != mListView) {
                if (UriHelper.getInstance().calculateTotalPages(responseNewsListEntity.getTotalNum()) > mCurrentPage) {
                    mListView.setCanLoadMore(true);
                } else {
                    mListView.setCanLoadMore(false);
                }
            }
        }
    }
    //跳转
    @Override
    public void navigateToNewsDetail(int position, ItemEntity entity, int x, int y, int width, int height) {

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
    public void onLoadMore() {
        LogUtil.e(TAG,"===>onLoadMore");
        mCurrentPage++;
        mNewsListPresenter.loadListData(TAG_LOG, Constants.EVENT_LOAD_MORE_DATA, mCurrentCategory, mCurrentPage, true);
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

    /**
     * list view  item clicked
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //查看详情

    }
}
