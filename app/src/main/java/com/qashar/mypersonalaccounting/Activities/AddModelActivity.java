package com.qashar.mypersonalaccounting.Activities;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.qashar.mypersonalaccounting.Adapters.GroupAdapter;
import com.qashar.mypersonalaccounting.Else.AlertTime;
import com.qashar.mypersonalaccounting.Fragments.GroupFragment;
import com.qashar.mypersonalaccounting.Fragments.BlankFragment;
import com.qashar.mypersonalaccounting.Category.Category;
import com.qashar.mypersonalaccounting.Category.CategoryActivity;
import com.qashar.mypersonalaccounting.CountriesCurrency.Currency;
import com.qashar.mypersonalaccounting.CountriesCurrency.CurrencyFragment;
import com.qashar.mypersonalaccounting.Models.Group;
import com.qashar.mypersonalaccounting.InterFace.SelectCurrency;
import com.qashar.mypersonalaccounting.InterFace.SelectItem;
import com.qashar.mypersonalaccounting.InterFace.SelectString;
import com.qashar.mypersonalaccounting.InterFace.SelectWallet;
import com.qashar.mypersonalaccounting.R;
import com.qashar.mypersonalaccounting.RoomDB.MyViewModels;
import com.qashar.mypersonalaccounting.Models.Task;
import com.qashar.mypersonalaccounting.Models.Wallet;
import com.qashar.mypersonalaccounting.Fragments.WalletFragment;
import com.qashar.mypersonalaccounting.databinding.ActivityAddModelBinding;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class AddModelActivity extends AppCompatActivity implements SelectItem, SelectCurrency, SelectWallet, SelectString {
    private ActivityAddModelBinding binding;
    private CurrencyFragment currencyFragment;
    private WalletFragment walletFragment;
    private MyViewModels viewModels;
    private String a,b,c;
    private String emoji = "null";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:aaa");
    private GroupFragment fragment;private ActivityResultLauncher<String> arl;
    private AlertTime alertTime;
    private boolean addedToWallet = false;
    private Integer icon = R.drawable.icon_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddModelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        viewModels = new MyViewModels(getApplication());
        arl = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if (result){
                    List<Group> list = new ArrayList();
                    for (int i = 0; i <10 ; i++) {
                        list.add(new Group(getContacts(getApplicationContext()).get(i).name));
                    }
                    GroupAdapter adapter = new GroupAdapter(new SelectString() {
                        @Override
                        public void select(String s) {
//                            binding.contatcts.setVisibility(View.GONE);
//                            binding.main.setVisibility(View.VISIBLE);
//                            binding.txtContatct.setText(s);
                        }
                    }, getApplicationContext(), list);
//                    binding.contatctRV.setAdapter(adapter);
//                    binding.contatctRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    }
            }
        });

        binding.sad.setBackgroundColor(Color.GRAY);
        binding.happy.setBackgroundColor(Color.GRAY);
        binding.silnt.setBackgroundColor(Color.GRAY);
        binding.txtDate.setText(dateFormat.format(new Date()));
        binding.txtTime.setText(timeFormat.format(new Date()));

        binding.sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emoji = "sad";
                binding.cardSad.setBackgroundColor(Color.RED);
                binding.cardHappy.setBackgroundColor(Color.GRAY);
                binding.cardSilnt.setBackgroundColor(Color.GRAY);
                binding.sad.setBackgroundColor(Color.RED);
                binding.happy.setBackgroundColor(Color.GRAY);
                binding.silnt.setBackgroundColor(Color.GRAY);
                binding.textView5.setTextColor(Color.WHITE);
            }
        });
        binding.silnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emoji = "middle";
                binding.cardSad.setBackgroundColor(Color.GRAY);
                binding.cardHappy.setBackgroundColor(Color.GRAY);
                binding.cardSilnt.setBackgroundColor(Color.YELLOW);
                binding.sad.setBackgroundColor(Color.GRAY);
                binding.happy.setBackgroundColor(Color.GRAY);
                binding.silnt.setBackgroundColor(Color.YELLOW);
            }
        });
        binding.happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emoji = "basic";
                binding.cardSad.setBackgroundColor(Color.GRAY);
                binding.cardHappy.setBackgroundColor(Color.GREEN);
                binding.cardSilnt.setBackgroundColor(Color.GRAY);
                binding.sad.setBackgroundColor(Color.GRAY);
                binding.happy.setBackgroundColor(Color.GREEN);
                binding.silnt.setBackgroundColor(Color.GRAY);
            }
        });
        try {
            putWallet();

            binding.currency.setText(getIntent().getStringExtra(""));
            check();
        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.switch1.setVisibility(View.GONE);
        binding.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        binding.alert.setVisibility(View.VISIBLE);
                    }else {
                        binding.alert.setVisibility(View.GONE);
                    }
            }
        });
        SharedPreferences preferences = getSharedPreferences("ROOT",MODE_PRIVATE);
        binding.addModelName.setText(preferences.getString("name","راتب"));
        binding.addModelIcon.setImageResource(preferences.getInt("icon",R.drawable.icon_sadaqa));
    }

    private void putWallet() {
        a = getResources().getString(R.string.Nagdy);
        b = getResources().getString(R.string.CreditCard);
        c = getResources().getString(R.string.More);
        viewModels.getAllWallets().observe(this, new Observer<List<Wallet>>() {
            @Override
            public void onChanged(List<Wallet> wallets) {
                if (wallets.size()==0){
                    getSupportFragmentManager().beginTransaction().add(new BlankFragment(),"Fragmnt").commit();
                }
                for (int i = 0; i < wallets.size(); i++) {

                    Wallet wallet = wallets.get(i);
                    binding.wTXT.setText(wallets.get(i).getName());
                    binding.currency.setText(wallets.get(i).getCurrency());
                    if (wallet.getType().equals(a)){
                        binding.wImage.setImageResource(R.drawable.ic);
                    }else if (wallet.getType().equals(b)){
                        binding.wImage.setImageResource(R.drawable.cre);
                    }else {
                        binding.wImage.setImageResource(R.drawable.more);
                    }
                }
            }
        });
    }

    private void check() throws Exception {
        String name = getIntent().getStringExtra("name");
        String unit = getIntent().getStringExtra("unit");
        icon = getIntent().getIntExtra("icon",R.drawable.d1);
        String type = getIntent().getStringExtra("type");
        if (type.equals("off")){
            addedToWallet = false;
        }else {
            addedToWallet = true;
            binding.priority.setVisibility(View.GONE);
            emoji = "nulll";

        }
        if (! name.equals(null)){
            binding.addModelIcon.setImageResource(icon);
            binding.addModelName.setText(name);
            Toast.makeText(getApplicationContext(), "a", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(getApplicationContext(), "b", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences("ROOT",MODE_PRIVATE);
                name = preferences.getString("name","");
                type = preferences.getString("type","");
                unit = preferences.getString("unit","");
                icon = preferences.getInt("icon",R.drawable.cre);
                binding.addModelIcon.setImageResource(icon);
                binding.addModelName.setText(name);

        }


    }

    @SuppressLint("Range")
    public static List<ContactModel> getContacts(Context ctx) {
        List<ContactModel> list = new ArrayList<>();
        ContentResolver contentResolver = ctx.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor cursorInfo = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(ctx.getContentResolver(),
                            ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id)));

                    Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id));
                    Uri pURI = Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);


                    while (cursorInfo.moveToNext()) {
                        ContactModel info = new ContactModel();
                        info.id = id;
                        info.name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        info.mobileNumber = cursorInfo.getString(cursorInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        info.photoURI= pURI;
                        list.add(info);
                    }

                    cursorInfo.close();
                }
            }
            cursor.close();
        }
        return list;
    }

    public void onAdd(View view) {
      insert(getRandomNumber(1,10000000));
    }

    private void insert(int i) {
        Date added = new Date();
        if (binding.etPrice.getText().toString().isEmpty()){
            try {
                BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(AddModelActivity.this)
                        .setTitle("Confirm?")
                        .setMessage("Are you sure want to Add 0 value ?")
                        .setCancelable(false).setAnimation(R.raw.a1)
                        .setPositiveButton("Yes", R.drawable.ic_down, new BottomSheetMaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                Float price = Float.parseFloat(binding.etPrice.getText().toString());
                                String wallet = binding.wTXT.getText().toString();
                                String type = binding.addModelName.getText().toString();
                                String currency = binding.currency.getText().toString();
                                String note = binding.etNote.getText().toString();
                                String date = binding.txtDate.getText().toString();
                                String time = binding.txtTime.getText().toString();
                                String group = binding.txtGroup.getText().toString();
                                String contact = binding.txtContact.getText().toString();
                                boolean checked = false;
                                String checkedTime = "null";
                                if (binding.switch1.isChecked()) {
                                    checked = true;
                                    checkedTime = binding.txtRepate.getText().toString();
                                }

                                Task task = null;
                                try {
                                    task = new Task( icon, wallet, type,
                                            currency, note, date, time, group, contact,
                                            emoji, checkedTime, checked,toLong(sdf.parse(String.valueOf(added))),addedToWallet,i);
                                    viewModels.insertTask(task);
                                    finish();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        })
                        .setNegativeButton("Cancel", R.drawable.ic_down, new BottomSheetMaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }
                        })
                        .build();

                // Show Dialog
                mBottomSheetDialog.show();


            }catch (Exception e){
            }
        }else {
            try {
                Float price = Float.parseFloat(binding.etPrice.getText().toString());
                String wallet = binding.wTXT.getText().toString();
                String type = binding.addModelName.getText().toString();
                String currency = binding.currency.getText().toString();
                String note = binding.etNote.getText().toString();
                String date = binding.txtDate.getText().toString();
                String time = binding.txtTime.getText().toString();
                String group = binding.txtGroup.getText().toString();
                String contact = binding.txtContact.getText().toString();
                boolean checked = false;
                String checkedTime = "null";
            if (binding.switch1.isChecked()) {
                checked = true;
                checkedTime = binding.txtRepate.getText().toString();
            }

                    if (!emoji.equals("null")){
                        Task task = new Task((price), icon, wallet, type,
                                currency, note, date, time, group, contact,
                                emoji, checkedTime, checked,toLong(dateFormat.parse(binding.txtDate.getText().toString())),addedToWallet,i);
                        viewModels.insertTask(task);

                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(), ""+getResources().getString(R.string.Priority), Toast.LENGTH_SHORT).show();
                    }
//
            }
            catch (Exception e){
                Log.d("QQQQ",e.toString());
            }
        }

    }
    public static int getRandomNumber(int min, int max) {
        return (new Random()).nextInt((max - min) + 1) + min;
    }

    public void onSelectAlert(View view) {
         alertTime = new AlertTime(new SelectString() {
             @Override
             public void select(String s) {
                 binding.txtRepate.setText(s);
                 alertTime.dismiss();
             }
         });
        alertTime.show(getSupportFragmentManager(),"");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public static class ContactModel {
        public String id;
        public String name;
        public String mobileNumber;
        public Bitmap photo;
        public Uri photoURI;
    }

    public void onPickCategory(View view) {
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra("type","addModel");
        startActivity(intent);
    }

    @Override
    public void select(Category category) {
        Toast.makeText(getApplicationContext(), ""+category.getName(), Toast.LENGTH_SHORT).show();
    }

    public void onPickCurrency(View view) {
        currencyFragment = new CurrencyFragment(this);
        currencyFragment.show(getSupportFragmentManager(),"");

    }

    @Override
    public void select(Currency s) {
        binding.currency.setText(s.getShortName());
        currencyFragment.dismiss();
    }

    public void onSelectWallet(View view) {
        walletFragment = new WalletFragment(this);
        walletFragment.show(getSupportFragmentManager(),"");
    }

    @Override
    public void selectWallet(Wallet wallet) {
        binding.wTXT.setText(wallet.getName());
        binding.currency.setText(wallet.getCurrency());
        if (wallet.getType().equals(a)){
            binding.wImage.setImageResource(R.drawable.ic);
        }else if (wallet.getType().equals(b)){
            binding.wImage.setImageResource(R.drawable.cre);
        }else {
            binding.wImage.setImageResource(R.drawable.more);
        }
        walletFragment.dismiss();
    }

    public void pickDate(View view) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            binding.txtDate.setText(year+":"+month+":"+dayOfMonth);
            }
        };

        DatePickerDialog dialog = new DatePickerDialog(this,listener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }
    public static Long toLong(Date date){
        return date==null?null:date.getTime();
    }


    public void pickTime(View view) {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            binding.txtTime.setText(hourOfDay+":"+minute);
            }
        };

        TimePickerDialog dialog = new TimePickerDialog(AddModelActivity.this,listener,calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),false);
        dialog.show();
    }

    public void onContactSelect(View view) {
        arl.launch(Manifest.permission.READ_CONTACTS);
//        binding.contatcts.setVisibility(View.VISIBLE);
//        binding.main.setVisibility(View.GONE);
    }

    public void onGroupSelect(View view) {
         fragment = new GroupFragment(this);
        fragment.show(getSupportFragmentManager(),"");
    }

    @Override
    public void select(String s) {
        binding.txtGroup.setText(s);
        fragment.dismiss();
    }
}