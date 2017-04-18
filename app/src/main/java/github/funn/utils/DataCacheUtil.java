package github.funn.utils;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import github.customlibrary.utils.LogUtil;
import github.customlibrary.utils.MD5Utils;
import github.funn.common.ApiConstants;

import static android.R.attr.bitmap;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/4/13.
 */

public class DataCacheUtil {

    public static void writeObjectToFile(String key,Object data){

        FileOutputStream fout=null;
        ObjectOutputStream out=null;

        String md5 = MD5Utils.enCode(key);
        if(StringUtil.isEmpty(md5)){
            return;
        }
        String dir = obtainObjectCacheDir();
        if(StringUtil.isEmpty(dir)){
            return;
        }
        File cacheFile=new File(dir,md5);

        try {
            fout=new FileOutputStream(cacheFile);
            out=new ObjectOutputStream(fout);
            out.writeObject(data);
            out.flush();


        } catch (Exception e) {
            e.printStackTrace();
        }finally {

            if(fout!=null){
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static Object readObjectToFile(String key){

        String md5 = MD5Utils.enCode(key);
        if(StringUtil.isEmpty(md5)){
            return null;
        }
        String dir = obtainObjectCacheDir();
        if(StringUtil.isEmpty(dir)){
            return null;
        }
        File cacheFile=new File(dir,md5);
        if(!cacheFile.exists()){
            return null;
        }
        FileInputStream fin=null;
        ObjectInputStream oin=null;
        try{
            fin=new FileInputStream(cacheFile);
            oin=new ObjectInputStream(fin);
            return oin.readObject();
        }catch (Exception ex){
            return null;
        }finally {
            if(fin!=null){
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(oin!=null){
                try {
                    oin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static String  obtainObjectCacheDir(){
        String path = ApiConstants.Paths.OBJECT_CACHE_PATH;
        File dir=new File(path);
        if(dir.mkdirs()){

        }
        return dir.getAbsolutePath();
    }


    public static boolean saveFileToDisk(String dir,String filename,String extension,byte[] buf){
        File newdir = new File(dir);
        if (!newdir.mkdirs()) {
        }

        String newFileName = MD5Utils.enCode(filename);
        if(StringUtil.isEmpty(newFileName)){
            SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
            newFileName="extension_"+format.format(new Date());
        }

        //文件保存路径
        final String uploadFilePath=dir+newFileName+extension;
        Log.e("tag","===>"+uploadFilePath);
        FileOutputStream out=null;

        try {
            out=new FileOutputStream(uploadFilePath);
//            bitmap.compress(Bitmap.CompressFormat.PNG,90,out);
            out.write(buf);
            out.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("cache","==>"+e);
        }finally {
            try {
                if(out!=null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

}
