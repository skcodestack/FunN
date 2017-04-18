package github.customlibrary.netstatus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.ArrayList;

import github.customlibrary.utils.LogUtil;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/3.
 */

public class NetStateReceiver extends BroadcastReceiver {

    public final static  String  CUSTOM_NET_CHANGE_ACTION="github.customlibrary.net.conn.CONNECTIVITY_CHANGE";
    private final static String  ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private final static String TAG = NetStateReceiver.class.getSimpleName();

    private static boolean isNetAvailable = false;
    private static NetUtil.NetType mNetType;
    private static ArrayList<NetChangeObserver> mNetChangeObservers = new ArrayList<NetChangeObserver>();
    private static BroadcastReceiver mBroadcastReceiver;

    /**
     * signal
     * @return
     */
    private static BroadcastReceiver getReceiver() {
        if (null == mBroadcastReceiver) {
            synchronized (NetStateReceiver.class) {
                if (null == mBroadcastReceiver) {
                    mBroadcastReceiver = new NetStateReceiver();
                }
            }
        }
        return mBroadcastReceiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mBroadcastReceiver = NetStateReceiver.this;
        if(intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION) || intent.getAction().equalsIgnoreCase(CUSTOM_NET_CHANGE_ACTION)){
            if(!NetUtil.isNetworkAvailable(context)){
                LogUtil.i(TAG,"----net disconnection----");
                isNetAvailable=false;
            }else{
                LogUtil.i(TAG,"----net connection----");
                isNetAvailable=true;
            }


        }

    }

    /**
     * 注冊網絡狀態監聽
     * @param mContext
     */
    public static void registerNetworkStateReceiver(Context mContext) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(CUSTOM_NET_CHANGE_ACTION);
        filter.addAction(ANDROID_NET_CHANGE_ACTION);
        mContext.getApplicationContext().registerReceiver(getReceiver(), filter);
    }

    /**
     * 檢查當前網絡狀態（check current net status）
     * @param mContext
     */
    public static void checkNetworkState(Context mContext) {
        Intent intent = new Intent();
        intent.setAction(CUSTOM_NET_CHANGE_ACTION);
        mContext.sendBroadcast(intent);
    }

    /**
     * 注銷網絡狀態監聽
     * @param mContext
     */
    public static void unRegisterNetworkStateReceiver(Context mContext) {
        if (mBroadcastReceiver != null) {
            try {
                mContext.getApplicationContext().unregisterReceiver(mBroadcastReceiver);
            } catch (Exception e) {
                LogUtil.d(TAG, e.getMessage());
            }
        }

    }
    /**
     * 獲取當前網絡狀態
     * @return
     */
    public static boolean isNetworkAvailable() {
        return isNetAvailable;
    }

    public static NetUtil.NetType getAPNType() {
        return mNetType;
    }

    /**
     * 通知所有观察者
     */
    private void notifyObserver() {
        if (!mNetChangeObservers.isEmpty()) {
            int size = mNetChangeObservers.size();
            for (int i = 0; i < size; i++) {
                NetChangeObserver observer = mNetChangeObservers.get(i);
                if (observer != null) {
                    if (isNetworkAvailable()) {
                        observer.onNetConnection(mNetType);
                    } else {
                        observer.onNetDisConnect();
                    }
                }
            }
        }
    }
    /**
     * 注冊網絡狀態觀察者
     * @param observer
     */
    public static void registerObserver(NetChangeObserver observer) {
        if (mNetChangeObservers == null) {
            mNetChangeObservers = new ArrayList<NetChangeObserver>();
        }
        mNetChangeObservers.add(observer);
    }

    /**
     * 移除網絡狀態觀察者
     * @param observer
     */
    public static void removeRegisterObserver(NetChangeObserver observer) {
        if (mNetChangeObservers != null) {
            if (mNetChangeObservers.contains(observer)) {
                mNetChangeObservers.remove(observer);
            }
        }
    }
}
