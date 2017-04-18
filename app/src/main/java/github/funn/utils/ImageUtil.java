package github.funn.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import github.customlibrary.utils.MD5Utils;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/13.
 */

public class ImageUtil {

    public static boolean saveBitmap(String dir, String filename,Bitmap bitmap){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File newdir = new File(dir);
            if (!newdir.mkdirs()) {
            }
            String fileName = MD5Utils.enCode(filename)+".png";
            if(StringUtil.isEmpty(fileName)){
                SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
                fileName="image_"+format.format(new Date())+".png";
            }

            //文件保存路径
            final String uploadFilePath=dir+fileName;
            Log.e("tag","===>"+uploadFilePath);
            FileOutputStream out=null;

            try {
                out=new FileOutputStream(uploadFilePath);
                bitmap.compress(Bitmap.CompressFormat.PNG,90,out);
                out.flush();
                out.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        return false;

    }
}
