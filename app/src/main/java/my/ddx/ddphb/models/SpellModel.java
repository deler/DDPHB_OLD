package my.ddx.ddphb.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * SpellModel
 * Created by deler on 18.03.17.
 */

public class SpellModel extends RealmObject {

    public final static String ID = "mId";
    public final static String NAME = "mName";
    public final static String LEVEL = "mLevel";
    public final static String SCHOOL = "mSchool";
    public static final String CLASSES = "mClasses";

    @PrimaryKey
    @SerializedName("id")
    private String mId;
    @SerializedName("class_ids")
    private String mClassIds;
    @SerializedName("source")
    private String mSource;
    @SerializedName("number")
    private int mNumber;
    @Index
    @SerializedName("name")
    private String mName;
    @Index
    @SerializedName("level")
    private int mLevel;
    @Index
    @SerializedName("school")
    private String mSchool;
    @SerializedName("ritual")
    private boolean mRitual;
    @SerializedName("casting_time")
    private String mCastingTime;
    @SerializedName("range")
    private String mRange;
    @SerializedName("components")
    private String mComponents;
    @SerializedName("duration")
    private String mDuration;
    @SerializedName("concentration")
    private boolean mConcentration;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("up_level")
    private String mUpLevel;
    private RealmList<ClassModel> mClasses;

    public String getId() {
        return mId;
    }

    public String getSource() {
        return mSource;
    }

    public String getName() {
        return mName;
    }

    public int getLevel() {
        return mLevel;
    }

    public String getSchool() {
        return mSchool;
    }

    public boolean isRitual() {
        return mRitual;
    }

    public String getCastingTime() {
        return mCastingTime;
    }

    public String getRange() {
        return mRange;
    }

    public String getComponents() {
        return mComponents;
    }

    public String getDuration() {
        return mDuration;
    }

    public boolean isConcentration() {
        return mConcentration;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getUpLevel() {
        return mUpLevel;
    }

    public String getClassIds() {
        return mClassIds;
    }

    public RealmList<ClassModel> getClasses() {
        return mClasses;
    }
}
