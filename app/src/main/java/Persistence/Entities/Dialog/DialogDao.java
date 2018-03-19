package Persistence.Entities.Dialog;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DialogDao {
    @Query("SELECT * FROM dialogs")
    List<Dialog> getAll();

    @Query("SELECT * FROM dialogs WHERE id IN (:dialogIds)")
    List<Dialog> loadAllByIds(int[] dialogIds);

    @Insert
    void insertAll(Dialog... dialogs);

    @Delete
    void delete(Dialog dialog);
}