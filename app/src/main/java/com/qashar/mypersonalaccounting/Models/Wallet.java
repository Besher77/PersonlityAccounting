package com.qashar.mypersonalaccounting.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class Wallet {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String name;
    private Float price;
    private String currency,type;
    private Boolean status;
    private Long addedAt;
    private Integer lastPos;

    public Wallet() {
    }

    public Wallet(Integer id, String name, Float price, String currency, String type, Boolean status, Long addedAt,Integer lastPos) {
        this.id = id;
        this.lastPos = lastPos;
        this.name = name;
        this.price = price;
        this.currency = currency;
        this.type = type;
        this.status = status;
        this.addedAt = addedAt;
    }

    public Wallet(String name, Float price, String currency, String type, Boolean status, Long addedAt,Integer lastPos) {
        this.name = name;
        this.lastPos=lastPos;
        this.price = price;
        this.currency = currency;
        this.type = type;
        this.status = status;
        this.addedAt = addedAt;
    }

    public Long getAddedAt() {
        return addedAt;
    }

    public Integer getLastPos() {
        return lastPos;
    }

    public void setLastPos(Integer lastPos) {
        this.lastPos = lastPos;
    }

    public void setAddedAt(Long addedAt) {
        this.addedAt = addedAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
