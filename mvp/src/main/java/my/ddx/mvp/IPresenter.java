package my.ddx.mvp;


import androidx.annotation.NonNull;

/**
 * IPresenter
 * Created by deler on 21.02.17.
 */

public interface IPresenter<T extends IView> {

    void onAttachView(@NonNull T view);

    void onDetachView();

    void onDestroy();

}
