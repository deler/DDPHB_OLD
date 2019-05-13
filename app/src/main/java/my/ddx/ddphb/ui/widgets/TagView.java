package my.ddx.ddphb.ui.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import my.ddx.ddphb.R;

/**
 * TagView
 * Created by deler on 22.03.17.
 */

public class TagView extends LinearLayout {
    @BindView(R.id.image_icon)
    ImageView mImageIcon;
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.image_remove)
    ImageView mImageRemove;

    public TagView(Context context) {
        super(context);
        init(null, 0, 0);
    }

    public TagView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    public TagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TagView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    private void init(@Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        setBackgroundResource(R.drawable.selector_tag_default);
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);
        LayoutInflater.from(getContext()).inflate(R.layout.view_tag, this, true);
        ButterKnife.bind(this);
    }

    public void setText(CharSequence text) {
        mText.setText(text);
    }

    public void setTextYRotation(float a) {
        mText.setRotationY(a);
    }
}
