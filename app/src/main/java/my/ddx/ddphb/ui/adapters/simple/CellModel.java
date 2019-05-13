package my.ddx.ddphb.ui.adapters.simple;

import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;

/**
 * CellModel for {@link SimpleListAdapter}
 * Created by deler on 18.03.17.
 */

public class CellModel {
    private String id;
    private CharSequence mTitle;
    @Nullable
    private CharSequence mDescription;
    @Nullable
    private Drawable mDrawable;

    public CellModel(String id, CharSequence title, @Nullable CharSequence description, @Nullable Drawable drawable) {
        this.id = id;
        mTitle = title;
        mDescription = description;
        mDrawable = drawable;
    }

    public String getId() {
        return id;
    }

    public CharSequence getTitle() {
        return mTitle;
    }

    @Nullable
    public CharSequence getDescription() {
        return mDescription;
    }

    @Nullable
    public Drawable getDrawable() {
        return mDrawable;
    }

    @Override
    public String toString() {
        return "CellModel{" +
                "id='" + id + '\'' +
                ", mTitle=" + mTitle +
                ", mDescription=" + mDescription +
                '}';
    }
}
