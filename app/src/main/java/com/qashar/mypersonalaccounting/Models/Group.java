package com.qashar.mypersonalaccounting.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Group {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String name;

    public Group() {
    }

    public Group(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Group(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
