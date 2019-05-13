package my.ddx.ddphb.screens.dialogs.spellfilter;

import my.ddx.ddphb.models.common.SpellFilterModel;
import my.ddx.mvp.IPresenter;

/**
 * ISpellFilterPresenter
 * Created by deler on 21.03.17.
 */

public interface ISpellFilterPresenter extends IPresenter<ISpellFilterView> {
    SpellFilterModel getFilter();
}
