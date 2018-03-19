package Persistence;

import android.arch.persistence.room.Room;
import android.content.Context;

import Persistence.Database.AppDatabase;

/**
 * Created by lich on 2018-03-20.
 */

public class Storage {
    private static final Storage ourInstance = new Storage();
    public static AppDatabase db;
    public static Storage getInstance() {
        return ourInstance;
    }
    public static void setInstance(Context ctx) {
        Storage.db = Room.databaseBuilder(ctx,
                AppDatabase.class, "database-name").build();

    }


    private Storage() {
    }
}
