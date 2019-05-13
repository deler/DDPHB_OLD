package my.ddx.ddphb.screens.main.characters;

import android.content.Context;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import androidx.appcompat.content.res.AppCompatResources;
import my.ddx.ddphb.App;
import my.ddx.ddphb.R;
import my.ddx.ddphb.managers.DataManager;
import my.ddx.ddphb.ui.adapters.simple.CellModel;

/**
 * SpellsListPresenter implementation {@link ICharactersListPresenter}
 * Created by deler on 18.03.17.
 */
public class CharactersListPresenter implements ICharactersListPresenter {
    @Inject
    Context mContext;
    @Inject
    DataManager mDataManager;

    private ICharactersListView mView;

    CharactersListPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void onAttachView(@NonNull ICharactersListView view) {
        mView = view;

        List<CellModel> models = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            models.add(new CellModel(
                    String.valueOf(i),
                    String.format(Locale.getDefault(), "Character name %d", i),
                    String.format(Locale.getDefault(), "Class %d", i),
                    AppCompatResources.getDrawable(getContext(), R.drawable.ic_group_black_24dp)));
        }

        mView.showCharactersList(models);
    }

    @Override
    public void onDetachView() {
        mView = null;
    }

    @Override
    public void onDestroy() {

    }

    public Context getContext() {
        return mContext;
    }
}
