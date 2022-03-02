package com.qashar.mypersonalaccounting.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.qashar.mypersonalaccounting.CountriesCurrency.CurrencyActivity;
import com.qashar.mypersonalaccounting.Fragments.ItemListDialogFragment;
import com.qashar.mypersonalaccounting.InterFace.SelectType;
import com.qashar.mypersonalaccounting.Models.Wallet;
import com.qashar.mypersonalaccounting.R;
import com.qashar.mypersonalaccounting.RoomDB.MyViewModels;
import com.qashar.mypersonalaccounting.databinding.ActivityAddWalletBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddWalletActivity extends AppCompatActivity implements SelectType {
    private ActivityAddWalletBinding binding;
    private ItemListDialogFragment fragment;
    String a,b,c;
    private SharedPreferences preferences;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
    private MyViewModels models;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddWalletBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        models = new MyViewModels(getApplication());
        preferences = getSharedPreferences("ROOT",MODE_PRIVATE);
        binding.currency.setText(preferences.getString("currency","$"));


        a = getResources().getString(R.string.Nagdy);
        b = getResources().getString(R.string.CreditCard);
        c = getResources().getString(R.string.More);
        binding.textView4.setText(a);
        binding.selectType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 fragment = new ItemListDialogFragment(AddWalletActivity.this);
                fragment.show(getSupportFragmentManager(),"");
            }
        });
        binding.currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CurrencyActivity.class);
                intent.putExtra("type","addCurrency");
                startActivity(intent);

            }
        });
    }
    public static Long toLong(Date date){
        return date==null?null:date.getTime();
    }

    public void onAdd(View view){
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
        Wallet wallet = new Wallet(name,price,curr,binding.textView4.getText().toString(),
                true,date,0);
        if (name.isEmpty()){
            Snackbar.make(view,"a",Snackbar.LENGTH_SHORT).show();
        }else if (binding.etPrice.getText().toString().isEmpty()){
            Snackbar.make(view,"b",Snackbar.LENGTH_SHORT).show();
        }else {
            models.insertWallet(wallet);
            finish();
        }
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