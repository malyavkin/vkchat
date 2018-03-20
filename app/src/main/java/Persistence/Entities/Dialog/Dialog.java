package Persistence.Entities.Dialog;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import Persistence.Entities.Model;

@Entity(tableName = "dialogs")
public class Dialog implements Model {
    private String lastMessage;

    @PrimaryKey
    private int id;
    private String type;
    private String title;
    private int admin_id;

//    @ColumnInfo(name = "users")
//    private int[] users;

    private String photo_50;
    private String photo_100;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public String getPhoto_50() {
        return photo_50;
    }

    public void setPhoto_50(String photo_50) {
        this.photo_50 = photo_50;
    }

    public String getPhoto_100() {
        return photo_100;
    }

    public void setPhoto_100(String photo_100) {
        this.photo_100 = photo_100;
    }

    public String getPhoto_200() {
        return photo_200;
    }

    public void setPhoto_200(String photo_200) {
        this.photo_200 = photo_200;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isKicked() {
        return kicked;
    }

    public void setKicked(boolean kicked) {
        this.kicked = kicked;
    }

    private String photo_200;
    private boolean left;
    private boolean kicked;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Dialog(String lastMessage, String title) {
        this.lastMessage = lastMessage;
        this.title = title;
    }
    public Dialog(String lastMessage, String title, int id) {
        this.lastMessage = lastMessage;
        this.title = title;
        this.id = id;
    }
}
