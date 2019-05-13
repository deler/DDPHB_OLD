package my.ddx.ddphb.screens.main.spells;

import com.jakewharton.rxbinding2.view.MenuItemActionViewEvent;

import java.util.List;

import io.reactivex.Flowable;
import my.ddx.ddphb.models.common.SpellFilterModel;
import my.ddx.ddphb.ui.adapters.simple.CellModel;
import my.ddx.mvp.IView;

/**
 * ISpellsListView
 * Created by deler on 18.03.17.
 */

interface ISpellsListView extends IView {
    void showSpellsList(List<CellModel> models);

    Flowable<Object> getFilterClickFlowable();

    Flowable<CellModel> getSpellClickFlowable();

    Flowable<String> getSearchQueryFlowable();

    Flowable<SpellFilterModel> getSpellFilterChangeFlowable();

    void openSpellDetails(String spellId, List<String> spellIds);

    void openFilterSettings(SpellFilterModel filter);
}
