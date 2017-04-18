package github.funn.view;

import github.funn.bean.BaseEntity;
import github.funn.bean.ItemEntity;
import github.funn.bean.NewsEntity;
import github.funn.view.base.BaseView;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/3.
 */

public interface NewsListView  extends BaseView{
    void refreshListData(NewsEntity responseNewsListEntity);

    void addMoreListData(NewsEntity responseNewsListEntity);

    void navigateToNewsDetail(int position, ItemEntity entity, int x, int y, int width, int height);
}
