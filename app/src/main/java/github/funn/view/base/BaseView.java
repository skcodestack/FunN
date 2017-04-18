package github.funn.view.base;

/**
 * Author root
 * Date: 2017/3/2.
 */

public interface BaseView {

    void  showLoadingPager(String msg);
    void  hideLoadingPager();
    void  showErrorPager(String msg);
    void  showExceptionPager(String msg);
    void  showNetErrorPager();
}
