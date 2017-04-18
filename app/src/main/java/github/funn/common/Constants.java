package github.funn.common;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/6.
 */

public class Constants {
    public static final int EVENT_BEGIN = 0X100;
    public static final int EVENT_REFRESH_DATA = EVENT_BEGIN + 10;
    public static final int EVENT_LOAD_MORE_DATA = EVENT_BEGIN + 20;
    public static final int EVENT_START_PLAY_MUSIC = EVENT_BEGIN + 30;
    public static final int EVENT_STOP_PLAY_MUSIC = EVENT_BEGIN + 40;

    //请求每页数据条数
    public static final int    pagesize=20;
    //请求成功code值
    public static final String SUCCESS_CODE="200";
}
