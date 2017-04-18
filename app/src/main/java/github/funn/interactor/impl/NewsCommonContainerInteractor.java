package github.funn.interactor.impl;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import github.funn.R;
import github.funn.bean.NewsCategoryEntity;
import github.funn.interactor.CommonContainerInteractor;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/3.
 */

public class NewsCommonContainerInteractor implements CommonContainerInteractor{

    public List<NewsCategoryEntity> getCommonCategoryList(Context context){
        List<NewsCategoryEntity> list=new ArrayList<>();
        String[] names = context.getResources().getStringArray(R.array.news_category_list_name);
        String[] indexs = context.getResources().getStringArray(R.array.news_category_list_index);
        String[] remarks = context.getResources().getStringArray(R.array.news_category_list_remark);
        String[] paths = context.getResources().getStringArray(R.array.news_category_list_path);
//        for (String str: names) {
//            list.add(str);
//        }
        NewsCategoryEntity bean=null;
        int count=names.length;
        for (int i=0;i<count;i++){
            bean=new NewsCategoryEntity();
            bean.setName(names[i]);
            bean.setIndex(indexs[i]);
            bean.setPath(paths[i]);
            bean.setRemark(remarks[i]);
            list.add(bean);
        }

        return list;
    }

}
