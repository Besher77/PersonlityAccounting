package com.qashar.mypersonalaccounting.Category;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.android.material.snackbar.Snackbar;
import com.qashar.mypersonalaccounting.InterFace.PickImage;
import com.qashar.mypersonalaccounting.R;
import com.qashar.mypersonalaccounting.RoomDB.MyViewModels;
import com.qashar.mypersonalaccounting.databinding.ActivityAddCategoryBinding;

import java.util.ArrayList;

public class AddCategoryActivity extends AppCompatActivity implements PickImage {
    private ActivityAddCategoryBinding binding;
    private ItemImages itemImages;
    private MyViewModels viewModels;
    private  Category category;
    private Integer icon = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModels = new MyViewModels(getApplication());
        ArrayList<String> strings = new ArrayList<>();
        strings.add(getResources().getString(R.string.Income));
        strings.add(getResources().getString(R.string.Outgoings));
        strings.add(getResources().getString(R.string.Debts));
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item,strings);
        binding.etUnit.setAdapter(stringArrayAdapter);
        binding.etUnit.setThreshold(1);
        ArrayList<String> strings1 = new ArrayList<>();
        strings1.add("Positive");
        strings1.add("Negative");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item,strings1);
        binding.etType.setAdapter(adapter);
        binding.etType.setThreshold(1);
        binding.imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemImages = new ItemImages(AddCategoryActivity.this::onPick);
                itemImages.show(getSupportFragmentManager(),"");
            }
        });
    }
    public void onAddCategory(View view){
        String income = getResources().getString(R.string.Income);
        String outgoings = getResources().getString(R.string.Outgoings);
        String debts = getResources().getString(R.string.Debts);
        String type = binding.etType.getText().toString();
        String unit = binding.etUnit.getText().toString();
        String name = binding.etName.getText().toString();
        if (icon!=0) {
            if (type.equals("Positive")) {
                category = new Category(name, "on", unit,icon, true, true);
            } else {
                category = new Category(name, "off", unit, icon, true, true);
            }
            if (!type.isEmpty() || name.isEmpty()) {
                viewModels.insertCategory(category);
                finish();
            }else {
                Snackbar.make(view,"You have to ",Snackbar.LENGTH_SHORT).show();
            }
        }else {
            Snackbar.make(view,"You have to select icon",Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPick(Integer res) {
        binding.imageView3.setImageResource(res);
        itemImages.dismiss();
        icon = res;
    }
}