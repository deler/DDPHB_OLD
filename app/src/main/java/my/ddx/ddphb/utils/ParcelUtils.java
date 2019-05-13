package my.ddx.ddphb.utils;

import android.os.Parcel;

/**
 * ParcelUtils
 * Created by deler on 21.03.17.
 */

public class ParcelUtils {

    public static boolean writeBooleanToParcel(Parcel parcel, Object value) {
        return writeBooleanToParcel(parcel, value != null);
    }

    public static boolean writeBooleanToParcel(Parcel parcel, boolean value) {
        parcel.writeInt(value ? 1 : 0);
        return value;
    }

    public static boolean readBooleanFromParcel(Parcel parcel) {
        return (parcel.readInt() != 0);
    }
}
