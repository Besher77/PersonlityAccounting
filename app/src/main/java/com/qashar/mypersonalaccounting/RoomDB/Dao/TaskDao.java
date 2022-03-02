package com.qashar.mypersonalaccounting.RoomDB.Dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.qashar.mypersonalaccounting.Models.Task;
import com.qashar.mypersonalaccounting.RoomDB.DateConverter;

import java.util.List;

@Dao
@TypeConverters(DateConverter.class)
public interface TaskDao {
    @Insert
    void insertTask(Task task);
    @Update
    void updateTask(Task task);
    @Delete
    void deleteTask(Task task);
    @Query("select * from  Task ")
    LiveData<List<Task>> getAllTasks();
    @Query("select * from  Task where wallet=:wallet and date=:date ")
    LiveData<List<Task>> getSingleData(String wallet,String date);
    @Query("select * from  Task group by date ")
    LiveData<List<Task>> getAllTasksBySingleItem();
    @Query("select * from  Task group by date and wallet=:wallet ")
    LiveData<List<Task>> getAllTasksBySingleItem(String wallet);
    @Query("select * from  Task where addedAt =:addedAt and wallet=:wallet  ")
    LiveData<List<Task>> getAllTasksByDateOfToday(Long addedAt, String wallet);
    @Query("select * from  Task where addedAt >=:from and addedAt<=:to and wallet=:walletName ")
    LiveData<List<Task>> getAllTasksByDateAndWalletName(Long from,Long to,String walletName);
    @Query("select * from  Task where addedAt >=:from and addedAt<=:to and wallet=:walletName and emoji=:pri ")
    LiveData<List<Task>> getAllTasksByDateAndWalletNameAndPrior(Long from,Long to,String walletName,String pri);
    @Query("select * from  Task where wallet=:wallet ")
    LiveData<List<Task>> getAllTasksByWallet(String wallet);
    @Query("select * from  Task where date=:date")
    LiveData<List<Task>> getAllTasksByDate(String date);
    @Query("select * from  Task where addedAt >=:from and addedAt<=:to")
    LiveData<List<Task>> getAllTasksByDate(Long from,Long to);
    @Query("select * from  Task where emoji=:emoji")
    LiveData<List<Task>> getAllTasksByByPriority(String emoji);

    @Query("select * from  Task where id=:id")
    LiveData<List<Task>> getAllTasksByID(Integer id);
    @Query("select * from  Task where id=:id")
    LiveData<List<Task>> getTaskByID(Integer id);
    }

