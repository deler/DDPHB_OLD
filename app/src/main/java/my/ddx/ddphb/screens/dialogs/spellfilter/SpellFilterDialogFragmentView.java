package my.ddx.ddphb.screens.dialogs.spellfilter;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appyvet.rangebar.RangeBar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.subjects.PublishSubject;
import my.ddx.ddphb.R;
import my.ddx.ddphb.models.common.SpellFilterModel;
import my.ddx.ddphb.screens.base.BaseDialogFragmentView;
import my.ddx.ddphb.ui.widgets.TagSelectorView;

/**
 * SpellFilterDialogFragmentView
 * Created by deler on 21.03.17.
 */

public class SpellFilterDialogFragmentView extends BaseDialogFragmentView<ISpellFilterView, ISpellFilterPresenter> implements ISpellFilterView {
    private static final String KEY_SPELL_FILTER = "KEY_SPELL_FILTER";
    private static final String TAG_ALL_ID = "TAG_ALL_ID";

    public static SpellFilterDialogFragmentView newInstance(SpellFilterModel spellFilterModel) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_SPELL_FILTER, spellFilterModel);
        SpellFilterDialogFragmentView fragment = new SpellFilterDialogFragmentView();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.range_level)
    RangeBar mRangeLevel;
    @BindView(R.id.text_title)
    TextView mTextTitle;
    @BindView(R.id.text_label_levels)
    TextView mTextLabelLevels;
    @BindView(R.id.button_cancel)
    Button mButtonCancel;
    @BindView(R.id.button_ok)
    Button mButtonOk;
    @BindView(R.id.layout_classes)
    TagSelectorView mLayoutClasses;
    @BindView(R.id.layout_schools)
    TagSelectorView mLayoutSchools;
    @BindView(R.id.layout_root)
    ViewGroup mLayoutRoot;

    private PublishSubject<LevelRangeEvent> mLevelRangeEventsubject = PublishSubject.create();
    private PublishSubject<Set<String>> mClassesEventSubject = PublishSubject.create();
    private PublishSubject<Set<String>> mSchoolsEventSubject = PublishSubject.create();


    @Nullable
    private Listener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmet_spell_filter, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void setChaingingDelayed(boolean delayed) {
        LayoutTransition layoutTransition = mLayoutRoot.getLayoutTransition();
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING);
        layoutTransition.setStartDelay(LayoutTransition.CHANGING, delayed ? 350 : 0);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRangeLevel.setOnRangeBarChangeListener((rangeBar, leftPinIndex, rightPinIndex, leftPinValue, rightPinValue) -> {
            mLevelRangeEventsubject.onNext(new LevelRangeEvent(leftPinIndex, rightPinIndex));
        });
    }

    @Override
    public ISpellFilterPresenter createPresenter() {
        SpellFilterModel spellFilterModel = getArguments().getParcelable(KEY_SPELL_FILTER);
        return new SpellFilterPresenter(spellFilterModel);
    }

    @Override
    public void setupClassFilter(Set<String> ids, List<ClassViewModel> classViewModels) {
        List<TagSelectorView.Tag> tags = new ArrayList<>();
        tags.add(new TagSelectorView.Tag(TAG_ALL_ID, getString(R.string.spell_filter_label_all)));
        for (ClassViewModel model : classViewModels) {
            tags.add(new TagSelectorView.Tag(model.getId(), model.getName()));
        }
        mLayoutClasses.setTags(tags);
        if (ids.isEmpty()) {
            mLayoutClasses.setSelectedTag(TAG_ALL_ID, true, false);
        } else {
            for (String id : ids) {
                mLayoutClasses.setSelectedTag(id, true, false);
            }
        }
        mLayoutClasses.setListener((tag, selected) -> {
            if (tag.getId().equalsIgnoreCase(TAG_ALL_ID)) {
                mLayoutClasses.resetSelected(true);
                mLayoutClasses.setSelectedTag(tag, true, true);
            } else {
                mLayoutClasses.setSelectedTag(TAG_ALL_ID, false, true);
                mLayoutClasses.setSelectedTag(tag, selected, true);
            }

            if (mLayoutClasses.getSelectedTags().isEmpty()) {
                mLayoutClasses.setSelectedTag(TAG_ALL_ID, true, true);
            }
//            else if (mLayoutClasses.isSeletedAllExclude(TAG_ALL_ID)){
//                mLayoutClasses.resetSelected(true);
//                mLayoutClasses.setSelectedTag(tag, true, true);
//            }

            Set<String> selectedIds = new HashSet<>();
            for (TagSelectorView.Tag tag1 : mLayoutClasses.getSelectedTags()) {
                if (!tag1.getId().equalsIgnoreCase(TAG_ALL_ID))
                    selectedIds.add(tag1.getId());
            }

            mClassesEventSubject.onNext(selectedIds);
        });
    }

    @Override
    public void setupSchoolFilter(Set<String> ids, List<SchoolViewModel> schoolViewModels) {
        List<TagSelectorView.Tag> tags = new ArrayList<>();
        tags.add(new TagSelectorView.Tag(TAG_ALL_ID, getString(R.string.spell_filter_label_all)));
        for (SchoolViewModel model : schoolViewModels) {
            tags.add(new TagSelectorView.Tag(model.getId(), model.getName()));
        }
        mLayoutSchools.setTags(tags);
        if (ids.isEmpty()) {
            mLayoutSchools.setSelectedTag(TAG_ALL_ID, true, false);
        } else {
            for (String id : ids) {
                mLayoutSchools.setSelectedTag(id, true, false);
            }
        }
        mLayoutSchools.setListener((tag, selected) -> {
            if (tag.getId().equalsIgnoreCase(TAG_ALL_ID)) {
                mLayoutSchools.resetSelected(true);
                mLayoutSchools.setSelectedTag(tag, true, true);
            } else {
                mLayoutSchools.setSelectedTag(TAG_ALL_ID, false, true);
                mLayoutSchools.setSelectedTag(tag, selected, true);
            }

            if (mLayoutSchools.getSelectedTags().isEmpty()) {
                mLayoutSchools.setSelectedTag(TAG_ALL_ID, true, true);
            }
//            else if (mLayoutSchools.isSeletedAllExclude(TAG_ALL_ID)){
//                mLayoutSchools.resetSelected(true);
//                mLayoutSchools.setSelectedTag(tag, true, true);
//            }

            Set<String> selectedIds = new HashSet<>();
            for (TagSelectorView.Tag tag1 : mLayoutSchools.getSelectedTags()) {
                if (!tag1.getId().equalsIgnoreCase(TAG_ALL_ID))
                    selectedIds.add(tag1.getId());
            }

            mSchoolsEventSubject.onNext(selectedIds);
        });
    }

    @Override
    public void setupLevelFilter(int min, int max) {
        mRangeLevel.setRangePinsByIndices(min, max);
    }

    @Override
    public Flowable<LevelRangeEvent> getLevelRangeFlowable() {
        return mLevelRangeEventsubject.toFlowable(BackpressureStrategy.DROP);
    }

    @Override
    public Flowable<Set<String>> getClassesIdsFlowable() {
        return mClassesEventSubject.toFlowable(BackpressureStrategy.DROP);
    }

    @Override
    public Flowable<Set<String>> getSchoolsFlowable() {
        return mSchoolsEventSubject.toFlowable(BackpressureStrategy.DROP);
    }

    public void setListener(@Nullable Listener listener) {
        mListener = listener;
    }

    @OnClick({R.id.button_cancel, R.id.button_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_cancel:
                dismissAllowingStateLoss();
                break;
            case R.id.button_ok:
                if (mListener != null) {
                    mListener.onFilterChanged(getPresenter().getFilter());
                }
                dismissAllowingStateLoss();
                break;
        }
    }

    public interface Listener {
        void onFilterChanged(SpellFilterModel spellFilterModel);
    }
}
