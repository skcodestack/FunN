package github.funn.common;

import android.os.Environment;

import java.io.File;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/6.
 */

public class ApiConstants {
    private static final String APP_NAME="fun";
    public static final class Urls {
            //http://127.0.0.1:8080/FunServer/img?page=2&pagesize=20
            public static final String BASE_LIST_URL="http://192.168.56.1:8080/FunServer/";
    }

    public static final class Paths {

        public static final String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
        public static final String IMAGE_LOADER_CACHE_PATH = "/SimplifyReader/Images/";

        public static final String OBJECT_CACHE_PATH=BASE_PATH+ File.separatorChar+
                APP_NAME+File.separatorChar+"data"+File.separatorChar;

        public static final String FILE_DOWNLOAD_PATH=BASE_PATH+ File.separatorChar+
                APP_NAME+File.separatorChar+"file";

    }

    public static final class Integers {
        public static final int PAGE_LAZY_LOAD_DELAY_TIME_MS = 200;
    }
}
