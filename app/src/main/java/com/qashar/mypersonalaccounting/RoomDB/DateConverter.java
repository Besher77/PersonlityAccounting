package com.qashar.mypersonalaccounting.RoomDB;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long mill){
        return mill==null?null:new Date(mill);
    }
    @TypeConverter
    public static Long toLong(Date date){
        return date==null?null:date.getTime();
    }

}
