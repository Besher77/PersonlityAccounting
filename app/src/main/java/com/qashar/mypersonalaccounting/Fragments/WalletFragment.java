package com.qashar.mypersonalaccounting.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.qashar.mypersonalaccounting.InterFace.SelectWallet;
import com.qashar.mypersonalaccounting.RoomDB.MyViewModels;
import com.qashar.mypersonalaccounting.Models.Wallet;
import com.qashar.mypersonalaccounting.Adapters.WalletAdapter;
import com.qashar.mypersonalaccounting.databinding.FragmentWalletBinding;

import java.util.List;

public class WalletFragment extends BottomSheetDialogFragment {
    FragmentWalletBinding binding;
    private MyViewModels models;
    private SelectWallet selectWallet;
    public WalletFragment(SelectWallet selectWallet) {
        this.selectWallet = selectWallet;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWalletBinding.inflate(getLayoutInflater());
        models = new MyViewModels(getActivity().getApplication());
        binding.addNewWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().add(new BlankFragment(),"Fragmnt").commit();

            }
        });
        models.getAllWallets().observe(getActivity(), new Observer<List<Wallet>>() {
            @Override
            public void onChanged(List<Wallet> wallets) {
                WalletAdapter adapter = new WalletAdapter(models,new SelectWallet() {
                    @Override
                    public void selectWallet(Wallet wallet) {
                        selectWallet.selectWallet(wallet);
                    }
                }, getActivity(), wallets);
                binding.rvwALLWT.setAdapter(adapter);
                binding.rvwALLWT.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });


        return binding.getRoot();
    }

    private void putData() {
    }


}
