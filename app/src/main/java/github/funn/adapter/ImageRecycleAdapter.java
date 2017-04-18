package github.funn.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


import java.util.ArrayList;
import java.util.List;

import github.funn.R;
import github.funn.bean.ItemEntity;
import github.funn.listeners.OnItemClickListener;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/13.
 */

public class ImageRecycleAdapter extends StaggeredGridLayoutAdapter<ItemEntity> {
    OnItemClickListener<ItemEntity> listener=null;
    public ImageRecycleAdapter(OnItemClickListener listener) {
        super(new ArrayList<ItemEntity>());
        this.listener=listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_image_card, null);
        return new ItemViewHolder(convertView);
    }

    @Override
    protected void onBindHeaderView(View headerView) {
        Log.e("TAG","这是HeadView数据绑定的过程");
    }

    @Override
    protected void onBindFooterView(View footerView) {
        Log.e("TAG","这是FootView数据绑定的过程");
    }

    @Override
    protected void onBindItemView(RecyclerView.ViewHolder holder, final ItemEntity item) {
        final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
//        ImageLoader.getInstance().displayImage(item.getImage_url(),itemViewHolder.imageView);
        ImageLoader.getInstance().displayImage(item.getImage_url(),itemViewHolder.imageView, new ImageLoadingListener() {
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
        itemViewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onClick(v,item);
                }
            }
        });

    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        View rootView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            rootView=itemView;
            imageView = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
