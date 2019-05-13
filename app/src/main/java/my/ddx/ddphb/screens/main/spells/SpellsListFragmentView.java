package my.ddx.ddphb.screens.main.spells;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding2.view.RxMenuItem;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import my.ddx.ddphb.R;
import my.ddx.ddphb.models.common.SpellFilterModel;
import my.ddx.ddphb.screens.base.BaseFragmentView;
import my.ddx.ddphb.screens.spelldetail.SpellDetailActivity;
import my.ddx.ddphb.screens.dialogs.spellfilter.SpellFilterDialogFragmentView;
import my.ddx.ddphb.ui.adapters.simple.CellModel;
import my.ddx.ddphb.ui.adapters.simple.SimpleListAdapter;

/**
 * SpellsListFragmentView
 * Created by deler on 18.03.17.
 */

public class SpellsListFragmentView extends BaseFragmentView<ISpellsListView, ISpellsListPresenter> implements ISpellsListView {

    private static final String TAG_SPELL_FILTER_DIALOG = "TAG_SPELL_FILTER_DIALOG";

    public static SpellsListFragmentView newInstance() {
        Bundle args = new Bundle();

        SpellsListFragmentView fragment = new SpellsListFragmentView();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.recycler_spells)
    RecyclerView mRecyclerCharacters;

    LinearLayoutManager mLayoutManager;
    SimpleListAdapter mAdapter;
    DividerItemDecoration mDividerItemDecoration;

    PublishSubject<CellModel> mSpellClickSubject = PublishSubject.create();
    PublishSubject<SpellFilterModel> mSpellFilterSubject = PublishSubject.create();
    CompositeDisposable mCompositeDisposable;
    BehaviorSubject<String> mSearchSubject = BehaviorSubject.createDefault("");
    PublishSubject<Object> mFilterClickSubject = PublishSubject.create();
    private Disposable mSearchSubscribe;
    private Disposable mSpellClickSubscribe;
    private Disposable mFileterClickSubscribe;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroyView() {
        mCompositeDisposable.dispose();
        super.onDestroyView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmet_spell_list, container, false);
        ButterKnife.bind(this, view);

        mCompositeDisposable = new CompositeDisposable();

        mRecyclerCharacters.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerCharacters.setLayoutManager(mLayoutManager);

        mDividerItemDecoration = new DividerItemDecoration(
                mRecyclerCharacters.getContext(),
                mLayoutManager.getOrientation());
        mDividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.separator_items));
        mRecyclerCharacters.addItemDecoration(mDividerItemDecoration);

        getActivity().setTitle(R.string.main_title_spells);

        if (savedInstanceState != null){
            SpellFilterDialogFragmentView spellFilterDialogFragmentView = (SpellFilterDialogFragmentView) getFragmentManager().findFragmentByTag(TAG_SPELL_FILTER_DIALOG);
            if (spellFilterDialogFragmentView != null) {
                spellFilterDialogFragmentView.setListener(new SpellFilterDialogListener());
            }
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_main_spells, menu);
        MenuItem filterItem = menu.findItem(R.id.action_filter);
        if (filterItem != null) {
            if (mFileterClickSubscribe != null) mFileterClickSubscribe.dispose();
            mCompositeDisposable.add(mFileterClickSubscribe = RxMenuItem.clicks(filterItem)
                    .throttleFirst(1, TimeUnit.SECONDS)
                    .doOnNext(mFilterClickSubject::onNext)
                    .subscribe());
        }

        MenuItem searchItem = menu.findItem(R.id.action_search);
        if (searchItem != null) {
            SearchView searchView = (SearchView) searchItem.getActionView();
            if (mSearchSubscribe != null) mSearchSubscribe.dispose();
            mCompositeDisposable.add(mSearchSubscribe = RxSearchView.queryTextChanges(searchView)
                    .debounce(1, TimeUnit.SECONDS)
                    .map(CharSequence::toString)
                    .doOnNext(mSearchSubject::onNext)
                    .subscribe());
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public ISpellsListPresenter createPresenter() {
        return new SpellsListPresenter();
    }

    @Override
    public void showSpellsList(List<CellModel> models) {
        mAdapter = new SimpleListAdapter(models);
        if (mSpellClickSubscribe != null) mSpellClickSubscribe.dispose();
        mCompositeDisposable.add(mSpellClickSubscribe = mAdapter.getClickFlowable()
                .throttleFirst(1, TimeUnit.SECONDS)
                .doOnNext(mSpellClickSubject::onNext)
                .subscribe());
        mRecyclerCharacters.setAdapter(mAdapter);
    }

    @Override
    public Flowable<Object> getFilterClickFlowable() {
        return mFilterClickSubject.toFlowable(BackpressureStrategy.DROP);
    }

    @Override
    public Flowable<CellModel> getSpellClickFlowable() {
        return mSpellClickSubject.toFlowable(BackpressureStrategy.DROP);
    }

    @Override
    public Flowable<String> getSearchQueryFlowable() {
        return mSearchSubject.toFlowable(BackpressureStrategy.LATEST);
    }

    @Override
    public Flowable<SpellFilterModel> getSpellFilterChangeFlowable() {
        return mSpellFilterSubject.toFlowable(BackpressureStrategy.DROP);
    }

    @Override
    public void openSpellDetails(String spellId, List<String> spellIds) {
        SpellDetailActivity.start(getContext(), spellId, spellIds);
    }

    @Override
    public void openFilterSettings(SpellFilterModel filter) {
        SpellFilterDialogFragmentView spellFilterDialogFragmentView = SpellFilterDialogFragmentView.newInstance(filter);
        spellFilterDialogFragmentView.setListener(new SpellFilterDialogListener());
        spellFilterDialogFragmentView.show(getFragmentManager(),TAG_SPELL_FILTER_DIALOG);
    }

    private class SpellFilterDialogListener implements SpellFilterDialogFragmentView.Listener {
        @Override
        public void onFilterChanged(SpellFilterModel spellFilterModel) {
            mSpellFilterSubject.onNext(spellFilterModel);
        }
    }
}
