package Persistence.Entities.Group;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import Persistence.Entities.Model;

@Entity(tableName = "groups")
public class Group implements Model {

    @PrimaryKey
    public final int id;
    public final String name;


    public Group(int id, String name) {
        this.id = id;
        this.name = name;

    }
}
