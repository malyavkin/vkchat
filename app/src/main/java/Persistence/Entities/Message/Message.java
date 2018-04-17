package Persistence.Entities.Message;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import Persistence.Entities.Model;

@Entity(tableName = "dialogs")
public class Message implements Model {
    @PrimaryKey
    String id;
    String user_id;
    String from_id;
    int date;
    String body;
    String chat_id;

    public Message(String id, String user_id, String from_id, int date, String body, String chat_id) {
        this.id = id;
        this.user_id = user_id;
        this.from_id = from_id;
        this.date = date;
        this.body = body;
        this.chat_id = chat_id;
    }

    @Ignore
    public Message(String id, String user_id, String from_id, int date, String body) {
        this.id = id;
        this.user_id = user_id;
        this.from_id = from_id;
        this.date = date;
        this.body = body;

    }
}