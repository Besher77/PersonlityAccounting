package com.qashar.mypersonalaccounting.Adapters;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qashar.mypersonalaccounting.Models.Todo;
import com.qashar.mypersonalaccounting.Fragments.EditTodoFragment;
import com.qashar.mypersonalaccounting.InterFace.SelectWallet;
import com.qashar.mypersonalaccounting.R;
import com.qashar.mypersonalaccounting.RoomDB.MyViewModels;
import com.qashar.mypersonalaccounting.Else.MyReceiver;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> {
    final Context context;
    final List<Todo> todos;
    private SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
    private SelectWallet selectWallet;
    private MyViewModels viewModels;
    private Activity activity;
    private FragmentManager childFragmentManager;
    public static Long toLong(Date date){
        return date==null?null:date.getTime();
    }


    public TodoAdapter(FragmentManager childFragmentManager, Activity activity, MyViewModels viewModels, Context context, List<Todo> todos) {
        this.context = context;
        this.activity=activity;
        this.viewModels = viewModels;
        this.todos = todos;
        this.childFragmentManager =childFragmentManager;

    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(todos.get(position).getTitle());
        Date date = new Date();
        String sad = sdf.format(date);
        if (todos.get(position).getDate()!=null){
            holder.remind.setText(todos.get(position).getRepeat()+"  |  "+sdf.format(todos.get(position).getDate()));

            try {
            Date oldDate = sdf.parse("11:39");
//                date = sdf.parse(hourOfDay+":"+minute);
            Date newDate = todos.get(position).getDate();
              Integer selectHour =Integer.parseInt(new SimpleDateFormat("hh").format(newDate));
              Integer selectMinute =Integer.parseInt(new SimpleDateFormat("mm").format(newDate));
              Integer newHour =Integer.parseInt(new SimpleDateFormat("hh").format(newDate));
              Integer newMinute =Integer.parseInt(new SimpleDateFormat("mm").format(new Date()));
                Log.i("old",""+selectHour+":"+selectMinute+"::"+newHour+":"+newMinute);
                if (selectHour>=newHour){
                    if (selectMinute>=newMinute){
                        holder.remind.setTextColor(Color.GREEN);
                    }else {
                        holder.remind.setTextColor(Color.RED);
                    }
                }

        } catch (ParseException e) {
            e.printStackTrace();
        }}
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTodoFragment fragment = new EditTodoFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id",todos.get(position).getId());
                fragment.setArguments(bundle);
                childFragmentManager.beginTransaction().add(fragment,"Fragmnt").commit();

            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                delete(todos.get(position));
                return false;
            }
        });
    }

    private void delete(Todo todo) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(activity)
                .setTitle("Delete?")
                .setMessage("Are you sure want to delete this file?")
                .setCancelable(false).setAnimation(R.raw.a1)
                .setPositiveButton("Delete", R.drawable.ic_down, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Toast.makeText(activity.getApplicationContext(), "Deleted!", Toast.LENGTH_SHORT).show();
                        if (todo.getDate()!=null){
                            cancelRemind(todo.getTodoID());
                        }
                        viewModels.deleteTodo(todo);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", R.drawable.ic_down, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Toast.makeText(activity.getApplicationContext(), "Cancelled!", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                })
                .build();

        // Show Dialog
        mBottomSheetDialog.show();
    }

    private void cancelRemind(Integer todoID) {
            Intent intent = new Intent(context, MyReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    todoID, intent, 0);
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            am.cancel(pendingIntent);
// Cancel the `PendingIntent` after you've canceled the alarm
            pendingIntent.cancel();


    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title,remind;
//        private ImageView visible,icMore;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            remind = itemView.findViewById(R.id.tvRemind);

        }


    }

}