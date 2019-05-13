package my.ddx.mvp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * DialogFragmentView
 * <p>
 * Created by deler on 21.02.17.
 */

public abstract class DialogFragmentView<V extends IView, Presenter extends IPresenter<V>> extends DialogFragment implements IView, IMvpDelegate.Callback<V, Presenter> {

    private IMvpDelegate<V, Presenter> mDelegate;

    public IMvpDelegate<V, Presenter> getDelegate() {
        return mDelegate;
    }

    public boolean isCustomViewCreateEvent() {
        return false;
    }

    @Override
    public boolean isNeedRetainPresenter() {
        return true;
    }

    @Override
    public boolean isNeedDestroyRetainedPresenter() {
        return getActivity().isFinishing() || isRemoving();
    }

    @NonNull
    @Override
    public String getRetainPresenterTag() {
        return "Presenter_" + this.getClass().getSimpleName();
    }

    @Override
    public V view() {
        //noinspection unchecked
        return (V) this;
    }

    @NonNull
    public Presenter getPresenter() {
        return mDelegate.getPresenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegate = new MvpDelegate<>(getActivity(), this);
        getDelegate().onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        getDelegate().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        getDelegate().onDestroyView();
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(android.view.View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!isCustomViewCreateEvent()) {
            getDelegate().onViewCreated();
        }
    }

    @Override
    public void onResume() {
        getDelegate().onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        getDelegate().onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        getDelegate().onDestroy();
        mDelegate = null;
        super.onDestroy();
    }
}
