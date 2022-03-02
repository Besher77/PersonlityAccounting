package com.qashar.mypersonalaccounting.RoomDB;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.qashar.mypersonalaccounting.Models.Group;
import com.qashar.mypersonalaccounting.Models.Model;
import com.qashar.mypersonalaccounting.Models.Task;
import com.qashar.mypersonalaccounting.Category.Category;
import com.qashar.mypersonalaccounting.RoomDB.Dao.CategoryDao;
import com.qashar.mypersonalaccounting.RoomDB.Dao.GroupDao;
import com.qashar.mypersonalaccounting.RoomDB.Dao.ModelDao;
import com.qashar.mypersonalaccounting.RoomDB.Dao.TaskDao;
import com.qashar.mypersonalaccounting.RoomDB.Dao.TodoDao;
import com.qashar.mypersonalaccounting.RoomDB.Dao.WalletDao;
import com.qashar.mypersonalaccounting.Models.Todo;
import com.qashar.mypersonalaccounting.Models.Wallet;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Category.class
        ,Task.class,Group.class,Model.class,
        Wallet.class, Todo.class},version = 2,exportSchema = true)
public abstract class MyRoomDB extends RoomDatabase {
    public static final String DB_NAME = "myDB.db";

    public abstract WalletDao walletDao();
    public abstract TodoDao todoDao();
    public abstract ModelDao modelDao();
    public abstract GroupDao groupDao();
    public abstract TaskDao taskDao();
    public abstract CategoryDao categoryDao();

    public static volatile MyRoomDB INSTANCE;
    private static final int NUMBER_OF_THREADS=4;
    static final ExecutorService ex =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static MyRoomDB getDataBase(Context context){
        if (INSTANCE==null){
            synchronized (MyRoomDB.class){
                if (INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),MyRoomDB.class,DB_NAME).build();
                }
            }
        }
        return INSTANCE;
    }


    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
