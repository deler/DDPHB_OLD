package my.ddx.ddphb.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * ClassModel
 * Created by deler on 19.03.17.
 */

public class ClassModel extends RealmObject {

    public static final String MODEL_NAME = "ClassModel";
    public static final String ID = "mId";
    public static final String NAME = "mName";

    @PrimaryKey
    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("hit_dice")
    private int mHitDice;
    @SerializedName("main_ability")
    private String mMainAbility;
    @SerializedName("saving_thorws")
    private String mSavingThorws;
    @SerializedName("equip_skils")
    private String mEquipSkills;

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getHitDice() {
        return mHitDice;
    }

    public String getMainAbility() {
        return mMainAbility;
    }

    public String getSavingThorws() {
        return mSavingThorws;
    }

    public String getEquipSkills() {
        return mEquipSkills;
    }
}
