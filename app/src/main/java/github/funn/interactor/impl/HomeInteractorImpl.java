package github.funn.interactor.impl;

import github.customlibrary.base.BaseLazyFragment;
import github.funn.interactor.HomeInteractor;
import github.funn.ui.fragment.NewsContainerFragment;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/3.
 */

public class HomeInteractorImpl implements HomeInteractor{


    @Override
    public BaseLazyFragment getPagerFragments() {

        return new NewsContainerFragment();
    }
}
