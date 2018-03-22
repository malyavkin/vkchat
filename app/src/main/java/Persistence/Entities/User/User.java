package Persistence.Entities.User;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import Persistence.Entities.Model;

@Entity(tableName = "users")
public class User implements Model {

    @PrimaryKey
    public int id;
    public String first_name;
    public String last_name;

    public User(int id, String first_name, String last_name) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
    }
}
