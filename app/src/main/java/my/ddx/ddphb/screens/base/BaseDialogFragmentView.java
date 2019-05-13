package my.ddx.ddphb.screens.base;

import my.ddx.mvp.DialogFragmentView;
import my.ddx.mvp.IPresenter;
import my.ddx.mvp.IView;

/**
 * BaseDialogFragmentView
 * Created by deler on 18.03.17.
 */

public abstract class BaseDialogFragmentView<V extends IView, P extends IPresenter<V>> extends DialogFragmentView<V, P> {
}
