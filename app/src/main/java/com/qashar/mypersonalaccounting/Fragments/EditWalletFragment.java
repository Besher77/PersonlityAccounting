package com.qashar.mypersonalaccounting.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.google.android.material.snackbar.Snackbar;
import com.qashar.mypersonalaccounting.CountriesCurrency.CurrencyActivity;
import com.qashar.mypersonalaccounting.InterFace.SelectType;
import com.qashar.mypersonalaccounting.Models.Task;
import com.qashar.mypersonalaccounting.R;
import com.qashar.mypersonalaccounting.RoomDB.MyViewModels;
import com.qashar.mypersonalaccounting.Models.Wallet;
import com.qashar.mypersonalaccounting.databinding.ActivityEditWalletBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EditWalletFragment extends DialogFragment implements SelectType {
    private ActivityEditWalletBinding binding;
    private MyViewModels models;
    private ItemListDialogFragment fragment;
    private Integer id = 0;
    private Float off = 0f;
    private Float on = 0f;
    private Float op = 0f;
    private String a,b,c;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = ActivityEditWalletBinding.inflate(getLayoutInflater());
        models = new MyViewModels(getActivity().getApplication());
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

         id = getArguments().getInt("id");
        putData();
        binding.deleteWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelete(v);
            }
        });
        binding.updateWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdate(v);
            }
        });



        binding.selectType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new ItemListDialogFragment((SelectType) getActivity());
                fragment.show(getChildFragmentManager(),"");
            }
        });
        binding.currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CurrencyActivity.class);
                intent.putExtra("type","editCurrency");
                startActivity(intent);

            }
        });
        return binding.getRoot();
    }
    public static Long toLong(Date date){
        return date==null?null:date.getTime();
    }
    public void onUpdate(View view){
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        Date s = new Date();
        Long date = null;
        try {
            date = toLong(sdf.parse(year.format(s)+"-"+month.format(s)+"-"+day.format(s)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String name = binding.etName.getText().toString();
        String curr = binding.currency.getText().toString();
        Float price = Float.parseFloat(binding.etPrice.getText().toString());
        Wallet wallet = new Wallet(id,name,price,curr,binding.textView4.getText().toString(),true,date,0);
        if (name.isEmpty()){
            Snackbar.make(view,"a",Snackbar.LENGTH_SHORT).show();
        }else if (binding.etPrice.getText().toString().isEmpty()){
            Snackbar.make(view,"b",Snackbar.LENGTH_SHORT).show();
        }else {
            models.updateWallet(wallet);
            getActivity().finish();
        }
    }
    public void onDelete(View view){
        models.getAllWalletById(id).observe(getActivity(), new Observer<List<Wallet>>() {
            @Override
            public void onChanged(List<Wallet> wallets) {
                models.deleteWallet(wallets.get(0));
                getDialog().dismiss();
            }
        });
    }


    private void putData() {
        models.getAllWalletById(id).observe(this, new Observer<List<Wallet>>() {
            @Override
            public void onChanged(List<Wallet> wallets) {
                models.getAllTasksByWallet(wallets.get(0).getName()).observe((LifecycleOwner) getActivity(), new Observer<List<Task>>() {
                    @Override
                    public void onChanged(List<Task> walletOperations) {
                        off = 0f;
                        on = 0f;
                        op = 0f;
                        for (int i = 0; i < walletOperations.size(); i++) {
                            if (walletOperations.get(i).isAddedAtWallet()){
                                off = off + walletOperations.get(i).getPrice();
                            }else if (walletOperations.get(i).getType().equals("on")){
                                on = on + walletOperations.get(i).getPrice();
                            } }
                        op = on-(off);

                        binding.etPrice.setText((wallets.get(0).getPrice()+op)+"");
                    }
                });



                a = getResources().getString(R.string.Nagdy);
                b = getResources().getString(R.string.CreditCard);
                c = getResources().getString(R.string.More);
                Wallet wallet = wallets.get(0);
                binding.etName.setText(wallet.getName());
                binding.etPrice.setText(""+wallet.getPrice());
                binding.currency.setText(""+wallet.getCurrency());
                binding.textView4.setText(""+wallet.getType());
                if (wallet.getType().equals(a)){
                    binding.imageView2.setImageResource(R.drawable.ic);
                }else if (wallet.getType().equals(b)){
                    binding.imageView2.setImageResource(R.drawable.cre);
                }else {
                    binding.imageView2.setImageResource(R.drawable.more);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences preferences = getActivity().getSharedPreferences("ROOT", Context.MODE_PRIVATE);
        binding.currency.setText(preferences.getString("currency","$"));
    }

    @Override
    public void onSelect(String name) {
        binding.textView4.setText(name);
        fragment.dismiss();
        if (name.equals(a)){
            binding.imageView2.setImageResource(R.drawable.ic);
        }else if (name.equals(b)){
            binding.imageView2.setImageResource(R.drawable.cre);
        }else {
            binding.imageView2.setImageResource(R.drawable.more);
        }
    }
}