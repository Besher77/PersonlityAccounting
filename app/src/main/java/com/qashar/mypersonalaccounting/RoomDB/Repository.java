package com.qashar.mypersonalaccounting.RoomDB;


import android.app.Application;

import androidx.lifecycle.LiveData;

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

import java.util.Date;
import java.util.List;

public class Repository {
    WalletDao walletDao;
    ModelDao modelDao;
    GroupDao groupDao;
    TaskDao taskDao;
    CategoryDao categoryDao;
    TodoDao todoDao;

    public Repository(Application application) {
        MyRoomDB db = MyRoomDB.getDataBase(application);
        walletDao = db.walletDao();
        modelDao = db.modelDao();
        groupDao = db.groupDao();
        todoDao = db.todoDao();
        taskDao = db.taskDao();
        categoryDao = db.categoryDao();

    }
    public void insertTask(Task task) {
        MyRoomDB.ex.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.insertTask(task);
            }
        });
    }
    public void deleteTask(Task task) {
        MyRoomDB.ex.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.deleteTask(task);
            }
        });
    }
    public void updateTask(Task task) {
        MyRoomDB.ex.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.updateTask(task);
            }
        });
    }
    public LiveData<List<Task>> getAllTasks() {
        return (LiveData<List<Task>>) taskDao.getAllTasks();
    }    public LiveData<List<Task>> getSingleData(String wallet,String date) {
        return (LiveData<List<Task>>) taskDao.getSingleData(wallet,date);
    }
    public LiveData<List<Task>> getAllTasksBySingleItem() {
        return (LiveData<List<Task>>) taskDao.getAllTasksBySingleItem();
    }  public LiveData<List<Task>> getAllTasksBySingleItem(String wallet) {
        return (LiveData<List<Task>>) taskDao.getAllTasksBySingleItem(wallet);
    }
    public LiveData<List<Task>> getAllTasksByDateOfToday(Long addedAt, String wallet) {
        return (LiveData<List<Task>>) taskDao.getAllTasksByDateOfToday(addedAt,wallet);
    }
    public LiveData<List<Task>> getAllTasksByDateAndWalletName(Long from,Long to ,String walletName) {
        return (LiveData<List<Task>>) taskDao.getAllTasksByDateAndWalletName(from,to,walletName);
    }
    public LiveData<List<Task>> getAllTasksByDateAndWalletNameAndPrior(Long from,Long to ,String walletName,String pri) {
        return (LiveData<List<Task>>) taskDao.getAllTasksByDateAndWalletNameAndPrior(from,to,walletName,pri);
    }
    public LiveData<List<Task>> getAllTasksByWallet(String wallet) {
        return (LiveData<List<Task>>) taskDao.getAllTasksByWallet(wallet);
    }
    public LiveData<List<Task>> getAllTasksByDate(String date) {
        return (LiveData<List<Task>>) taskDao.getAllTasksByDate(date);
    }public LiveData<List<Task>> getAllTasksByDate(Long from,Long to) {
        return (LiveData<List<Task>>) taskDao.getAllTasksByDate(from,to);
    }
    public LiveData<List<Task>> getAllTasksByPriority(String priority) {
        return (LiveData<List<Task>>) taskDao.getAllTasksByByPriority(priority);
    }
    public LiveData<List<Task>> getAllTasksByID(Integer id) {
        return (LiveData<List<Task>>) taskDao.getAllTasksByID(id);
    }
    public LiveData<List<Task>> getTaskByID(Integer id) {
        return (LiveData<List<Task>>) taskDao.getTaskByID(id);
    }

    public void insertCategory(Category group) {
        MyRoomDB.ex.execute(new Runnable() {
            @Override
            public void run() {
                categoryDao.insertCategory(group);
            }
        });
    }
    public void deleteCategory(Category group) {
        MyRoomDB.ex.execute(new Runnable() {
            @Override
            public void run() {
                categoryDao.deleteCategory(group);
            }
        });
    }
    public void updateCategory(Category group) {
        MyRoomDB.ex.execute(new Runnable() {
            @Override
            public void run() {
                categoryDao.updateCategory(group);
            }
        });
    }
    public LiveData<List<Category>> getAllCategories() {
        return (LiveData<List<Category>>) categoryDao.getAllCategories();
    }
    public LiveData<List<Category>> getCategoryByID(Integer id) {
        return (LiveData<List<Category>>) categoryDao.getCategoryByID(id);
    }


    public void insertTodo(Todo todo) {
        MyRoomDB.ex.execute(new Runnable() {
            @Override
            public void run() {
                todoDao.insertTodo(todo);
            }
        });
    }
    public void deleteTodo(Todo todo) {
        MyRoomDB.ex.execute(new Runnable() {
            @Override
            public void run() {
                todoDao.deleteTodo(todo);
            }
        });
    }
    public void updateTodo(Todo todo) {
        MyRoomDB.ex.execute(new Runnable() {
            @Override
            public void run() {
                todoDao.updateTodo(todo);
            }
        });
    }
    public LiveData<List<Todo>> getAllTodos() {
        return (LiveData<List<Todo>>) todoDao.getAllTodos();
    }
    public LiveData<List<Todo>> getTodoByID(Integer id) {
        return (LiveData<List<Todo>>) todoDao.getTodoByID(id);
    }



    public void insertGroup(Group group) {
        MyRoomDB.ex.execute(new Runnable() {
            @Override
            public void run() {
                groupDao.insertGroup(group);
            }
        });
    }
    public void deleteGroup(Group group) {
        MyRoomDB.ex.execute(new Runnable() {
            @Override
            public void run() {
                groupDao.deleteGroup(group);
            }
        });
    }
    public void updateGroup(Group group) {
        MyRoomDB.ex.execute(new Runnable() {
            @Override
            public void run() {
                groupDao.updateGroup(group);
            }
        });
    }
    public LiveData<List<Group>> getAllGroupss() {
        return (LiveData<List<Group>>) groupDao.getAllGroups();
    }
    public LiveData<List<Group>> getGroupByID(Integer id) {
        return (LiveData<List<Group>>) groupDao.getGroupByID(id);
    }


    public void insertModel(Model model) {
        MyRoomDB.ex.execute(new Runnable() {
            @Override
            public void run() {
                modelDao.insertModel(model);
            }
        });
    }
    public void deleteModel(Model model) {
        MyRoomDB.ex.execute(new Runnable() {
            @Override
            public void run() {
                modelDao.deleteModel(model);
            }
        });
    }
    public void updateModel(Model model) {
        MyRoomDB.ex.execute(new Runnable() {
            @Override
            public void run() {
                modelDao.updateModel(model);
            }
        });
    }
    public LiveData<List<Model>> getAllModels() {
        return (LiveData<List<Model>>) modelDao.getAllModels();
    }
    public LiveData<List<Model>> getAllModelByDate(Date date) {
        return (LiveData<List<Model>>) modelDao.getAllModelByDate(date);
    }


    public void insertWallet(Wallet wallet) {
        MyRoomDB.ex.execute(new Runnable() {
            @Override
            public void run() {
                walletDao.insertWallet(wallet);
            }
        });
    }
    public void deleteWallet(Wallet wallet) {
        MyRoomDB.ex.execute(new Runnable() {
            @Override
            public void run() {
                walletDao.deleteWallet(wallet);
            }
        });
    }
    public void updateWallet(Wallet wallet) {
        MyRoomDB.ex.execute(new Runnable() {
            @Override
            public void run() {
                walletDao.updateWallet(wallet);
            }
        });
    }
    public LiveData<List<Wallet>> getAllWallets() {
        return (LiveData<List<Wallet>>) walletDao.getAllWallets();
    }
    public LiveData<List<Wallet>> getAllTrueWallets() {
        return (LiveData<List<Wallet>>) walletDao.getAllTrueWallets(true);
    }
    public LiveData<List<Wallet>> getAllWalletById(Integer id) {
        return (LiveData<List<Wallet>>) walletDao.getAllWalletById(id);
    }
    public LiveData<List<Wallet>> getAllWalletByName(String name) {
        return (LiveData<List<Wallet>>) walletDao.getAllWalletByName(name);
    }
    public LiveData<List<Wallet>> getAllWalletByDate(Long from , Long to) {
        return (LiveData<List<Wallet>>) walletDao.getAllWalletByDate(from,to,true);
    }
    public LiveData<List<Wallet>> getAllWalletByDateAndName(Long from , Long to,String wallet) {
        return (LiveData<List<Wallet>>) walletDao.getAllWalletByDateAndName(from,to,wallet,true);
    }

}