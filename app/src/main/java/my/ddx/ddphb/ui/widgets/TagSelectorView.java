package my.ddx.ddphb.ui.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.AlignContent;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.JustifyContent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import my.ddx.ddphb.R;

/**
 * TagSelectorView
 * Created by deler on 22.03.17.
 */

public class TagSelectorView extends FlexboxLayout {

    private Map<Tag, TagView> mTagTagViewMap;
    private List<Tag> mTags;
    private Set<Tag> mTagSelectedSet = new HashSet<>();

    @Nullable
    private Listener mListener;

    public TagSelectorView(Context context) {
        super(context);
        init(null, 0);
    }

    public TagSelectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public TagSelectorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs, defStyleAttr);
    }

    protected void init(@Nullable AttributeSet attrs, int defStyleAttr) {
        setAlignContent(AlignContent.FLEX_START);
        setAlignItems(AlignItems.FLEX_START);
        setFlexWrap(FlexWrap.WRAP);
        setJustifyContent(JustifyContent.FLEX_START);
    }

    protected void addTagView(TagView view) {
        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = getContext().getResources().getDimensionPixelOffset(R.dimen.spacing_small);
        params.bottomMargin = margin;
        params.topMargin = margin;
        params.leftMargin = margin;
        params.rightMargin = margin;
        addView(view, params);
    }

    public void setTags(List<Tag> tags) {
        mTags = tags;
        removeAllViews();
        mTagTagViewMap = new HashMap<>();
        for (Tag tag : tags) {
            TagView tagView = createTagView(tag);
            mTagTagViewMap.put(tag, tagView);
            addTagView(tagView);
        }
    }

    protected TagView createTagView(Tag tag) {
        TagView tagView = new TagView(getContext());
        tagView.setText(tag.getName());
        tagView.setOnClickListener(new TagClickListener(tag));
        return tagView;
    }

    private void animatedSelectTag(final TagView tagView, boolean selected) {
        tagView.animate().rotationYBy(90).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                tagView.setTextYRotation(180);
                tagView.setSelected(selected);
                tagView.animate().rotationYBy(90).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        tagView.setRotationY(0);
                        tagView.setTextYRotation(0);
                    }
                });
            }
        });
    }

    public boolean isSelected(Tag tag) {
        return mTagSelectedSet.contains(tag);
    }

    public void setListener(@Nullable Listener listener) {
        mListener = listener;
    }

    public void hideUnselectedTags(boolean hide) {
        for (Tag tag : mTags) {
            TagView tagView = mTagTagViewMap.get(tag);
            if (isSelected(tag)) {
                tagView.setVisibility(VISIBLE);
            } else {
                tagView.setVisibility(hide ? GONE : VISIBLE);
            }
        }
    }

    public void resetSelected(boolean animated){
        if (animated){
            for (Tag tag : mTagSelectedSet) {
                TagView tagView = mTagTagViewMap.get(tag);
                if (tagView.isSelected()) {
                    animatedSelectTag(tagView, false);
                }
            }
            mTagSelectedSet.clear();
        } else {
            for (Tag tag : mTagSelectedSet) {
                TagView tagView = mTagTagViewMap.get(tag);
                if (tagView.isSelected()) {
                    tagView.setSelected(false);
                }
            }
            mTagSelectedSet.clear();
        }
    }

    public void setSelectedTag(String tagId, boolean selected, boolean animated) {
        for (Tag tag : mTags) {
            if (tag.getId().equalsIgnoreCase(tagId)) {
                setSelectedTag(tag, selected, animated);
                return;
            }
        }
    }
    public void setSelectedTag(Tag tag, boolean selected, boolean animated) {
        boolean changeStatus = mTagSelectedSet.contains(tag) != selected;
        if (selected){
            mTagSelectedSet.add(tag);
        } else {
            mTagSelectedSet.remove(tag);
        }

        TagView tagView = mTagTagViewMap.get(tag);
        if (changeStatus) {
            if (animated) {
                animatedSelectTag(tagView, isSelected(tag));
            } else {
                tagView.setSelected(isSelected(tag));
            }
        }
    }

    public Set<Tag> getSelectedTags() {
        return mTagSelectedSet;
    }

    public boolean isSeletedAllExclude(String tagAllId) {
        Tag tag = null;
        for (Tag tag1 : mTags) {
            if (tag1.getId().equalsIgnoreCase(tagAllId)) {
                tag = tag1;
                break;
            }
        }
        if (tag == null) return false;

        Set<Tag> tags = new HashSet<>(mTags);
        tags.removeAll(mTagSelectedSet);
        if (tags.size() == 1 && tags.contains(tag)) {
            return true;
        }
        return false;
    }

    public interface Listener {
        void onTagSeleted(Tag tag, boolean selected);
    }

    private class TagClickListener implements OnClickListener {
        private Tag mTag;

        TagClickListener(Tag tag) {
            mTag = tag;
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onTagSeleted(mTag, !isSelected(mTag));
            }
        }
    }

    public static class Tag {
        private String id;
        private CharSequence name;

        public Tag(String id, CharSequence name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public CharSequence getName() {
            return name;
        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (name != null ? name.hashCode() : 0);
            return result;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Tag tag = (Tag) o;

            if (id != null ? !id.equals(tag.id) : tag.id != null) return false;
            return name != null ? name.equals(tag.name) : tag.name == null;

        }
    }
}
