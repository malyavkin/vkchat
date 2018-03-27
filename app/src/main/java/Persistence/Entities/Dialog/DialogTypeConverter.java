package Persistence.Entities.Dialog;

import android.arch.persistence.room.TypeConverter;

public class DialogTypeConverter {
    @TypeConverter
    public static DialogType toType(String s) {
        return DialogType.valueOf(s);
    }

    @TypeConverter
    public static String toString(DialogType dt) {
        return dt.toString();
    }
}
