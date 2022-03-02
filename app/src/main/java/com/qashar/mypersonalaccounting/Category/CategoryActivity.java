package com.qashar.mypersonalaccounting.Category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.qashar.mypersonalaccounting.Activities.AddModelActivity;
import com.qashar.mypersonalaccounting.InterFace.SelectItem;
import com.qashar.mypersonalaccounting.R;
import com.qashar.mypersonalaccounting.RoomDB.MyViewModels;
import com.qashar.mypersonalaccounting.Activities.UpdateModelActivity;
import com.qashar.mypersonalaccounting.databinding.ActivityCategoryBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity implements SelectItem {
    private ActivityCategoryBinding binding;
    private ArrayList<Category> categories = new ArrayList<>();
    private ArrayList<Category> incomes = new ArrayList<>();
    private ArrayList<Category> outs = new ArrayList<>();
    private ArrayList<Category> debts = new ArrayList<>();
    private ArrayList<Category> search = new ArrayList<>();
    private String type;
    private SelectItem selectItem;
    private MyViewModels viewModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModels = new MyViewModels(getApplication());

        try {
             type = getIntent().getStringExtra("addModel");
        }catch (Exception e){}

        categories = new ArrayList<>();
        incomes = new ArrayList<>();
        outs = new ArrayList<>();
        debts = new ArrayList<>();
        search = new ArrayList<>();
        try {
            viewModels.getAllCategories().observe(this, new Observer<List<Category>>() {
                @Override
                public void onChanged(List<Category> categories) {
                    Toast.makeText(getApplicationContext(), ""+categories.size(), Toast.LENGTH_SHORT).show();
                    categories.add(new Category("الراتب","on","Income",R.drawable.i1,true,true));
                    categories.add(new Category("المكافأت","on","Income",R.drawable.i2,true,true));
                    categories.add(new Category("الهداياء","on","Income",R.drawable.i3,true,true));
                    categories.add(new Category("المبيعات","on","Income",R.drawable.i4,true,true));
                    categories.add(new Category("الاضافي","on","Income",R.drawable.i5,true,true));
                    categories.add(new Category("أخرى","on","Income",R.drawable.i6,true,true));


                    categories.add(new Category("تحويل الرصيد","off",getResources().getString(R.string.Outgoings),R.drawable.more,true,true));

                    categories.add(new Category("الغذاء","off",getResources().getString(R.string.Outgoings),R.drawable.ic_category_foodndrink,true,true));
                    categories.add(new Category("المقاهي","off",getResources().getString(R.string.Outgoings),R.drawable.icon_cafe,true,false));
                    categories.add(new Category("المطاعم","off",getResources().getString(R.string.Outgoings),R.drawable.icon_restaurants,true,false));

                    categories.add(new Category("المواصلات","off",getResources().getString(R.string.Outgoings),R.drawable.ic_category_transport,true,true));
                    categories.add(new Category("البنزين","off",getResources().getString(R.string.Outgoings),R.drawable.icon_petrol,true,false));
                    categories.add(new Category("الصيانة","off",getResources().getString(R.string.Outgoings),R.drawable.icon_mainenance,true,false));
                    categories.add(new Category("الجراج","off",getResources().getString(R.string.Outgoings),R.drawable.icon_parking,true,false));
                    categories.add(new Category("الأجرة","off",getResources().getString(R.string.Outgoings),R.drawable.icon_taxi,true,false));

                    categories.add(new Category("فواتير","off",getResources().getString(R.string.Outgoings),R.drawable.icon_135,true,true));
                    categories.add(new Category("الكهرباء","off",getResources().getString(R.string.Outgoings),R.drawable.icon_electricity,true,false));
                    categories.add(new Category("الغاز","off",getResources().getString(R.string.Outgoings),R.drawable.icon_gas,true,false));
                    categories.add(new Category("الأنترنت","off",getResources().getString(R.string.Outgoings),R.drawable.icon_internet,true,false));
                    categories.add(new Category("الأتصالات","off",getResources().getString(R.string.Outgoings),R.drawable.icon_phone,true,false));
                    categories.add(new Category("الأيجار","off",getResources().getString(R.string.Outgoings),R.drawable.icon_rentals,true,false));
                    categories.add(new Category("التلفاز","off",getResources().getString(R.string.Outgoings),R.drawable.icon_tv,true,false));
                    categories.add(new Category("المياة","off",getResources().getString(R.string.Outgoings),R.drawable.icon_water,true,false));

                    categories.add(new Category("الأسرة","off",getResources().getString(R.string.Outgoings),R.drawable.ic_category_family,true,true));
                    categories.add(new Category("الأطفال","off",getResources().getString(R.string.Outgoings),R.drawable.icon_babies,true,false));
                    categories.add(new Category("الصيانة المنزلية","off",getResources().getString(R.string.Outgoings),R.drawable.icon_improv,true,false));
                    categories.add(new Category("الخدمات","off",getResources().getString(R.string.Outgoings),R.drawable.icon_services,true,false));
                    categories.add(new Category("الحيوانت الاليفة","off",getResources().getString(R.string.Outgoings),R.drawable.icon_pets,true,false));

                    categories.add(new Category("التعليم","off",getResources().getString(R.string.Outgoings),R.drawable.ic_category_education,true,true));
                    categories.add(new Category("كتب درأسية","off",getResources().getString(R.string.Outgoings),R.drawable.icon_books,true,false));
                    categories.add(new Category("الدورات التدريبية","off",getResources().getString(R.string.Outgoings),R.drawable.icon_courses,true,false));

                    categories.add(new Category("استثمار","off",getResources().getString(R.string.Outgoings),R.drawable.icon_buisness,true,true));

                    categories.add(new Category("الترفية","off",getResources().getString(R.string.Outgoings),R.drawable.ic_category_entertainment,true,true));
                    categories.add(new Category("العاب","off",getResources().getString(R.string.Outgoings),R.drawable.icon_games,true,false));
                    categories.add(new Category("أفلام صوتية","off",getResources().getString(R.string.Outgoings),R.drawable.icon_movies,true,false));

                    categories.add(new Category("الرسوم والأشتراكات","off",getResources().getString(R.string.Outgoings),R.drawable.icon_fees,true,true));

                    categories.add(new Category("التبرعات والهداياء","off",getResources().getString(R.string.Outgoings),R.drawable.ic_category_donations,true,true));
                    categories.add(new Category("الصدقة","off",getResources().getString(R.string.Outgoings),R.drawable.icon_sadaqa,true,false));
                    categories.add(new Category("الالزكاة","off",getResources().getString(R.string.Outgoings),R.drawable.icon_zakah,true,false));
                    categories.add(new Category("الهداياء","off",getResources().getString(R.string.Outgoings),R.drawable.icon_gift,true,false));

                    categories.add(new Category("الصحة واللياقة البدنية","off",getResources().getString(R.string.Outgoings),R.drawable.ic_category_medical,true,true));
                    categories.add(new Category("الاطباء","off",getResources().getString(R.string.Outgoings),R.drawable.ic_category_doctor,true,false));
                    categories.add(new Category("الأدوية","off",getResources().getString(R.string.Outgoings),R.drawable.ic_category_pharmacy,true,false));
                    categories.add(new Category("العناية الشخصية","off",getResources().getString(R.string.Outgoings),R.drawable.icon_personal_care,true,false));
                    categories.add(new Category("الأنشطة الرياضية","off",getResources().getString(R.string.Outgoings),R.drawable.icon_sports,true,false));

                    categories.add(new Category("التامينات","off",getResources().getString(R.string.Outgoings),R.drawable.icon_insurances,true,true));

                    categories.add(new Category("التسوق","off",getResources().getString(R.string.Outgoings),R.drawable.ic_category_shopping,true,true));
                    categories.add(new Category("اكسسوارات","off",getResources().getString(R.string.Outgoings),R.drawable.icon_accessories,true,true));
                    categories.add(new Category("ملابس","off",getResources().getString(R.string.Outgoings),R.drawable.icon_clothes,true,false));
                    categories.add(new Category("الكترونيات","off",getResources().getString(R.string.Outgoings),R.drawable.icon_electronics,true,false));
                    categories.add(new Category("احذية","off",getResources().getString(R.string.Outgoings),R.drawable.icon_footwear,true,false));

                    categories.add(new Category("السفر","off",getResources().getString(R.string.Outgoings),R.drawable.ic_category_travel,true,true));
                    categories.add(new Category("السحب النقدي","off",getResources().getString(R.string.Outgoings),R.drawable.icon_withdrawal,true,true));
                    categories.add(new Category("اخرى","off",getResources().getString(R.string.Outgoings),R.drawable.ic_category_other_expense,true,true));

                    categories.add(new Category("دفع ديون واقساط","off",getResources().getString(R.string.Debts),R.drawable.d1,true,true));
                    categories.add(new Category("استلام ديون واقساط","on",getResources().getString(R.string.Debts),R.drawable.d2,true,true));

                    filter(categories);
                }
            });
         }catch (Exception e){
            Log.i("QQQQ",e.toString());
        }
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                search.clear();
                for (int i = 0; i < categories.size(); i++) {
                    if (categories.get(i).getName().contains(s.toString())){
                        search.add(categories.get(i));
                    }
                }
                filter(search);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        binding.floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddCategoryActivity.class));
            }
        });
    }

    private void filter(List<Category> list) {
        incomes.clear();
        outs.clear();
        debts.clear();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUnit().equals("Income")){
                incomes.add(list.get(i));
            }else if (list.get(i).getUnit().equals(getResources().getString(R.string.Outgoings))){
                outs.add(list.get(i));
            }
            else if (list.get(i).getUnit().equals(getResources().getString(R.string.Debts))){
                debts.add(list.get(i));
            }
        }
        binding.rvIncome.setAdapter(new CategoryAdapter(this,CategoryActivity.this,incomes));
        binding.rvIncome.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));
        binding.rvOut.setAdapter(new CategoryAdapter(this,CategoryActivity.this,outs));
        binding.rvOut.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));
        binding.rvDebts.setAdapter(new CategoryAdapter(this,CategoryActivity.this,debts));
        binding.rvDebts.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));

    }

    @Override
    public void select(Category category) {
        SharedPreferences preferences = getSharedPreferences("ROOT",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name",category.getName());
        editor.putInt("icon",category.getImage());
        editor.putString("type",category.getType());
        editor.putString("unit",category.getUnit());
        editor.apply();
        Intent intent = new Intent(CategoryActivity.this, AddModelActivity.class);
        intent.putExtra("icon",category.getImage());
        intent.putExtra("name",category.getName());
        intent.putExtra("unit",category.getUnit());
        intent.putExtra("type",category.getType());
        Intent intent2 = new Intent(CategoryActivity.this, UpdateModelActivity.class);
        intent2.putExtra("icon",category.getImage());
        intent2.putExtra("name",category.getName());
        intent2.putExtra("unit",category.getUnit());
        intent2.putExtra("type",category.getType());
        if (getIntent().getStringExtra("type").equals("addModel")){
            finish();
            startActivity(intent);
        }else if (getIntent().getStringExtra("type").equals("updateModel")){
            intent2.putExtra("id",getIntent().getIntExtra("id",0));
            startActivity(intent2);
        }

    }

}