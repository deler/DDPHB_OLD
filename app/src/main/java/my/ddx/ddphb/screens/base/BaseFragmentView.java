package my.ddx.ddphb.screens.base;

import my.ddx.mvp.FragmentView;
import my.ddx.mvp.IPresenter;
import my.ddx.mvp.IView;

/**
 * BaseFragmentView
 * Created by deler on 18.03.17.
 */

public abstract class BaseFragmentView<V extends IView, P extends IPresenter<V>> extends FragmentView<V, P> {
}
