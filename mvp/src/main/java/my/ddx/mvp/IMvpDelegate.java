package my.ddx.mvp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * IMvpDelegate
 * Created by deler on 21.02.17.
 */

public interface IMvpDelegate<V extends IView, Presenter extends IPresenter<V>> {

    void onCreate(@Nullable Bundle savedInstanceState);

    void onViewCreated();

    void onResume();

    void onPause();

    void onSaveInstanceState(Bundle outState);

    void onDestroyView();

    void onDestroy();

    Presenter getPresenter();

    @Nullable
    IExtendPresenter getExtendPresenter();

    interface Callback<V extends IView, Presenter extends IPresenter<V>> {
        Presenter createPresenter();

        boolean isNeedRetainPresenter();

        boolean isNeedDestroyRetainedPresenter();

        @NonNull
        String getRetainPresenterTag();

        V view();
    }
}
