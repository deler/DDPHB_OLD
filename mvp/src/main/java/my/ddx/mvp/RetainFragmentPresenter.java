package my.ddx.mvp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * RetainFragmentPresenter
 * Created by deler on 21.02.17.
 */
public class RetainFragmentPresenter<T extends IPresenter> extends Fragment {

    public static <T extends IPresenter> RetainFragmentPresenter<T> findOrCreate(FragmentActivity activity, String tag) {
        RetainFragmentPresenter<T> retainFragment = find(activity, tag);

        if (retainFragment == null) {
            retainFragment = new RetainFragmentPresenter<>();
            activity.getSupportFragmentManager().beginTransaction()
                    .add(retainFragment, tag)
                    .commitAllowingStateLoss();
        }

        return retainFragment;
    }

    @Nullable
    public static <T extends IPresenter> RetainFragmentPresenter<T> find(FragmentActivity activity, String tag) {
        Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(tag);

        RetainFragmentPresenter<T> retainFragment = null;
        if (fragment != null && fragment instanceof RetainFragmentPresenter) {
            try {
                //noinspection unchecked
                retainFragment = (RetainFragmentPresenter<T>) fragment;
            } catch (ClassCastException i) {
                retainFragment = null;
            }
        }
        return retainFragment;
    }

    // Remove from FragmentManager
    public static void remove(FragmentActivity activity, String tag) {
        if (!activity.getSupportFragmentManager().isDestroyed()) {
            RetainFragmentPresenter<IPresenter> fragment = find(activity, tag);
            activity.getSupportFragmentManager().beginTransaction()
                    .remove(fragment)
                    .commitAllowingStateLoss();
        }
    }

    @Nullable
    public T mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        mPresenter = null;
        super.onDestroy();
    }

    @Nullable
    public T getPresenter() {
        return mPresenter;
    }

    public void setPresenter(@Nullable T presenter) {
        mPresenter = presenter;
    }
}
