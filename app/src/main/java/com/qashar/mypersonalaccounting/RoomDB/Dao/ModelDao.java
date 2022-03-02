package com.qashar.mypersonalaccounting.RoomDB.Dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.qashar.mypersonalaccounting.Models.Model;
import com.qashar.mypersonalaccounting.RoomDB.DateConverter;

import java.util.Date;
import java.util.List;

@Dao
@TypeConverters(DateConverter.class)
public interface ModelDao {
    @Insert
    void insertModel(Model model);
    @Update
    void updateModel(Model model);
    @Delete
    void deleteModel(Model model);
    @Query("select * from  Model")
    LiveData<List<Model>> getAllModels();
    @Query("select * from  Model where date=:date  ")
    LiveData<List<Model>> getAllModelByDate(Date date);
    }

