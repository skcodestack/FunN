package github.funn.interactor;

import android.content.Context;

import java.util.List;

import github.funn.bean.NewsCategoryEntity;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/3.
 */

public interface CommonContainerInteractor {

    List<NewsCategoryEntity> getCommonCategoryList(Context context);

}
