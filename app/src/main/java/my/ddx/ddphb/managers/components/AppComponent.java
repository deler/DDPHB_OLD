package my.ddx.ddphb.managers.components;

import javax.inject.Singleton;

import dagger.Component;
import my.ddx.ddphb.managers.modules.AppModule;
import my.ddx.ddphb.managers.modules.DataModule;
import my.ddx.ddphb.screens.main.characters.CharactersListPresenter;
import my.ddx.ddphb.screens.main.spells.SpellsListPresenter;
import my.ddx.ddphb.screens.spelldetail.spelldetail.SpellDetailPresenter;
import my.ddx.ddphb.screens.dialogs.spellfilter.SpellFilterPresenter;

/**
 * AppComponent
 * Created by deler on 18.03.17.
 */

@Component(modules = {AppModule.class, DataModule.class})
@Singleton
public interface AppComponent {
    void inject(CharactersListPresenter presenter);

    void inject(SpellsListPresenter presenter);

    void inject(SpellDetailPresenter presenter);

    void inject(SpellFilterPresenter presenter);
}
