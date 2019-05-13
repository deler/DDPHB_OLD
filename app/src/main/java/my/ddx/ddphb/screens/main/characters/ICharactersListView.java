package my.ddx.ddphb.screens.main.characters;

import java.util.List;

import my.ddx.ddphb.ui.adapters.simple.CellModel;
import my.ddx.mvp.IView;

/**
 * ICharactersListView
 * Created by deler on 18.03.17.
 */

interface ICharactersListView extends IView {
    void showCharactersList(List<CellModel> models);
}
