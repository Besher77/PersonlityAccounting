package com.qashar.mypersonalaccounting.RoomDB.Dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.qashar.mypersonalaccounting.Models.Group;
import com.qashar.mypersonalaccounting.RoomDB.DateConverter;

import java.util.List;

@Dao
@TypeConverters(DateConverter.class)
public interface GroupDao {
    @Insert
    void insertGroup(Group group);
    @Update
    void updateGroup(Group group);
    @Delete
    void deleteGroup(Group group);
    @Query("select * from  `Group` ")
    LiveData<List<Group>> getAllGroups();
    @Query("select * from  `Group` where id=:id")
    LiveData<List<Group>> getGroupByID(Integer id);
    }

