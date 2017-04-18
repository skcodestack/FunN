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

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;


import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import butterknife.InjectView;
import github.customlibrary.eventbus.EventCenter;
import github.customlibrary.netstatus.NetUtil;
import github.customlibrary.wigdets.SmoothImageView;
import github.funn.R;
import github.funn.common.ApiConstants;
import github.funn.ui.activity.base.BaseActivity;
import github.funn.utils.FToash;
import github.funn.utils.ImageUtil;
import github.funn.utils.PopupWindowUtil;
import uk.co.senab.photoview.PhotoViewAttacher;


public class ImagesDetailActivity extends BaseActivity implements View.OnClickListener {

    public static final String INTENT_IMAGE_URL_TAG = "INTENT_IMAGE_URL_TAG";
    public static final String INTENT_IMAGE_X_TAG = "INTENT_IMAGE_X_TAG";
    public static final String INTENT_IMAGE_Y_TAG = "INTENT_IMAGE_Y_TAG";
    public static final String INTENT_IMAGE_W_TAG = "INTENT_IMAGE_W_TAG";
    public static final String INTENT_IMAGE_H_TAG = "INTENT_IMAGE_H_TAG";

    private String mImageUrl;
    private int mLocationX;
    private int mLocationY;
    private int mWidth;
    private int mHeight;
    private Bitmap curBitmap=null;
    private String url="";

    @InjectView(R.id.images_detail_smooth_image)
    SmoothImageView mSmoothImageView;


    @Override
    protected void getBundleExtras(Bundle extras) {
        mImageUrl = extras.getString(INTENT_IMAGE_URL_TAG);
        mLocationX = extras.getInt(INTENT_IMAGE_X_TAG);
        mLocationY = extras.getInt(INTENT_IMAGE_Y_TAG);
        mWidth = extras.getInt(INTENT_IMAGE_W_TAG);
        mHeight = extras.getInt(INTENT_IMAGE_H_TAG);
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    public void onBackPressed() {
        mSmoothImageView.transformOut();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(0, 0);
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_images_detail;
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
        mSmoothImageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
        mSmoothImageView.transformIn();

        ImageLoader.getInstance().displayImage(mImageUrl, mSmoothImageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                curBitmap=loadedImage;
                url=imageUri;

                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        pictureDeal();
                        return false;
                    }
                });
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });

        mSmoothImageView.setOnTransformListener(new SmoothImageView.TransformListener() {
            @Override
            public void onTransformComplete(int mode) {
                if (mode == 2) {
                    finish();
                }
            }
        });

        mSmoothImageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v2) {
                mSmoothImageView.transformOut();
            }
        });
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
        takePhotoPopWin.showAtLocation(mSmoothImageView, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
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
            //保存图片
//            curBitmap
            if(curBitmap!=null && !TextUtils.isEmpty(url)) {
                int start = url.lastIndexOf("/");
                int end = url.lastIndexOf(".");
                String name = url.substring(start, end);
                Log.e("tag","===>"+name);
                boolean b=ImageUtil.saveBitmap(ApiConstants.Paths.FILE_DOWNLOAD_PATH,name, curBitmap);
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
