package my.ddx.mvp;

import android.os.Bundle;

import java.lang.ref.WeakReference;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

/**
 * MvpDelegate
 * Created by deler on 21.02.17.
 */

public class MvpDelegate<V extends IView, Presenter extends IPresenter<V>> implements IMvpDelegate<V, Presenter> {

    WeakReference<FragmentActivity> mHost;
    private IMvpDelegate.Callback<V, Presenter> mCallback;
    private Presenter mPresenter;

    public MvpDelegate(FragmentActivity activity, IMvpDelegate.Callback<V, Presenter> callback) {
        mHost = new WeakReference<>(activity);
        mCallback = callback;
    }

    private void initPresenter() {
        if (mCallback.isNeedRetainPresenter() && getActivity() != null) {
            RetainFragmentPresenter<Presenter> fragmentPresenter = RetainFragmentPresenter.findOrCreate(getActivity(), mCallback.getRetainPresenterTag());
            mPresenter = fragmentPresenter.getPresenter();
            if (mPresenter == null) {
                fragmentPresenter.setPresenter(mPresenter = mCallback.createPresenter());
            }
        } else {
            mPresenter = mCallback.createPresenter();
        }
    }

    @Nullable
    private FragmentActivity getActivity() {
        return mHost.get();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        initPresenter();
        if (savedInstanceState != null) {
            if (getExtendPresenter() != null) getExtendPresenter().onRestore(savedInstanceState.getBundle(mCallback.getRetainPresenterTag()));
        }
    }

    @Override
    public void onViewCreated() {
        getPresenter().onAttachView(mCallback.view());
    }

    @Override
    public void onResume() {
        if (getExtendPresenter() != null) getExtendPresenter().onResume();
    }

    @Override
    public void onPause() {
        if (getExtendPresenter() != null) getExtendPresenter().onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (getExtendPresenter() != null) {
            Bundle bundle = new Bundle();
            getExtendPresenter().onSave(bundle);
            outState.putBundle(mCallback.getRetainPresenterTag(), bundle);
        }
    }

    @Override
    public void onDestroyView() {
        getPresenter().onDetachView();
    }

    @Override
    public void onDestroy() {
        if (mCallback.isNeedDestroyRetainedPresenter()){
            if (getActivity() != null && mCallback != null) {
                RetainFragmentPresenter.remove(getActivity(), mCallback.getRetainPresenterTag());
            }
        }
        if (!mCallback.isNeedRetainPresenter()) {
            getPresenter().onDestroy();
        }
        mPresenter = null;
        mCallback = null;
    }

    @Override
    @Nullable
    public IExtendPresenter getExtendPresenter() {
        if (mPresenter instanceof IExtendPresenter) {
            return (IExtendPresenter) mPresenter;
        } else {
            return null;
        }
    }


    @Override
    public Presenter getPresenter() {
        return mPresenter;
    }
}
