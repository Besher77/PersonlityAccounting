package com.qashar.mypersonalaccounting.Fragments;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.google.android.material.snackbar.Snackbar;
import com.qashar.mypersonalaccounting.RoomDB.MyViewModels;
import com.qashar.mypersonalaccounting.Else.MyReceiver;
import com.qashar.mypersonalaccounting.Models.Todo;
import com.qashar.mypersonalaccounting.databinding.ActivityNewTodoBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EditTodoFragment extends DialogFragment {
    private ActivityNewTodoBinding binding;
    private SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
    private Date date;
    private boolean isDateSelected = false;
    private boolean isRemindMe = false;
    private MyViewModels models;
    private Calendar calendar;
    private Integer lastID;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityNewTodoBinding.inflate(getLayoutInflater());
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        models = new MyViewModels(getActivity().getApplication());
        putData();
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

    private void putData() {
        Integer id = getArguments().getInt("id");
        models.getTodoByID(id).observe((LifecycleOwner) getActivity(), new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                Todo todo = todos.get(0);
                lastID = todo.getTodoID();
                binding.etTitle.setText(todo.getTitle());
                binding.etNote.setText(todo.getNote());
                if (todo.getDate()!=null){
                    binding.switch1.setChecked(true);
                    binding.lnRepate.setVisibility(View.VISIBLE);
                    binding.etRepeat.setText(todo.getRepeat());
                    binding.dateTV.setText(sdf.format(todo.getDate()));
                    isRemindMe =true;
                    isDateSelected = true;
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
        Todo todo = new Todo(getArguments().getInt("id"),title,note,null,null,lastID);
        if (!title.isEmpty()){
            models.updateTodo(todo);
        }else {
            binding.etTitle.setError("Empty Input !");
        }
        getDialog().dismiss();
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
                    Calendar calendar = Calendar.getInstance();
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
                    EditTodoFragment.this.calendar = calendar;


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
        Todo todo = new Todo(getArguments().getInt("id"),title,note,date,repeat,lastID);

        if (!title.isEmpty()){
            models.updateTodo(todo);
        }else {
            binding.etTitle.setError("Empty Input !");
        }
        if (isRemindMe){
            setAlarm(calendar,title,note,lastID);
        }
        getDialog().dismiss();

    }



}