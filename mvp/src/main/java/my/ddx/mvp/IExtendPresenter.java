package my.ddx.mvp;

import android.os.Bundle;

/**
 * IExtendPresenter
 * Created by deler on 28.02.17.
 */

public interface IExtendPresenter {

    void onRestore(Bundle savedInstanceState);

    void onSave(Bundle bundle);

    void onResume();

    void onPause();
}
