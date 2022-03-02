package com.qashar.mypersonalaccounting.ui;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.qashar.mypersonalaccounting.Fragments.NewTodoFragment;
import com.qashar.mypersonalaccounting.RoomDB.MyViewModels;
import com.qashar.mypersonalaccounting.Models.Todo;
import com.qashar.mypersonalaccounting.Adapters.TodoAdapter;
import com.qashar.mypersonalaccounting.databinding.FragmentThirdBinding;

import java.util.List;


@SuppressLint("ValidFragment")
public class ThirdFragment extends android.app.Fragment {
    private FragmentThirdBinding binding;
    private MyViewModels viewModels;
    private FragmentManager getSupportFragmentManager;

    public ThirdFragment(FragmentManager getSupportFragmentManager) {
        this.getSupportFragmentManager = getSupportFragmentManager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            binding = FragmentThirdBinding.inflate(getLayoutInflater());
            viewModels = new MyViewModels(getActivity().getApplication());


            putTodos();
                getChildFragmentManager().beginTransaction().add(new NewTodoFragment(getSupportFragmentManager),"Fragmnt").commit();

        return binding.getRoot();
    }

    private void putTodos() {
        viewModels.getAllTodos().observe((LifecycleOwner) getActivity(), new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {

                TodoAdapter adapter = new TodoAdapter(getChildFragmentManager(),getActivity(),viewModels,getActivity(),todos);
                binding.todoRV.setAdapter(adapter);
                binding.todoRV.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        });

    }

}