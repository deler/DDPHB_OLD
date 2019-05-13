package my.ddx.ddphb.screens.spelldetail.spelldetail;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.ddx.ddphb.R;
import my.ddx.ddphb.screens.base.BaseFragmentView;

/**
 * SpellDetailFragmentView
 * Created by deler on 20.03.17.
 */

public class SpellDetailFragmentView extends BaseFragmentView<ISpellDetailView, ISpellDetailPresenter> implements ISpellDetailView {

    private static final String KEY_SPELL_ID = "KEY_SPELL_ID";

    public static SpellDetailFragmentView newInstance(String spellId) {

        Bundle args = new Bundle();
        args.putString(KEY_SPELL_ID, spellId);

        SpellDetailFragmentView fragment = new SpellDetailFragmentView();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.text_name)
    TextView mTextName;
    @BindView(R.id.text_school_and_level)
    TextView mTextSchoolAndLevel;
    @BindView(R.id.text_casting_time)
    TextView mTextCastingTime;
    @BindView(R.id.text_range)
    TextView mTextRange;
    @BindView(R.id.text_duration)
    TextView mTextDuration;
    @BindView(R.id.text_components)
    TextView mTextComponents;
    @BindView(R.id.text_description)
    TextView mTextDescription;
    @BindView(R.id.text_up_level)
    TextView mTextUpLevel;
    @BindView(R.id.text_classes)
    TextView mTextClasses;
    @BindView(R.id.layout_up_level)
    LinearLayout mLayoutUpLevel;
    @BindView(R.id.text_label_ritual)
    TextView mTextLabelRitual;
    @BindView(R.id.text_label_concentration)
    TextView mTextLabelConcentration;


    private String mSpellId;

    @NonNull
    @Override
    public String getRetainPresenterTag() {
        return String.format("%s_%s", super.getRetainPresenterTag(), mSpellId);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mSpellId = getArguments().getString(KEY_SPELL_ID);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmet_spell_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public ISpellDetailPresenter createPresenter() {
        return new SpellDetailPresenter(mSpellId);
    }


    @Override
    public void setupView(SpellViewModel spellModel) {
        mTextName.setText(spellModel.getName());
        mTextSchoolAndLevel.setText(spellModel.getSchoolAndLevel());
        mTextCastingTime.setText(spellModel.getCastingTime());
        mTextLabelRitual.setVisibility(spellModel.isRitual() ? View.VISIBLE : View.GONE);
        mTextRange.setText(spellModel.getRange());
        mTextDuration.setText(spellModel.getDuration());
        mTextLabelConcentration.setVisibility(spellModel.isConcentration() ? View.VISIBLE : View.GONE);
        mTextComponents.setText(spellModel.getComponents());
        mTextDescription.setText(spellModel.getDescription());
        mLayoutUpLevel.setVisibility(TextUtils.isEmpty(spellModel.getUpLevel()) ? View.GONE : View.VISIBLE);
        mTextUpLevel.setText(spellModel.getUpLevel());
        mTextClasses.setText(spellModel.getClasses());
    }
}
