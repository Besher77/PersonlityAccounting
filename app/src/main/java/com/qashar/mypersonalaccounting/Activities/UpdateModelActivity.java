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

import com.google.android.material.snackbar.Snackbar;
import com.qashar.mypersonalaccounting.Else.AlertTime;
import com.qashar.mypersonalaccounting.Models.Group;
import com.qashar.mypersonalaccounting.Adapters.GroupAdapter;
import com.qashar.mypersonalaccounting.Fragments.GroupFragment;
import com.qashar.mypersonalaccounting.Models.Task;
import com.qashar.mypersonalaccounting.Category.Category;
import com.qashar.mypersonalaccounting.Category.CategoryActivity;
import com.qashar.mypersonalaccounting.CountriesCurrency.Currency;
import com.qashar.mypersonalaccounting.CountriesCurrency.CurrencyFragment;
import com.qashar.mypersonalaccounting.InterFace.SelectCurrency;
import com.qashar.mypersonalaccounting.InterFace.SelectItem;
import com.qashar.mypersonalaccounting.InterFace.SelectString;
import com.qashar.mypersonalaccounting.InterFace.SelectWallet;
import com.qashar.mypersonalaccounting.R;
import com.qashar.mypersonalaccounting.RoomDB.MyViewModels;
import com.qashar.mypersonalaccounting.Models.Wallet;
import com.qashar.mypersonalaccounting.Fragments.WalletFragment;
import com.qashar.mypersonalaccounting.databinding.ActivityUpdateModelBinding;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UpdateModelActivity extends AppCompatActivity implements SelectItem, SelectCurrency, SelectWallet, SelectString{
    private ActivityUpdateModelBinding binding;
    private MyViewModels viewModels;
    private CurrencyFragment currencyFragment;
    private WalletFragment walletFragment;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
    private String a,b,c;
    private String emoji = "null";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:aaa");
    private GroupFragment fragment;
    private ActivityResultLauncher<String> arl;
    private AlertTime alertTime;
    private boolean addedToWallet = false;
    private Integer icon = R.drawable.icon_1;
    private long opID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateModelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModels = new MyViewModels(getApplication());
        try {
            putDate(getIntent().getIntExtra("id",0));
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("QQQQ",e.toString());
        }
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
        viewModels = new MyViewModels(getApplication());
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
    }
    public static Long toLong(Date date){
        return date==null?null:date.getTime();
    }

    private void putWallet(String wallet) {
        a = getResources().getString(R.string.Nagdy);
        b = getResources().getString(R.string.CreditCard);
        c = getResources().getString(R.string.More);
        viewModels.getAllWalletByName(wallet).observe(this, new Observer<List<Wallet>>() {
            @Override
            public void onChanged(List<Wallet> wallets) {
                    Wallet wallet = wallets.get(0);
                    binding.wTXT.setText(wallets.get(0).getName());
                    if (wallet.getType().equals(a)){
                        binding.wImage.setImageResource(R.drawable.ic);
                    }else if (wallet.getType().equals(b)){
                        binding.wImage.setImageResource(R.drawable.cre);
                    }else {
                        binding.wImage.setImageResource(R.drawable.more);
                    }
                }

        });
    }

    @SuppressLint("Range")
    public static List<AddModelActivity.ContactModel> getContacts(Context ctx) {
        List<AddModelActivity.ContactModel> list = new ArrayList<>();
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
                        AddModelActivity.ContactModel info = new AddModelActivity.ContactModel();
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

    public void onUpdate(View view) {
        try {
            Integer id = getIntent().getIntExtra("id",0);
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
            if (price <= 0) {
                price = 0f;
                Snackbar.make(view, "You can not add", Snackbar.LENGTH_SHORT).show();
            } else {
//                Task task = new Task(price,R.drawable.i1,wallet,type,currency,note,date,time,group,contact,emoji,checkedTime,checked);
                if (!emoji.equals("null")){
                    Task task = new Task(id,(price + 0), icon, wallet, type,
                            currency, note, date, time, group, contact,
                            emoji, checkedTime, checked,toLong(sdf.parse(String.valueOf(new Date()))),addedToWallet,opID);
                    viewModels.updateTask(task);
//                    updateWallet(wallet,addedToWallet,price);
                    finish();
                }else {
                    Snackbar.make(view, "You have to select priority", Snackbar.LENGTH_SHORT).show();
                }
//


            }
        }
        catch (Exception e){
            Log.d("QQQQ",e.toString());
        }
    }
/*
    private void updateWallet(String wallet, boolean type, Float price) {
        Toast.makeText(getApplicationContext(), "N"+wallet, Toast.LENGTH_SHORT).show();
        viewModels.getAllWalletByOpId(getIntent().getIntExtra("id",0))
                .observe(this, new Observer<List<WalletOperation>>() {
            @Override
            public void onChanged(List<WalletOperation> walletOperations) {
                WalletOperation operation = walletOperations.get(0);

                if (type){
                    try {
                        walletOperation = new WalletOperation(operation.getId(),wallet,price,"on",emoji,getIntent().getIntExtra("id",0),toLong(dateFormat.parse(binding.txtDate.getText().toString())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        walletOperation = new WalletOperation(operation.getId(),wallet,price,"off",emoji,getIntent().getIntExtra("id",0),toLong(dateFormat.parse(binding.txtDate.getText().toString())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                viewModels.updateWalletOp(walletOperation);
            }
        });
    }
*/
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
    public static class ContactModel {
        public String id;
        public String name;
        public String mobileNumber;
        public Bitmap photo;
        public Uri photoURI;
    }
    public void onPickCategory(View view) {
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra("type","updateModel");
        intent.putExtra("id",getIntent().getIntExtra("id",0));
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

    public void pickTime(View view) {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                binding.txtTime.setText(hourOfDay+":"+minute);
            }
        };

        TimePickerDialog dialog = new TimePickerDialog(UpdateModelActivity.this,listener,calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),false);
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


    private void putDate(int id)throws Exception {
        viewModels.getAllTasksByID(id).observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                Task task = tasks.get(0);
                opID = task.getWalletID();
                icon = task.getIcon();
                putWallet(task.getWallet());
            try {

                getIntent().getStringExtra("name").equals("null");
               icon = getIntent().getIntExtra("icon",R.drawable.cre);
                    binding.etPrice.setText("" + task.getPrice());
                    binding.txtGroup.setText(task.getGroup());
                    binding.txtTime.setText(task.getTime());
                    binding.txtDate.setText(task.getDate());
                    binding.txtRepate.setText(task.getChecked()+"");
                Toast.makeText(getApplicationContext(), ""+task.getChecked(), Toast.LENGTH_SHORT).show();
                    binding.etNote.setText(task.getNote());
                    binding.currency.setText(task.getCurrency());
                    binding.addModelName.setText(getIntent().getStringExtra("name"));
                    binding.addModelIcon.setImageResource(getIntent().getIntExtra("icon",R.drawable.d1));

                    if (task.isRepeated()) {
                        binding.switch1.setChecked(true);
                        binding.alert.setVisibility(View.VISIBLE);
                        binding.txtRepate.setText(task.getChecked());
                    } else {
                        binding.switch1.setChecked(false);
//                        binding.alert.setVisibility(View.GONE);
                    }
            }catch (Exception e){
                binding.etPrice.setText("" + task.getPrice());
                binding.txtGroup.setText(task.getGroup());
                binding.txtTime.setText(task.getTime());
                binding.txtDate.setText(task.getDate());
                binding.etNote.setText(task.getNote());
                binding.currency.setText(task.getCurrency());
                binding.addModelName.setText(task.getType());
                binding.addModelIcon.setImageResource(task.getIcon());
                if (task.isRepeated()) {
                    binding.switch1.setChecked(true);
                    binding.alert.setVisibility(View.VISIBLE);
                    binding.txtRepate.setText(task.getChecked());
                } else binding.switch1.setChecked(false);
                binding.alert.setVisibility(View.GONE);
            }
            if (task.getEmoji().equals("basic")){
                emoji = "basic";
                binding.cardSad.setBackgroundColor(Color.GRAY);
                binding.cardHappy.setBackgroundColor(Color.GREEN);
                binding.cardSilnt.setBackgroundColor(Color.GRAY);
                binding.sad.setBackgroundColor(Color.GRAY);
                binding.happy.setBackgroundColor(Color.GREEN);
                binding.silnt.setBackgroundColor(Color.GRAY);
            }else if (task.getEmoji().equals("middle")){
                emoji = "middle";
                binding.cardSad.setBackgroundColor(Color.GRAY);
                binding.cardHappy.setBackgroundColor(Color.GRAY);
                binding.cardSilnt.setBackgroundColor(Color.YELLOW);
                binding.sad.setBackgroundColor(Color.GRAY);
                binding.happy.setBackgroundColor(Color.GRAY);
                binding.silnt.setBackgroundColor(Color.YELLOW);
            }else if (task.getEmoji().equals("sad")){
                emoji = "sad";
                binding.cardSad.setBackgroundColor(Color.RED);
                binding.cardHappy.setBackgroundColor(Color.GRAY);
                binding.cardSilnt.setBackgroundColor(Color.GRAY);
                binding.sad.setBackgroundColor(Color.RED);
                binding.happy.setBackgroundColor(Color.GRAY);
                binding.silnt.setBackgroundColor(Color.GRAY);
                binding.textView5.setTextColor(Color.WHITE);
            }

            }
        });
    }


}