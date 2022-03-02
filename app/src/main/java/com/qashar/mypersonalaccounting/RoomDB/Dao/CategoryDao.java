package com.qashar.mypersonalaccounting.RoomDB.Dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.qashar.mypersonalaccounting.Category.Category;
import com.qashar.mypersonalaccounting.RoomDB.DateConverter;

import java.util.List;

@Dao
@TypeConverters(DateConverter.class)
public interface CategoryDao {
    @Insert
    void insertCategory(Category group);
    @Update
    void updateCategory(Category group);
    @Delete
    void deleteCategory(Category group);
    @Query("select * from  Category ")
    LiveData<List<Category>> getAllCategories();
    @Query("select * from  Category where id=:id")
    LiveData<List<Category>> getCategoryByID(Integer id);
    }

