package com.qashar.mypersonalaccounting.Category;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Category {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String name,type,unit;
    private Integer image;
    private boolean status,parent;

    public Category() {
    }

    public Category(Integer id, String name, String type,String unit, Integer image, boolean status,boolean parent) {
        this.id = id;
        this.parent = parent;
        this.name = name;
        this.type = type;
        this.image = image;
        this.status = status;
        this.unit = unit;
    }

    public Category(String name, String type,String unit, Integer image, boolean status,boolean parent) {
        this.name = name;
        this.parent = parent;
        this.type = type;
        this.image = image;
        this.status = status;
        this.unit = unit;
    }

    public boolean isParent() {
        return parent;
    }

    public boolean isStatus() {
        return status;
    }

    public void setParent(boolean parent) {
        this.parent = parent;
    }



    public String getUnit() {
        return unit;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }
}
