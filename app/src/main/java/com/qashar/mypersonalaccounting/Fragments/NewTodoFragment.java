package com.qashar.mypersonalaccounting.Fragments;

import static android.content.Context.ALARM_SERVICE;

import static com.qashar.mypersonalaccounting.ui.HomeFragment.toLong;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;
import com.qashar.mypersonalaccounting.Else.MyReceiver;
import com.qashar.mypersonalaccounting.Models.Todo;
import com.qashar.mypersonalaccounting.Else.Settings;
import com.qashar.mypersonalaccounting.R;
import com.qashar.mypersonalaccounting.RoomDB.MyViewModels;
import com.qashar.mypersonalaccounting.databinding.ActivityNewTodoBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SuppressLint("ValidFragment")
public class NewTodoFragment extends DialogFragment {
    private ActivityNewTodoBinding binding;
    private SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
    private Date date;
    private boolean isDateSelected = false;
    private boolean isRemindMe = false;
    private MyViewModels models;
    private Calendar calendar;
    private Settings settings;
    private FragmentManager getSupportFragmentManager;

    public NewTodoFragment(FragmentManager getSupportFragmentManager) {
        this.getSupportFragmentManager = getSupportFragmentManager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityNewTodoBinding.inflate(getLayoutInflater());
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        models = new MyViewModels(getActivity().getApplication());
        settings = new Settings(getActivity());
        calendar = Calendar.getInstance();

        putRepeat();
        binding.saveTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (isRemindMe){
                        if (isDateSelected){
                            save();
                        }
                    }else {
                        saveTodoWithOutRemind();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        binding.saveTodo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Snackbar.make(v,"Cancled",Snackbar.LENGTH_SHORT).show();
                cancel();
                return false;
            }
        });

        binding.dateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateDialog(1);
            }
        });
        binding.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    isRemindMe = true;
                    binding.lnRepate.setVisibility(View.VISIBLE);
                }else {
                    isRemindMe = false;
                    binding.lnRepate.setVisibility(View.GONE);
                }
            }
        });

        return binding.getRoot();

    }

    private void putRepeat() {
        List<String> strings = new ArrayList<>();
        strings.add(getResources().getString(R.string.Daily));
        strings.add(getResources().getString(R.string.custom));
        binding.etRepeat.setText(getResources().getString(R.string.Daily));
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item,strings);
        binding.etRepeat.setAdapter(stringArrayAdapter);
        binding.etRepeat.setThreshold(1);
        binding.etRepeat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals(getResources().getString(R.string.custom))){
                    selectDate();
                }
            }
        });
    }
    private void selectDate() {
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT From DATE");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        materialDatePicker.show(getSupportFragmentManager, "MATERIAL_DATE_PICKER");
        materialDatePicker.addOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("LLL dd,yyyy");
                    Date s = format.parse(materialDatePicker.getHeaderText());
                    SimpleDateFormat month = new SimpleDateFormat("MM");
                    SimpleDateFormat day = new SimpleDateFormat("dd");
                    SimpleDateFormat year = new SimpleDateFormat("yyyy");
                    binding.etRepeat.setText(month.format(s)+":"+ day.format(s)+":"+year.format(s));
                    NewTodoFragment.this.calendar.set(Calendar.YEAR, Integer.parseInt(year.format(s)));
                    NewTodoFragment.this.calendar.set(Calendar.MONTH, Integer.parseInt(month.format(s)));
                    NewTodoFragment.this.calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day.format(s)));

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

    }



    private void cancel(){
        Intent intent = new Intent(getActivity(), MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),
                10, intent, 0);
        AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        am.cancel(pendingIntent);
// Cancel the `PendingIntent` after you've canceled the alarm
        pendingIntent.cancel();
    }
    private void saveTodoWithOutRemind() {

        String title = binding.etTitle.getText().toString();
        String note = binding.etNote.getText().toString();
        String repeat = binding.etRepeat.getText().toString();
        Todo todo = new Todo(title,note,null,null,settings.getLastID());
        settings.setLastID(settings.getLastID()+1);
        if (!title.isEmpty()){
            models.insertTodo(todo);
        }else {
            binding.etTitle.setError("Empty Input !");
        }
    }
    private void onCreateDialog(int id) {

        Calendar calendar = Calendar.getInstance();
        Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
        Integer minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog dialog = new TimePickerDialog(getActivity(),TimeMap,hour,minute,true);
        dialog.show();
    }
    public static Long toLong(Date date){
        return date==null?null:date.getTime();
    }
    protected TimePickerDialog.OnTimeSetListener TimeMap =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker TimeP, int hourOfDay, int minute) {
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);
                    try {
                        isDateSelected = true;
                        date = sdf.parse(hourOfDay+":"+minute);
                        binding.dateTV.setText(hourOfDay+":"+minute);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }



                }
            };
    private void setAlarm(Calendar calendar,String title,String mess,Integer request){

        if (calendar.getTime().compareTo(new Date()) < 0) calendar.add(Calendar.HOUR_OF_DAY, 0);
        Intent intent = new Intent(getActivity(), MyReceiver.class);
        intent.putExtra("title",title);
        intent.putExtra("message",mess);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), request, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
        }

    }
    private void save() throws Exception{
        String title = binding.etTitle.getText().toString();
        String note = binding.etNote.getText().toString();
        String repeat = binding.etRepeat.getText().toString();
        Todo todo = new Todo(title,note,date,repeat,settings.getLastID());

        if (!title.isEmpty()){
            models.insertTodo(todo);
        }else {
            binding.etTitle.setError("Empty Input !");
        }
        if (isRemindMe){
            setAlarm(calendar,title,note,settings.getLastID());
        }
        settings.setLastID(settings.getLastID()+1);
        getDialog().dismiss();

    }



}