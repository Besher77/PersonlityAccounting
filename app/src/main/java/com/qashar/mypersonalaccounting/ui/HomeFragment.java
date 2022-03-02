package com.qashar.mypersonalaccounting.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.qashar.mypersonalaccounting.Activities.AddModelActivity;
import com.qashar.mypersonalaccounting.Adapters.MainAdapter;
import com.qashar.mypersonalaccounting.Models.Task;
import com.qashar.mypersonalaccounting.Fragments.PrintFragment;
import com.qashar.mypersonalaccounting.InterFace.SelectWallet;
import com.qashar.mypersonalaccounting.R;
import com.qashar.mypersonalaccounting.RoomDB.MyViewModels;
import com.qashar.mypersonalaccounting.Adapters.SelectWalletAdapter;
import com.qashar.mypersonalaccounting.Models.Wallet;
import com.qashar.mypersonalaccounting.Activities.WalletActivity;

import com.qashar.mypersonalaccounting.databinding.FragmentHomeBinding;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HomeFragment extends android.app.Fragment {
    private com.qashar.mypersonalaccounting.databinding.FragmentHomeBinding binding;
    private MyViewModels viewModels;
    private String selectedWallet = "*";
    private FragmentManager getSupportFragmentManager;;
    private ActivityResultLauncher<String> arl;

    public HomeFragment(FragmentManager getSupportFragmentManager) {
        this.getSupportFragmentManager = getSupportFragmentManager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        viewModels = new MyViewModels(getActivity().getApplication());
        pudWallets();
        getAllDataFromRoomDB();
        binding.icCancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.lnSearch.setVisibility(View.GONE);
                binding.ln.setVisibility(View.VISIBLE);
            }
        });
        binding.icMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity(),binding.icMore);
                popupMenu.getMenuInflater().inflate(R.menu.main_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.mangment:startActivity(new Intent(getActivity(), WalletActivity.class));
                            case R.id.search:
                                binding.lnSearch.setVisibility(View.VISIBLE);
                                binding.ln.setVisibility(View.GONE);
                                break;
                            case R.id.print:
                                getChildFragmentManager().beginTransaction().add(new PrintFragment(getSupportFragmentManager),"Fragmnt").commit();

                                break;
                        }
                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });
        binding.newTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddModelActivity.class));
            }
        });
        return binding.getRoot();
    }
    public static Long toLong(Date date){
        return date==null?null:date.getTime();
    }

    private void pudWallets() {
        viewModels.getAllTrueWallets().observe((LifecycleOwner) getActivity(), new Observer<List<Wallet>>() {
            @Override
            public void onChanged(List<Wallet> wallets) {
                List<Wallet> list = new ArrayList<>();
                list.add(new Wallet(-1,getResources().getString(R.string.all),0f,"","",true,toLong(new Date()),0));
                for (int i = 0; i < wallets.size(); i++) {
                    list.add(wallets.get(i));
                }
                SelectWalletAdapter adapter = new SelectWalletAdapter(selectedWallet, viewModels, new SelectWallet() {
                    @Override
                    public void selectWallet(Wallet wallet) {
                        selectedWallet = wallet.getName();
                        if (wallet.getId().equals(-1)){
                            getAllDataFromRoomDB();
                        }else{
                         getAllDataFromRoomDBByWalletName(wallet.getName());
                        }

                    }
                }, getActivity(), list);
                binding.walletRV.setAdapter(adapter);
                binding.walletRV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
            }
        });
    }
    private void getAllDataFromRoomDB(){
        viewModels.getAllTasksBySingleItem().observe((LifecycleOwner) getActivity(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                if (tasks.size()==0){
                    binding.rv.setVisibility(View.GONE);
                    binding.imageView5.setVisibility(View.VISIBLE);
                }else {
                    for (int i = 0; i < tasks.size(); i++) {

                    }
                    binding.rv.setVisibility(View.VISIBLE);
                    binding.imageView5.setVisibility(View.GONE);
                    MainAdapter adapter = new MainAdapter(viewModels, getActivity(), tasks,selectedWallet);
                    binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    binding.rv.setAdapter(adapter);
                }
            }
        });
    }
    private void getAllDataFromRoomDBByWalletName(String wallet){
        viewModels.getAllTasksBySingleItem().observe((LifecycleOwner) getActivity(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                List<Task> myTasks = new ArrayList<>();
                for (int i = 0; i < tasks.size(); i++) {
                    if (tasks.get(i).getWallet().equals(wallet)){
                        myTasks.add(tasks.get(i));
                    }
                }
                if (myTasks.size()==0){
                    binding.imageView5.setVisibility(View.VISIBLE);
                    binding.rv.setVisibility(View.GONE);
//                    binding.lnPrice.setVisibility(View.GONE);
                }else {
                    binding.imageView5.setVisibility(View.GONE);
                    binding.rv.setVisibility(View.VISIBLE);
                    MainAdapter adapter = new MainAdapter(viewModels, getActivity(), myTasks, selectedWallet);
                    binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    binding.rv.setAdapter(adapter);
                }


            }
        });
    }


}