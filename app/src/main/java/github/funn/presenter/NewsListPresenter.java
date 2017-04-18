package github.funn.presenter;

import github.funn.bean.BaseEntity;
import github.funn.bean.ItemEntity;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/6.
 */

public interface NewsListPresenter {
    void loadListData(String requestTag, int event_tag, String keywords, int page, boolean isSwipeRefresh);

    void onItemClickListener(int position, ItemEntity entity, int x, int y, int width, int height);
}
