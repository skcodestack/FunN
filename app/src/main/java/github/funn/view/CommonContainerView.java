package github.funn.view;

import java.util.List;

import github.funn.bean.NewsCategoryEntity;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/3.
 */

public interface CommonContainerView {
    void initializePagerViews(List<NewsCategoryEntity> categoryList);
}
