package github.funn;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import github.customlibrary.base.BaseAppManager;
import github.customlibrary.utils.VolleyHelper;
import github.funn.common.ApiConstants;
import github.funn.utils.FrescoHelper;
import github.funn.utils.ImageLoaderHelper;


/**
 * 当前项目
 * @author root
 *
 */
public class FunNApplication extends Application {

	private  static  FunNApplication  baseapplication;
	private  static int myTid;
	private  static Handler mhandler;
	static RefWatcher refWatcher;
	static Context mContext;

	public static RefWatcher getRefWatcher(Context context) {
		FunNApplication application = (FunNApplication) context.getApplicationContext();
		return application.refWatcher;
	}

	public FunNApplication() {
		baseapplication=this;
		mhandler = new Handler();
		 myTid = android.os.Process.myTid();
	}
	@Override
	public void onCreate() {
		super.onCreate();
		Fresco.initialize(this, FrescoHelper.getsImagePipelineConfig(this));
		refWatcher= LeakCanary.install(this);
		FunNApplication.mContext=getApplicationContext();
		VolleyHelper.getInstance().init(getApplication());
		ImageLoader.getInstance().init(ImageLoaderHelper.getInstance(this).getImageLoaderConfiguration(ApiConstants.Paths.IMAGE_LOADER_CACHE_PATH));

	}
	/**u
	 * 得到上下文
	 * @return
	 */
	public  static  Context getApplication(){
		return  baseapplication;
	}
	/**
	 * 得到主线程ID
	 * @return
	 */
	public  static  int  getThreadId(){
		return  myTid;
	}
	/**
	 * 得到handler
	 * @return
	 */
	public  static  Handler  getHandler(){
		return  mhandler;
	}
	@Override
	public void onLowMemory() {
		android.os.Process.killProcess(android.os.Process.myPid());
		super.onLowMemory();
	}

	public void exitApp() {
		BaseAppManager.getInstance().clear();
		System.gc();
		android.os.Process.killProcess(android.os.Process.myPid());
	}
}
