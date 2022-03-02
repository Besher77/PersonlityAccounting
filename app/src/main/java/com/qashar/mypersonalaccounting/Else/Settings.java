package com.qashar.mypersonalaccounting.Else;

import android.content.Context;
import android.content.SharedPreferences;

public class Settings {
    private Integer lastID;
    private Context context;
    private Integer theme;
    private Integer invoiceNum;

    public Settings(Context context) {
        this.context = context;
    }

    public Integer getLastID() {
        SharedPreferences preferences = context.getSharedPreferences("Root",Context.MODE_PRIVATE);
        return preferences.getInt("LastID",0);
    }

    public void setLastID(Integer lastID) {
        SharedPreferences preferences = context.getSharedPreferences("Root",Context.MODE_PRIVATE);
        SharedPreferences.Editor e = preferences.edit();
        e.putInt("LastID",lastID);
        e.apply();
    }
    public Integer getInvoiceNum() {
        SharedPreferences preferences = context.getSharedPreferences("Root",Context.MODE_PRIVATE);
        return preferences.getInt("InvoiceNum",0);
    }

    public void setInvoiceNum(Integer nub) {
        SharedPreferences preferences = context.getSharedPreferences("Root",Context.MODE_PRIVATE);
        SharedPreferences.Editor e = preferences.edit();
        e.putInt("InvoiceNum",nub);
        e.apply();
    }
    public Integer getTheme() {
        SharedPreferences preferences = context.getSharedPreferences("Root",Context.MODE_PRIVATE);
        return preferences.getInt("theme",0);
    }

    public void setTheme(Integer theme) {
        SharedPreferences preferences = context.getSharedPreferences("Root",Context.MODE_PRIVATE);
        SharedPreferences.Editor e = preferences.edit();
        e.putInt("theme",theme);
        e.apply();
    }
}
