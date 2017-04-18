/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package github.funn.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.facebook.drawee.view.SimpleDraweeView;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;

import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;
import github.customlibrary.eventbus.EventCenter;
import github.customlibrary.netstatus.NetUtil;
import github.customlibrary.utils.HttpUtil;
import github.customlibrary.utils.LogUtil;
import github.customlibrary.wigdets.SmoothImageView;
import github.funn.R;
import github.funn.common.ApiConstants;
import github.funn.ui.activity.base.BaseActivity;
import github.funn.utils.DataCacheUtil;
import github.funn.utils.FToash;
import github.funn.utils.FrescoUtil;
import github.funn.utils.PopupWindowUtil;
import github.funn.utils.StringUtil;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import uk.co.senab.photoview.PhotoViewAttacher;


public class GifDetailActivity extends BaseActivity implements View.OnClickListener {

    private final String TAG=this.getClass().getSimpleName();
    public static final String INTENT_GIF_URL_TAG = "INTENT_IMAGE_URL_TAG";
    public static final String INTENT_IMAGE_X_TAG = "INTENT_IMAGE_X_TAG";
    public static final String INTENT_IMAGE_Y_TAG = "INTENT_IMAGE_Y_TAG";
    public static final String INTENT_IMAGE_W_TAG = "INTENT_IMAGE_W_TAG";
    public static final String INTENT_IMAGE_H_TAG = "INTENT_IMAGE_H_TAG";

    private String mImageUrl;
    private int mLocationX;
    private int mLocationY;
    private int mWidth;
    private int mHeight;
    private byte[]  fileByte=null;
    private String  file_url="";

    @InjectView(R.id.gif_image_view)
    GifImageView mGifImageView;
    @InjectView(R.id.gif_loading_view)
    GifImageView loadingView;
    @Override
    protected void getBundleExtras(Bundle extras) {
        mImageUrl = extras.getString(INTENT_GIF_URL_TAG);
//        mLocationX = extras.getInt(INTENT_IMAGE_X_TAG);
//        mLocationY = extras.getInt(INTENT_IMAGE_Y_TAG);
//        mWidth = extras.getInt(INTENT_IMAGE_W_TAG);
//        mHeight = extras.getInt(INTENT_IMAGE_H_TAG);
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    public void onBackPressed() {
//        mGifImageView.recyc
//        Drawable drawable = mGifImageView.getDrawable();
//        if(drawable!=null){
//        }
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(0, 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpUtil.getClient().cancelAllRequests(true);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_gifs_detail;
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

        //下载pdf
        LogUtil.e(TAG,"gifurl=>"+mImageUrl);
        // 指定文件类型
        String[] fileTypes = new String[] { "image/png", "image/jpeg","image/gif"};
        try {

            mGifImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            loadingView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });


            HttpUtil.get(mImageUrl, new BinaryHttpResponseHandler(fileTypes) {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {


                    try {
                        if(loadingView==null || mGifImageView==null){
                            return;
                        }
                        if (statusCode == 200 && binaryData != null && binaryData.length > 0) {
                            loadingView.setVisibility(View.GONE);
                            mGifImageView.setVisibility(View.VISIBLE);
                            GifDrawable gifFromBytes = null;

                            gifFromBytes = new GifDrawable(binaryData);
                            mGifImageView.setImageDrawable(gifFromBytes);

                            fileByte=binaryData;
                            file_url= mImageUrl;
                            mGifImageView.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {
                                    pictureDeal();
                                    return false;
                                }
                            });
                        } else {
                            loadingView.setBackgroundResource(R.drawable.gif_load_error_white);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {
                    if(loadingView==null || mGifImageView==null){
                        return;
                    }
                    loadingView.setBackgroundResource(R.drawable.gif_load_error_white);
                }
            });

        }catch (Exception ex){
            ex.printStackTrace();
            Log.e(TAG,"gif dwon :"+ex);
        }
    }

    @Override
    protected void onNetworkConnected(NetUtil.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

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

    WindowManager.LayoutParams params=null;
    PopupWindowUtil takePhotoPopWin=null;
    private void pictureDeal(){
        takePhotoPopWin = new PopupWindowUtil(this, this);
//        设置Popupwindow显示位置（从底部弹出）
        takePhotoPopWin.showAtLocation(mGifImageView, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        params = getWindow().getAttributes();
        //当弹出Popupwindow时，背景变半透明
//        params.alpha=0.7f;
        getWindow().setAttributes(params);
        //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
        takePhotoPopWin.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params = getWindow().getAttributes();
                params.alpha=1f;
                getWindow().setAttributes(params);
            }
        });



    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_save){
            //保存gif
            if(fileByte!=null && !StringUtil.isEmpty(file_url)) {
                Log.e("tag","==>"+file_url);
                int start = file_url.lastIndexOf("/");
                int end = file_url.lastIndexOf(".");
                String name = file_url.substring(start,end);
                String extension = file_url.substring(end);
                Log.e("tag","==>"+name+":"+extension);
                boolean b=DataCacheUtil.saveFileToDisk(ApiConstants.Paths.FILE_DOWNLOAD_PATH,name,extension,fileByte);
                if(b){
                    FToash.getInstant().showNormalToash("保存成功");
                }else {
                    FToash.getInstant().showErrorToash("保存失败");
                }
            }else {
                FToash.getInstant().showErrorToash("保存失败");
            }
            if(takePhotoPopWin!=null){
                takePhotoPopWin.dismiss();
            }
        }
    }
}
