package github.funn.utils;

import android.text.TextUtils;

/**
 * Author root
 * Date: 2017/1/9.
 */
public class StringUtil {

    public  static String getStr(String str){
        if(!TextUtils.isEmpty(str)){
            return str.trim();
        }
        return "";
    }
    public  static boolean  isEmpty(String str){
        if(!TextUtils.isEmpty(getStr(str))){
            return false;
        }
        return true;
    }
    public  static boolean  compare(String s, String d){
        if(getStr(s).equals(getStr(d))){
            return true;
        }
        return false;
    }
}
