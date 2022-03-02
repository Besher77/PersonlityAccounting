package com.qashar.mypersonalaccounting.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.qashar.mypersonalaccounting.RoomDB.DateConverter;

import java.util.Date;

@Entity
@TypeConverters(DateConverter.class)
public class Todo {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String title;
    private String note;
    private Date date;
    private String repeat;
    private Integer todoID;

    public Todo() {
    }

    public Todo(Integer id, String title, String note, Date date, String repeat, Integer todoID) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.date = date;
        this.repeat = repeat;
        this.todoID = todoID;
    }

    public Todo(String title, String note, Date date, String repeat, Integer todoID) {
        this.title = title;
        this.note = note;
        this.date = date;
        this.repeat = repeat;
        this.todoID = todoID;
    }

    public Integer getTodoID() {
        return todoID;
    }

    public void setTodoID(Integer todoID) {
        this.todoID = todoID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }
}
