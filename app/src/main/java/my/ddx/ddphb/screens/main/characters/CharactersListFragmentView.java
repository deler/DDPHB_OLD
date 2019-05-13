package my.ddx.ddphb.screens.main.characters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tapadoo.alerter.Alerter;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import my.ddx.ddphb.R;
import my.ddx.ddphb.screens.base.BaseFragmentView;
import my.ddx.ddphb.ui.adapters.simple.CellModel;
import my.ddx.ddphb.ui.adapters.simple.SimpleListAdapter;

/**
 * SpellsListFragmentView
 * Created by deler on 18.03.17.
 */

public class CharactersListFragmentView extends BaseFragmentView<ICharactersListView, ICharactersListPresenter> implements ICharactersListView {

    public static CharactersListFragmentView newInstance() {
        Bundle args = new Bundle();

        CharactersListFragmentView fragment = new CharactersListFragmentView();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.recycler_characters)
    RecyclerView mRecyclerCharacters;
    LinearLayoutManager mLayoutManager;
    SimpleListAdapter mAdapter;
    DividerItemDecoration mDividerItemDecoration;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmet_character_list, container, false);
        ButterKnife.bind(this, view);

        mRecyclerCharacters.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerCharacters.setLayoutManager(mLayoutManager);

        mDividerItemDecoration = new DividerItemDecoration(
                mRecyclerCharacters.getContext(),
                mLayoutManager.getOrientation());
        mDividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.separator_items));
        mRecyclerCharacters.addItemDecoration(mDividerItemDecoration);

        getActivity().setTitle(R.string.main_title_characters);

        return view;
    }

    @Override
    public ICharactersListPresenter createPresenter() {
        return new CharactersListPresenter();
    }

    @Override
    public void showCharactersList(List<CellModel> models) {
        mAdapter = new SimpleListAdapter(models);
        mAdapter.getClickFlowable().subscribe((object) -> Alerter.create(getActivity())
                .setTitle(R.string.main_title_characters)
                .setText("Not yet ready")
                .setDuration(2000)
                .setBackgroundColorRes(R.color.orange)
                .show());
        mRecyclerCharacters.setAdapter(mAdapter);
    }
}
