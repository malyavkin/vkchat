package Persistence.Entities.Dialog;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import Persistence.Entities.Model;

@Entity(tableName = "dialogs")
public class Dialog implements Model {
    public String lastMessage;
    @PrimaryKey
    @NonNull
    // совмещенный
    public String id;

    public String type;
    public int entity_id;
    public String chatTitle;
    public int lastMessageDate;

    public String photo_50;
    public String photo_200;
    public String photo_100;


    public Dialog(String type, int entity_id, String lastMessage, String chatTitle, int lastMessageDate) {
        this.lastMessage = lastMessage;
        this.lastMessageDate = lastMessageDate;

        this.chatTitle = chatTitle;
        this.entity_id = entity_id;
        this.type = type;
        this.id = type + "_" + entity_id;

    }

    public String getTitle() {
        if (chatTitle.equals("")) {
            return id;
        } else {
            return chatTitle;
        }
    }
}
