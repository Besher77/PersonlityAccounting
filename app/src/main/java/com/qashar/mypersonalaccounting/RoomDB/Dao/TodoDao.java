package com.qashar.mypersonalaccounting.RoomDB.Dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.qashar.mypersonalaccounting.RoomDB.DateConverter;
import com.qashar.mypersonalaccounting.Models.Todo;

import java.util.List;

@Dao
@TypeConverters(DateConverter.class)
public interface TodoDao {
    @Insert
    void insertTodo(Todo todo);
    @Update
    void updateTodo(Todo todo);
    @Delete
    void deleteTodo(Todo todo);
    @Query("select * from  Todo ")
    LiveData<List<Todo>> getAllTodos();
    @Query("select * from  Todo where id=:id")
    LiveData<List<Todo>> getTodoByID(Integer id);
    }

