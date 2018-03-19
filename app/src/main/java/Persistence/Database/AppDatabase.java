package Persistence.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import Persistence.Entities.Dialog.Dialog;
import Persistence.Entities.Dialog.DialogDao;

@Database(entities = {Dialog.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DialogDao dialogDao();
}
