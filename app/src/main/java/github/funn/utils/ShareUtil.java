package github.funn.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/4/13.
 */

public class ShareUtil {
    public   static void share(Activity mActivity,String content, Uri uri){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        if(uri!=null){
            //uri 是图片的地址
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("image/*");
            //当用户选择短信时使用sms_body取得文字
            shareIntent.putExtra("sms_body", content);
        }else{
            shareIntent.setType("text/plain");
        }
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        //自定义选择框的标题
        //startActivity(Intent.createChooser(shareIntent, "邀请好友"));
        //系统默认标题
        mActivity.startActivity(shareIntent);
    }
}
