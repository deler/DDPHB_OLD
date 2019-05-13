package my.ddx.ddphb.models.common;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.Nullable;
import my.ddx.ddphb.utils.ParcelUtils;

/**
 * SpellFilterModel
 * Created by deler on 21.03.17.
 */
public class SpellFilterModel implements Parcelable {

    public static final Creator<SpellFilterModel> CREATOR = new Creator<SpellFilterModel>() {
        public SpellFilterModel createFromParcel(Parcel source) {
            return new SpellFilterModel(source);
        }

        public SpellFilterModel[] newArray(int size) {
            return new SpellFilterModel[size];
        }
    };

    @Nullable
    private String mName;
    private Set<String> mSchools = new HashSet<>();
    private Set<String> mClassIds = new HashSet<>();

    private int mMinLevel = 0;
    private int mMaxLevel = 9;

    public SpellFilterModel() {
    }

    private SpellFilterModel(Parcel in) {
        if (ParcelUtils.readBooleanFromParcel(in)) this.mName = in.readString();
        mMinLevel = in.readInt();
        mMaxLevel = in.readInt();

        mClassIds = new HashSet<>(in.createStringArrayList());
        mSchools = new HashSet<>(in.createStringArrayList());
    }

    @Nullable
    public String getName() {
        return mName;
    }

    public SpellFilterModel setName(@Nullable String name) {
        if (name != null) {
            this.mName = name.toUpperCase();
        } else {
            this.mName = null;
        }
        return this;
    }

    public int getMinLevel() {
        return mMinLevel;
    }

    public SpellFilterModel setMinLevel(int minLevel) {
        if (minLevel < 0) minLevel = 0;
        mMinLevel = minLevel;
        return this;
    }

    public int getMaxLevel() {
        return mMaxLevel;
    }

    public SpellFilterModel setMaxLevel(int maxLevel) {
        if (maxLevel > 9) maxLevel = 9;
        mMaxLevel = maxLevel;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (ParcelUtils.writeBooleanToParcel(dest, this.mName)) dest.writeString(this.mName);
        dest.writeInt(this.mMinLevel);
        dest.writeInt(this.mMaxLevel);

        dest.writeStringList(new ArrayList<>(mClassIds));
        dest.writeStringList(new ArrayList<>(mSchools));
    }

    public SpellFilterModel setSchools(@Nullable Set<String> schools) {
        mSchools = schools;
        return this;
    }

    public SpellFilterModel setClassIds(Set<String> classIds) {
        mClassIds = classIds;
        return this;
    }

    public Set<String> getSchools() {
        return mSchools;
    }

    public Set<String> getClassIds() {
        return mClassIds;
    }
}
