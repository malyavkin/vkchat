package Persistence.Entities.User;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM `Group`")
    List<User> getAll();

    @Query("SELECT * FROM `Group` WHERE id IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM `Group` WHERE id IN (:userIds)")
    List<User> loadAllByIdsAfterDate(int[] userIds);


    @Insert
    void insertAll(User... users);
}