package com.qashar.mypersonalaccounting.RoomDB.Dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.qashar.mypersonalaccounting.RoomDB.DateConverter;
import com.qashar.mypersonalaccounting.Models.Wallet;

import java.util.List;

@Dao
@TypeConverters(DateConverter.class)
public interface WalletDao {
    @Insert
    void insertWallet(Wallet wallet);
    @Update
    void updateWallet(Wallet wallet);
    @Delete
    void deleteWallet(Wallet wallet);
    @Query("select * from  Wallet ")
    LiveData<List<Wallet>> getAllWallets();
    @Query("select * from  Wallet where status=:status ")
    LiveData<List<Wallet>> getAllTrueWallets(boolean status);
    @Query("select * from  Wallet where id=:id  ")
    LiveData<List<Wallet>> getAllWalletById(Integer id);

    @Query("select * from  Wallet where name=:name  ")
    LiveData<List<Wallet>> getAllWalletByName(String name);
    @Query("select * from  Wallet where addedAt >=:from and addedAt <=:to and status=:status ")
    LiveData<List<Wallet>> getAllWalletByDate(Long from,Long to,boolean status);
    @Query("select * from  Wallet where addedAt >=:from and addedAt <=:to and name=:name and status=:status ")
    LiveData<List<Wallet>> getAllWalletByDateAndName(Long from,Long to,String name,boolean status);
    }

