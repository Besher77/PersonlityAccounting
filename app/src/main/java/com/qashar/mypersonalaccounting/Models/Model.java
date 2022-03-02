package com.qashar.mypersonalaccounting.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.qashar.mypersonalaccounting.RoomDB.DateConverter;

import java.util.Date;

@Entity
@TypeConverters(DateConverter.class)
public class Model {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String type,contact;
    private Integer price,fullNPrice,getFullPPrice;
    private Integer icon;
    private Date date;

    public Model() {
    }

    public Model(String type, String contact, Integer price, Integer fullNPrice, Integer getFullPPrice, Integer icon, Date date) {
        this.type = type;
        this.contact = contact;
        this.price = price;
        this.fullNPrice = fullNPrice;
        this.getFullPPrice = getFullPPrice;
        this.icon = icon;
        this.date = date;
    }

    public Model(Integer id, String type, String contact, Integer price, Integer fullNPrice, Integer getFullPPrice, Integer icon, Date date) {
        this.id = id;
        this.type = type;
        this.contact = contact;
        this.price = price;
        this.fullNPrice = fullNPrice;
        this.getFullPPrice = getFullPPrice;
        this.icon = icon;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getFullNPrice() {
        return fullNPrice;
    }

    public void setFullNPrice(Integer fullNPrice) {
        this.fullNPrice = fullNPrice;
    }

    public Integer getGetFullPPrice() {
        return getFullPPrice;
    }

    public void setGetFullPPrice(Integer getFullPPrice) {
        this.getFullPPrice = getFullPPrice;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
