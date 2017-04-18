package github.funn.common;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/7.
 */

public class UriHelper {

    private  static volatile UriHelper mUriHelper=null;
    private UriHelper(){}

    public static UriHelper getInstance(){
        if(mUriHelper==null) {
            synchronized (UriHelper.class) {
                if(mUriHelper==null){
                    mUriHelper=new UriHelper();
                }
            }
        }
        return mUriHelper;
    }
    public int calculateTotalPages(int totalNumber) {
        if (totalNumber > 0) {
            return totalNumber % Constants.pagesize != 0 ? (totalNumber / Constants.pagesize + 1) : totalNumber / Constants.pagesize;
        } else {
            return 0;
        }
    }
    public  String  getNewsListUri(String keywords,int page){
        //?key=您申请的KEY&page=1&pagesize=10
        StringBuilder sb=new StringBuilder();
        sb.append(ApiConstants.Urls.BASE_LIST_URL).append(keywords).append("?page=").append(page).append("&pagesize=").append(Constants.pagesize);
        return sb.toString();
    }
}
