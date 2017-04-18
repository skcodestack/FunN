package github.funn.utils;

import android.graphics.Color;
import android.graphics.Typeface;

import com.github.johnpersano.supertoasts.SuperToast;

import github.funn.FunNApplication;


/**
 * Author root
 * Date: 2016/12/1.
 */
public class FToash {

    private static FToash ccToash=new FToash();
    private FToash(){}

    public static FToash getInstant(){
        return  ccToash;
    }


    public  void  showErrorToash(int ids){
        showToash(FunNApplication.getApplication().getResources().getString(ids), SuperToast.Background.RED);
    }
    public  void  showNormalToash(int ids){
        showToash(FunNApplication.getApplication().getResources().getString(ids), SuperToast.Background.BLUE);
    }
    public  void  showErrorToash(String msg){
        showToash(msg, SuperToast.Background.RED);
    }
    public  void  showNormalToash(String msg){
        showToash(msg, SuperToast.Background.BLUE);
    }

    public  void  showToash(int ids,int color){
        showToash(FunNApplication.getApplication().getResources().getString(ids),color);
    }

    public  void  showToash(String msg, int color){
        SuperToast.cancelAllSuperToasts();
        SuperToast superToast=new SuperToast(FunNApplication.getApplication());
        superToast.setAnimations(SuperToast.Animations.FLYIN);
        superToast.setDuration(SuperToast.Duration.SHORT);
        superToast.setBackground(color);
        superToast.setText(msg);
        superToast.setTextSize(SuperToast.TextSize.SMALL);
        superToast.setTextColor(Color.parseColor("#ffffff"));

//        superToast.setTypefaceStyle(Typeface.SERIF);
        superToast.show();
    }

    public  static  void  cancelAllSuperToasts(){
        SuperToast.cancelAllSuperToasts();
    }

}
