package my.ddx.ddphb.screens.main.spells;

import android.content.Context;
import androidx.annotation.NonNull;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.realm.RealmResults;
import my.ddx.ddphb.App;
import my.ddx.ddphb.R;
import my.ddx.ddphb.managers.DataManager;
import my.ddx.ddphb.models.SpellModel;
import my.ddx.ddphb.models.common.SpellFilterModel;
import my.ddx.ddphb.ui.adapters.simple.CellModel;

/**
 * SpellsListPresenter implementation {@link ISpellsListPresenter}
 * Created by deler on 18.03.17.
 */
public class SpellsListPresenter implements ISpellsListPresenter {
    @Inject
    Context mContext;
    @Inject
    DataManager mDataManager;

    private RealmResults<SpellModel> mSpells;

    private ISpellsListView mView;

    private CompositeDisposable mViewDisposable;

    private SpellFilterModel mFilter = new SpellFilterModel();

    SpellsListPresenter() {
        App.getAppComponent().inject(this);

        loadSpells();
    }

    @Override
    public void onAttachView(@NonNull ISpellsListView view) {
        mViewDisposable = new CompositeDisposable();
        mView = view;

        mViewDisposable.add(mView.getSearchQueryFlowable().observeOn(AndroidSchedulers.mainThread()).subscribe(this::filterByName));
        mViewDisposable.add(mView.getSpellClickFlowable().observeOn(AndroidSchedulers.mainThread()).subscribe(this::showDetailsForFiltered));
        mViewDisposable.add(mView.getFilterClickFlowable().observeOn(AndroidSchedulers.mainThread()).subscribe(this::openFilterSettings));
        mViewDisposable.add(mView.getSpellFilterChangeFlowable().observeOn(AndroidSchedulers.mainThread()).subscribe(this::filterChanged));

        if (mSpells.isLoaded()) {
            publishSpells();
        }
    }

    @Override
    public void onDetachView() {
        if (mViewDisposable != null) mViewDisposable.dispose();
        mView = null;
    }

    @Override
    public void onDestroy() {
        if (mSpells != null) {
            mSpells.removeAllChangeListeners();
        }
    }

    private void publishSpells() {
        if (mView != null) {
            List<CellModel> models = new ArrayList<>();
            for (SpellModel spell : mSpells) {
                CharSequence levelAndSchool;
                if (spell.getLevel() == 0) {
                    levelAndSchool = getContext().getString(R.string.spells_text_cantrips_and_school, spell.getSchool());
                } else {
                    levelAndSchool = getContext().getString(R.string.spells_text_level_and_school, spell.getLevel(), spell.getSchool());
                }
                models.add(new CellModel(
                        spell.getId(),
                        spell.getName(),
                        levelAndSchool,
                        null));
            }

            mView.showSpellsList(models);
        }
    }

    private void filterByName(String stirng) {
        Logger.d(stirng);
        mFilter.setName(stirng);
        loadSpells();
    }

    private void filterChanged(SpellFilterModel spellFilterModel) {
        mFilter = spellFilterModel;
        loadSpells();
    }

    private void loadSpells() {
        if (mSpells != null) {
            mSpells.removeAllChangeListeners();
        }
        mSpells = mDataManager.getSpellsManager().getSpells(mFilter);
        mSpells.addChangeListener(elements -> publishSpells());
        if (mSpells.isLoaded()) {
            publishSpells();
        }
    }

    private void showDetailsForFiltered(CellModel cellModel) {
        if (mView != null) {
            List<String> spellIds = new ArrayList<>();
            for (SpellModel spell : mSpells) {
                spellIds.add(spell.getId());
            }
            mView.openSpellDetails(cellModel.getId(), spellIds);
        }
    }

    private void openFilterSettings(Object o) {
        if (mView!= null){
            mView.openFilterSettings(mFilter);
        }

    }

    public Context getContext() {
        return mContext;
    }
}
