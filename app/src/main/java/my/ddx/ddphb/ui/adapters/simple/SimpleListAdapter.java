package my.ddx.ddphb.ui.adapters.simple;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.subjects.PublishSubject;
import my.ddx.ddphb.R;

/**
 * SimpleListAdapter
 * Created by deler on 18.03.17.
 */

public class SimpleListAdapter extends RecyclerView.Adapter<CellViewHolder> {

    private final CellListener mCellListener = new CellListener();
    private final PublishSubject<CellModel> onClickSubject = PublishSubject.create();
    private List<CellModel> mModels;

    public SimpleListAdapter(List<CellModel> models) {
        mModels = models;
    }

    @Override
    public CellViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(CellViewHolder.layoutId, parent, false);
        return new CellViewHolder(view, mCellListener);
    }

    @Override
    public void onBindViewHolder(CellViewHolder holder, int position) {
        CellModel model = mModels.get(position);
        holder.setup(model);
    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }

    public Flowable<CellModel> getClickFlowable() {
        return onClickSubject.toFlowable(BackpressureStrategy.DROP);
    }

    private class CellListener implements CellViewHolder.CellListener {
        @Override
        public void onClick(CellModel model) {
            onClickSubject.onNext(model);
        }
    }
}
