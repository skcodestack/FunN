package github.funn.interactor.impl;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import github.customlibrary.utils.LogUtil;
import github.customlibrary.utils.VolleyHelper;
import github.customlibrary.volley.GsonRequest;
import github.funn.bean.NewsEntity;
import github.funn.common.Constants;
import github.funn.common.UriHelper;
import github.funn.interactor.CommonListInteractor;
import github.funn.listeners.BaseMultiLoadedListener;
import github.funn.utils.DataCacheUtil;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/6.
 */

public class NewsListInteractorImpl implements CommonListInteractor {
    BaseMultiLoadedListener<NewsEntity> loadedListener=null;
    public NewsListInteractorImpl(BaseMultiLoadedListener<NewsEntity> loadedListener){
        this.loadedListener=loadedListener;
    }
    @Override
    public void getCommonListData(boolean isSwipeRefresh,String requestTag, final int event_tag, String keywords,final int page) {
//        com.android.volley.toolbox
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        String request_url=UriHelper.getInstance().getNewsListUri(keywords, page);
        //
        if(!isSwipeRefresh && page==1 && event_tag== Constants.EVENT_REFRESH_DATA){
            //不是刷新，并且是请求的第一页，则读取缓存
            Object object = DataCacheUtil.readObjectToFile(request_url);
            if(object!=null && object instanceof NewsEntity){
                loadedListener.onSuccess(event_tag, (NewsEntity) object);
            }else {
                requestDateFromNet(request_url,requestTag,event_tag,page==1);
            }

        }else {
            requestDateFromNet(request_url,requestTag,event_tag,page==1);
        }


    }

    /**
     * 请求数据
     * @param request_url
     * @param requestTag
     * @param event_tag
     * @param isCache   是否缓存
     */
    private  void  requestDateFromNet(final String request_url, String requestTag, final int event_tag, final boolean isCache){
        GsonRequest<NewsEntity> gsonRequest = new GsonRequest<NewsEntity>(
                request_url,
                null,
                new TypeToken<NewsEntity>() {
                }.getType(),
                new Response.Listener<NewsEntity>() {
                    @Override
                    public void onResponse(NewsEntity response) {
                        loadedListener.onSuccess(event_tag, response);
                        if(isCache){
                            DataCacheUtil.writeObjectToFile(request_url,response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadedListener.onException(event_tag,error.getMessage());
                    }
                }
        );

        gsonRequest.setShouldCache(true);
        gsonRequest.setTag(requestTag);
        VolleyHelper.getInstance().getRequestQueue().add(gsonRequest);
    }
}
