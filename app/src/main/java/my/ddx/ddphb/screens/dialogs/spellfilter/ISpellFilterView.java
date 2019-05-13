package my.ddx.ddphb.screens.dialogs.spellfilter;

import java.util.List;
import java.util.Set;

import io.reactivex.Flowable;
import my.ddx.mvp.IView;

/**
 * ISpellFilterView
 * Created by deler on 21.03.17.
 */

interface ISpellFilterView extends IView {

    void setupClassFilter(Set<String> ids, List<ClassViewModel> classViewModels);

    void setupSchoolFilter(Set<String> ids, List<SchoolViewModel> schoolViewModels);

    void setupLevelFilter(int min, int max);

    Flowable<LevelRangeEvent> getLevelRangeFlowable();
    Flowable<Set<String>> getClassesIdsFlowable();
    Flowable<Set<String>> getSchoolsFlowable();


    class LevelRangeEvent {
        private int min = 0;
        private int max = 9;

        public LevelRangeEvent(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }
    }

    class ClassViewModel {
        private String id;
        private CharSequence name;

        public ClassViewModel(String id, CharSequence name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public CharSequence getName() {
            return name;
        }
    }

    class SchoolViewModel {
        private String id;
        private CharSequence name;

        public SchoolViewModel(String id, CharSequence name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public CharSequence getName() {
            return name;
        }
    }
}
