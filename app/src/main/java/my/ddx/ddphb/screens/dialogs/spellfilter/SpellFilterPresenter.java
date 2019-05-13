package my.ddx.ddphb.screens.dialogs.spellfilter;

import android.content.Context;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.realm.RealmResults;
import my.ddx.ddphb.App;
import my.ddx.ddphb.managers.DataManager;
import my.ddx.ddphb.models.ClassModel;
import my.ddx.ddphb.models.SpellModel;
import my.ddx.ddphb.models.common.SpellFilterModel;

/**
 * SpellFilterPresenter implementation {@link ISpellFilterPresenter}
 * Created by deler on 21.03.17.
 */
public class SpellFilterPresenter implements ISpellFilterPresenter {
    @Inject
    Context mContext;
    @Inject
    DataManager mDataManager;

    private ISpellFilterView mView;
    private SpellFilterModel mSpellFilterModel;
    private RealmResults<ClassModel> mClasses;
    private RealmResults<SpellModel> mSpellSchools;

    private CompositeDisposable mCompositeDisposable;

    public SpellFilterPresenter(SpellFilterModel spellFilterModel) {
        mSpellFilterModel = spellFilterModel;
        App.getAppComponent().inject(this);

        loadClasses();
        loadSpellSchools();
    }

    private void loadSpellSchools() {
        if (mSpellSchools != null) mSpellSchools.removeAllChangeListeners();
        mSpellSchools = mDataManager.getSpellsManager().getSpellSchools();
        mSpellSchools.addChangeListener(element -> publishSchools());
    }

    private void publishSchools() {
        if (mView != null) {
            List<ISpellFilterView.SchoolViewModel> models = new ArrayList<>();
            for (SpellModel spellSchool : mSpellSchools) {
                models.add(new ISpellFilterView.SchoolViewModel(spellSchool.getSchool(), spellSchool.getSchool().toUpperCase()));
            }
            mView.setupSchoolFilter(mSpellFilterModel.getSchools(), models);
        }
    }

    private void loadClasses() {
        if (mClasses != null) mClasses.removeAllChangeListeners();
        mClasses = mDataManager.getClassesManager().getClasses();
        mClasses.addChangeListener(element -> publishClasses());
    }

    private void publishClasses() {
        if (mView != null) {
            List<ISpellFilterView.ClassViewModel> models = new ArrayList<>();
            for (ClassModel classModel : mClasses) {
                models.add(new ISpellFilterView.ClassViewModel(classModel.getId(), classModel.getName().toUpperCase()));
            }
            mView.setupClassFilter(mSpellFilterModel.getClassIds(), models);
        }
    }

    private void publishLevels(){
        if (mView != null){
            mView.setupLevelFilter(mSpellFilterModel.getMinLevel(), mSpellFilterModel.getMaxLevel());
        }
    }

    @Override
    public void onAttachView(@NonNull ISpellFilterView view) {
        mView = view;

        mCompositeDisposable = new CompositeDisposable();

        if (mClasses.isLoaded()) {
            publishClasses();
        }

        if (mSpellSchools.isLoaded()) {
            publishSchools();
        }

        publishLevels();

        mCompositeDisposable.add(mView.getLevelRangeFlowable().subscribe(levelRangeEvent -> {
            mSpellFilterModel.setMinLevel(levelRangeEvent.getMin()).setMaxLevel(levelRangeEvent.getMax());
        }));

        mCompositeDisposable.add(mView.getClassesIdsFlowable().subscribe(ids -> {
            mSpellFilterModel.setClassIds(ids);
        }));

        mCompositeDisposable.add(mView.getSchoolsFlowable().subscribe(schools -> {
            mSpellFilterModel.setSchools(schools);
        }));
    }

    @Override
    public void onDetachView() {
        mView = null;
        if(mCompositeDisposable != null) mCompositeDisposable.dispose();
    }

    @Override
    public void onDestroy() {
        if (mClasses != null) mClasses.removeAllChangeListeners();
        if (mSpellSchools != null) mSpellSchools.removeAllChangeListeners();

    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public SpellFilterModel getFilter() {
        return mSpellFilterModel;
    }
}
