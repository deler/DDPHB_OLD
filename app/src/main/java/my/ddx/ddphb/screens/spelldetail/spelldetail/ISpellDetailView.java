package my.ddx.ddphb.screens.spelldetail.spelldetail;

import my.ddx.ddphb.models.SpellModel;
import my.ddx.mvp.IView;

/**
 * ISpellDetailView
 * Created by deler on 20.03.17.
 */

interface ISpellDetailView extends IView {
    void setupView(SpellViewModel spellModel);

    class SpellViewModel {
        private String id;
        private CharSequence name;
        private CharSequence schoolAndLevel;
        private boolean ritual;
        private CharSequence castingTime;
        private CharSequence range;
        private CharSequence components;
        private CharSequence duration;
        private boolean concentration;
        private CharSequence description;
        private CharSequence upLevel;
        private CharSequence mClasses;

        public SpellViewModel(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public CharSequence getName() {
            return name;
        }

        public SpellViewModel setName(CharSequence name) {
            this.name = name;
            return this;
        }

        public CharSequence getSchoolAndLevel() {
            return schoolAndLevel;
        }

        public SpellViewModel setSchoolAndLevel(CharSequence schoolAndLevel) {
            this.schoolAndLevel = schoolAndLevel;
            return this;
        }

        public boolean isRitual() {
            return ritual;
        }

        public SpellViewModel setRitual(boolean ritual) {
            this.ritual = ritual;
            return this;
        }

        public CharSequence getCastingTime() {
            return castingTime;
        }

        public SpellViewModel setCastingTime(CharSequence castingTime) {
            this.castingTime = castingTime;
            return this;
        }

        public CharSequence getRange() {
            return range;
        }

        public SpellViewModel setRange(CharSequence range) {
            this.range = range;
            return this;
        }

        public CharSequence getComponents() {
            return components;
        }

        public SpellViewModel setComponents(CharSequence components) {
            this.components = components;
            return this;
        }

        public CharSequence getDuration() {
            return duration;
        }

        public SpellViewModel setDuration(CharSequence duration) {
            this.duration = duration;
            return this;
        }

        public boolean isConcentration() {
            return concentration;
        }

        public SpellViewModel setConcentration(boolean concentration) {
            this.concentration = concentration;
            return this;
        }

        public CharSequence getDescription() {
            return description;
        }

        public SpellViewModel setDescription(CharSequence description) {
            this.description = description;
            return this;
        }

        public CharSequence getUpLevel() {
            return upLevel;
        }

        public SpellViewModel setUpLevel(CharSequence upLevel) {
            this.upLevel = upLevel;
            return this;
        }

        public CharSequence getClasses() {
            return mClasses;
        }

        public SpellViewModel setClasses(CharSequence classes) {
            mClasses = classes;
            return this;
        }
    }
}
