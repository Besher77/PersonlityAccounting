package com.qashar.mypersonalaccounting.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.qashar.mypersonalaccounting.CountriesCurrency.CurrencyActivity;
import com.qashar.mypersonalaccounting.InterFace.SelectType;
import com.qashar.mypersonalaccounting.R;
import com.qashar.mypersonalaccounting.RoomDB.MyViewModels;
import com.qashar.mypersonalaccounting.Models.Wallet;
import com.qashar.mypersonalaccounting.databinding.ActivityAddWalletBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BlankFragment extends DialogFragment implements SelectType {
    private ActivityAddWalletBinding binding;
    private ItemListDialogFragment fragment;
    String a,b,c;
    private SharedPreferences preferences;
    private MyViewModels models;
    Long date = toLong(new Date());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ActivityAddWalletBinding.inflate(getLayoutInflater());
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        models = new MyViewModels(getActivity().getApplication());
        a = getResources().getString(R.string.Nagdy);
        b = getResources().getString(R.string.CreditCard);
        c = getResources().getString(R.string.More);
        binding.textView4.setText(a);
        binding.selectType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new ItemListDialogFragment(new SelectType() {
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
                });
                fragment.show(getActivity().getSupportFragmentManager(),"");
            }
        });
        binding.currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CurrencyActivity.class);
                intent.putExtra("type","addCurrency");
                startActivity(intent);

            }
        });
        binding.addWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date s = new Date();
                SimpleDateFormat month = new SimpleDateFormat("MM");
                SimpleDateFormat day = new SimpleDateFormat("dd");
                SimpleDateFormat year = new SimpleDateFormat("yyyy");
                try {
                    date = toLong(sdf.parse(year.format(s)+"-"+month.format(s)+"-"+day.format(s)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String name = binding.etName.getText().toString();
                String curr = binding.currency.getText().toString();
                Float price = Float.parseFloat(binding.etPrice.getText().toString());
                Wallet wallet = new Wallet(name,price,curr,binding.textView4.getText().toString(),true,date,0);
                if (name.isEmpty()){
                    Snackbar.make(v,"a",Snackbar.LENGTH_SHORT).show();
                }else if (binding.etPrice.getText().toString().isEmpty()){
                    Snackbar.make(v,"b",Snackbar.LENGTH_SHORT).show();
                }else {
                    models.insertWallet(wallet);
                    getDialog().dismiss();
                }
            }
        });
        return binding.getRoot();
    }
    public static Long toLong(Date date){
        return date==null?null:date.getTime();
    }


    @Override
    public void onResume() {
        super.onResume();
        preferences = getActivity().getSharedPreferences("ROOT",MODE_PRIVATE);
        binding.currency.setText(preferences.getString("currency","$"));
    }

    public void onAdd(View view){

    }

    @Override
    public void onSelect(String name) {

    }
}