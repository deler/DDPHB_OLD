package my.ddx.ddphb.ui.adapters.simple;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import my.ddx.ddphb.R;

/**
 * CellViewHolder for {@link SimpleListAdapter}
 * Created by deler on 18.03.17.
 */

class CellViewHolder extends RecyclerView.ViewHolder {

    @LayoutRes
    static int layoutId = R.layout.cell_simple_list;

    @BindView(R.id.image_icon)
    ImageView mImageIcon;
    @BindView(R.id.text_title)
    TextView mTextTitle;
    @BindView(R.id.text_description)
    TextView mTextDescription;

    @Nullable
    private CellListener mListener;
    @Nullable
    private CellModel mModel;

    CellViewHolder(View itemView, @Nullable CellListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mListener = listener;

        itemView.setOnClickListener(v -> {
            if (mListener != null && mModel != null) {
                mListener.onClick(mModel);
            }
        });
    }

    void setup(CellModel model) {
        mModel = model;

        mTextTitle.setText(model.getTitle());

        if (model.getDrawable() == null) {
            mImageIcon.setVisibility(View.GONE);
        } else {
            mImageIcon.setVisibility(View.VISIBLE);
            mImageIcon.setImageDrawable(model.getDrawable());
        }

        if (TextUtils.isEmpty(model.getDescription())) {
            mTextDescription.setVisibility(View.GONE);
        } else {
            mTextDescription.setVisibility(View.VISIBLE);
            mTextDescription.setText(model.getDescription());
        }
    }

    interface CellListener {
        void onClick(CellModel model);
    }
}
