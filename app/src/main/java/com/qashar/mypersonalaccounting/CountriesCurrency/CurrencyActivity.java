package com.qashar.mypersonalaccounting.CountriesCurrency;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.qashar.mypersonalaccounting.InterFace.SelectCurrency;
import com.qashar.mypersonalaccounting.R;
import com.qashar.mypersonalaccounting.databinding.ActivityCurrncyBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CurrencyActivity extends AppCompatActivity implements SelectCurrency {
    private ActivityCurrncyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCurrncyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.i("QQQQ",loadJSONFromAsset());
        try {
            loadData(loadJSONFromAsset());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Currency> currencyList = new ArrayList<>();
        currencyList.add(new Currency("ليرة تركية","ل.ت",R.drawable.flag_try));
        currencyList.add(new Currency("ريال يمني","ر.ي",R.drawable.flag_try));
        currencyList.add(new Currency("ريال سعودي","ر.s",R.drawable.flag_try));
        currencyList.add(new Currency("درهم اماراتي","د.أ",R.drawable.flag_try));
        currencyList.add(new Currency("جنية مصري","ج.م",R.drawable.flag_try));
        currencyList.add(new Currency("دولار امريكي","$",R.drawable.flag_try));
        currencyList.add(new Currency("دينار كويتي","د.ك",R.drawable.flag_try));
        currencyList.add(new Currency("ريال عماني","ر.ع",R.drawable.flag_try));

        pudData(currencyList);

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        ArrayList<Currency> currencies = new ArrayList<>();

                for (int i = 0; i <currencyList.size() ; i++) {
                    if (currencyList.get(i).getName().contains(s.toString())){
                    currencies.add( currencyList.get(i));
                        pudData(currencies);
                    }
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void loadData(String loadJSONFromAsset)throws Exception {
        JSONObject object = new JSONObject(loadJSONFromAsset);
        JSONArray array = new JSONArray(object);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("currency.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    private void pudData(ArrayList<Currency> currencyList) {
        CurrencyAdapter adapter = new CurrencyAdapter(CurrencyActivity.this,this,currencyList);
        binding.rv.setAdapter(adapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void select(Currency s) {
        SharedPreferences preferences = getSharedPreferences("ROOT",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("currency",s.getShortName());
        editor.apply();
        finish();
    }
}
