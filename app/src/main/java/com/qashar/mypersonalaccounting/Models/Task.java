package com.qashar.mypersonalaccounting.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.qashar.mypersonalaccounting.RoomDB.DateConverter;


@Entity
@TypeConverters(DateConverter.class)
public class Task {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private Float price;
    private Integer icon;
    private String wallet,type,currency,note,date,time,group,contact, emoji,checked;
    private boolean isRepeated;
    private long addedAt;
    private boolean addedAtWallet;
    private long walletID;

    public Task() {
    }

    public Task(Integer icon, String wallet, String type, String currency, String note, String date, String time, String group, String contact, String emoji, String checked, boolean isRepeated, long addedAt, boolean addedAtWallet, long walletID) {
        this.icon = icon;
        this.wallet = wallet;
        this.type = type;
        this.currency = currency;
        this.note = note;
        this.date = date;
        this.time = time;
        this.group = group;
        this.contact = contact;
        this.emoji = emoji;
        this.checked = checked;
        this.isRepeated = isRepeated;
        this.addedAt = addedAt;
        this.addedAtWallet = addedAtWallet;
        this.walletID = walletID;
    }

    public Task(Integer id, Float price, Integer icon, String wallet, String type, String currency, String note, String date, String time, String group, String contact, String emoji, String checked, boolean isRepeated, long addedAt, boolean addedAtWallet, long walletID) {
        this.id = id;
        this.price = price;
        this.icon = icon;
        this.wallet = wallet;
        this.type = type;
        this.currency = currency;
        this.note = note;
        this.date = date;
        this.time = time;
        this.group = group;
        this.contact = contact;
        this.emoji = emoji;
        this.checked = checked;
        this.isRepeated = isRepeated;
        this.addedAt = addedAt;
        this.addedAtWallet = addedAtWallet;
        this.walletID = walletID;
    }

    public Task(Float price, Integer icon, String wallet, String type, String currency, String note, String date, String time, String group, String contact, String emoji, String checked, boolean isRepeated, long addedAt, boolean addedAtWallet, long walletID) {
        this.price = price;
        this.icon = icon;
        this.wallet = wallet;
        this.type = type;
        this.currency = currency;
        this.note = note;
        this.date = date;
        this.time = time;
        this.group = group;
        this.contact = contact;
        this.emoji = emoji;
        this.checked = checked;
        this.isRepeated = isRepeated;
        this.addedAt = addedAt;
        this.addedAtWallet = addedAtWallet;
        this.walletID = walletID;
    }

    public long getWalletID() {
        return walletID;
    }

    public void setWalletID(long walletID) {
        this.walletID = walletID;
    }

    public boolean isAddedAtWallet() {
        return addedAtWallet;
    }

    public void setAddedAtWallet(boolean addedAtWallet) {
        this.addedAtWallet = addedAtWallet;
    }

    public long getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(long addedAt) {
        this.addedAt = addedAt;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public boolean isRepeated() {
        return isRepeated;
    }

    public void setRepeated(boolean repeated) {
        isRepeated = repeated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }
}
