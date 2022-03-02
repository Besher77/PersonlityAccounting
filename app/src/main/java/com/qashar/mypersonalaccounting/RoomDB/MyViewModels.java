package com.qashar.mypersonalaccounting.RoomDB;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.qashar.mypersonalaccounting.Models.Group;
import com.qashar.mypersonalaccounting.Models.Model;
import com.qashar.mypersonalaccounting.Models.Task;
import com.qashar.mypersonalaccounting.Category.Category;
import com.qashar.mypersonalaccounting.Models.Todo;
import com.qashar.mypersonalaccounting.Models.Wallet;

import java.util.Date;
import java.util.List;

public class MyViewModels extends AndroidViewModel {
    Repository repository;
    public MyViewModels(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void insertTodo(Todo todo) {
                repository.insertTodo(todo);

    }
    public void deleteTodo(Todo todo) {

                repository.deleteTodo(todo);

    }
    public void updateTodo(Todo todo) {
                repository.updateTodo(todo);
    }
    public LiveData<List<Todo>> getAllTodos() {
        return (LiveData<List<Todo>>) repository.getAllTodos();
    }
    public LiveData<List<Todo>> getTodoByID(Integer id) {
        return (LiveData<List<Todo>>) repository.getTodoByID(id);
    }



    public void insertTask(Task task) {
                repository.insertTask(task);
    }
    public void deleteTask(Task task) {
                repository.deleteTask(task);
    }
    public void updateTask(Task task) {
                repository.updateTask(task);
    }
    public LiveData<List<Task>> getAllTasks() {
        return (LiveData<List<Task>>) repository.getAllTasks();
    }
    public LiveData<List<Task>> getSingleData(String wallet,String date) {
        return (LiveData<List<Task>>) repository.getSingleData(wallet,date);
    }
    public LiveData<List<Task>> getAllTasksBySingleItem() {
        return (LiveData<List<Task>>) repository.getAllTasksBySingleItem();
    }
    public LiveData<List<Task>> getAllTasksBySingleItem(String wallet) {
        return (LiveData<List<Task>>) repository.getAllTasksBySingleItem(wallet);
    }
    public LiveData<List<Task>> getAllTasksByDateOfToday(Long addedAt, String wallet) {
        return (LiveData<List<Task>>) repository.getAllTasksByDateOfToday(addedAt,wallet);
    }
    public LiveData<List<Task>>
    getAllTasksByDateAndWalletName(Long from,Long to,String waleetName) {
        return (LiveData<List<Task>>) repository.getAllTasksByDateAndWalletName(from,to,waleetName);
    }   public LiveData<List<Task>>
    getAllTasksByDate(Long from,Long to) {
        return (LiveData<List<Task>>) repository.getAllTasksByDate(from,to);
    }
    public LiveData<List<Task>> getAllTasksByDateAndWalletNameAndPrior(Long from,Long to,String waletName,String pri) {
        return (LiveData<List<Task>>) repository.getAllTasksByDateAndWalletNameAndPrior(from,to,waletName,pri);
    }
    public LiveData<List<Task>> getAllTasksByWallet(String wallet) {
        return (LiveData<List<Task>>) repository.getAllTasksByWallet(wallet);
    }
    public LiveData<List<Task>> getAllTasksByDate(String date) {
        return (LiveData<List<Task>>) repository.getAllTasksByDate(date);
    }
    public LiveData<List<Task>> getAllTasksByPriority(String priority) {
        return (LiveData<List<Task>>) repository.getAllTasksByPriority(priority);
    }
    public LiveData<List<Task>> getAllTasksByID(Integer id) {
        return (LiveData<List<Task>>) repository.getAllTasksByID(id);
    }
    public LiveData<List<Task>> getTaskByID(Integer id) {
        return (LiveData<List<Task>>) repository.getTaskByID(id);
    }

    public void insertCategory(Category group) {
        repository.insertCategory(group);
    }
    public void deleteCategory(Category group) {
        repository.deleteCategory(group);
    }
    public void updateCategory(Category group) {
        repository.updateCategory(group);
    }
    public LiveData<List<Category>> getAllCategories() {
        return (LiveData<List<Category>>) repository.getAllCategories();
    }
    public LiveData<List<Category>> getCategoryByID(Integer id) {
        return (LiveData<List<Category>>) repository.getCategoryByID(id);
    }


    public void insertGroup(Group group) {
        repository.insertGroup(group);
    }
    public void deleteGroup(Group group) {
        repository.deleteGroup(group);
    }
    public void updateGroup(Group group) {
        repository.updateGroup(group);
    }
    public LiveData<List<Group>> getAllGroups() {
        return (LiveData<List<Group>>) repository.getAllGroupss();
    }
    public LiveData<List<Group>> getGroupByID(Integer id) {
        return (LiveData<List<Group>>) repository.getGroupByID(id);
    }

    public void insertModel(Model model) {
        repository.insertModel(model);
    }
    public void deleteModel(Model model) {
        repository.deleteModel(model);
    }
    public void updateModel(Model model) {
        repository.updateModel(model);
    }
    public LiveData<List<Model>> getAllModels() {
        return (LiveData<List<Model>>) repository.getAllModels();
    }
    public LiveData<List<Model>> getAllModelByDate(Date date) {
        return (LiveData<List<Model>>) repository.getAllModelByDate(date);
    }

   


    public void insertWallet(Wallet wallet) {
       repository.insertWallet(wallet);
    }

    public void deleteWallet(Wallet wallet) {
      repository.deleteWallet(wallet);
    }

    public void updateWallet(Wallet wallet) {
                repository.updateWallet(wallet);
    }

    public LiveData<List<Wallet>> getAllWallets() {
        return (LiveData<List<Wallet>>) repository.getAllWallets();
    }
    public LiveData<List<Wallet>> getAllTrueWallets() {
        return (LiveData<List<Wallet>>) repository.getAllTrueWallets();
    }
    public LiveData<List<Wallet>> getAllWalletById(Integer id) {
        return (LiveData<List<Wallet>>) repository.getAllWalletById(id);
    }
    public LiveData<List<Wallet>> getAllWalletByName(String name) {
        return (LiveData<List<Wallet>>) repository.getAllWalletByName(name);
    }
    public LiveData<List<Wallet>> getAllWalletByDate(Long from,Long to) {
        return (LiveData<List<Wallet>>) repository.getAllWalletByDate(from,to);
    }
    public LiveData<List<Wallet>> getAllWalletByDateAndName(Long from,Long to,String walletName) {
        return (LiveData<List<Wallet>>) repository.getAllWalletByDateAndName(from,to,walletName);
    }



}
