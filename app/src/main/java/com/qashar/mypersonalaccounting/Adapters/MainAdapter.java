package com.qashar.mypersonalaccounting.Adapters;


import static com.qashar.mypersonalaccounting.Activities.AddModelActivity.toLong;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.qashar.mypersonalaccounting.Activities.ShowDetailsActivity;
import com.qashar.mypersonalaccounting.InterFace.SelectItem;
import com.qashar.mypersonalaccounting.R;
import com.qashar.mypersonalaccounting.RoomDB.MyViewModels;
import com.qashar.mypersonalaccounting.Models.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import info.hoang8f.widget.FButton;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    final Context context;
    final List<Task> models;
    private SelectItem selectItem;
    private MyViewModels viewModels;
    private Float on = 0f , off = 0f;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
    private String selectedWallet = "*";
    public MainAdapter(MyViewModels viewModels, Context context, List<Task> models, String selectedWallet) {
        this.context = context;
        this.viewModels = viewModels;
        this.models = models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        holder.day.setText(""+new SimpleDateFormat("dd").format(models.get(position).getAddedAt()));//Day Integer
        holder.day2.setText(""+new SimpleDateFormat("E").format(models.get(position).getAddedAt()));//Day String
        holder.date.setText(""+new SimpleDateFormat("yyyy,LLL").format(models.get(position).getAddedAt()));//Years with day
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowDetailsActivity.class);
                intent.putExtra("date",models.get(position).getDate());
                intent.putExtra("wallet",selectedWallet);
                intent.putExtra("chart","no");
                context.startActivity(intent);
            }
        });
        holder.icDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.rv.setVisibility(View.VISIBLE);
            }
        });
        holder.day.setBackgroundResource(R.drawable.btn3);
        holder.currency.setText(models.get(position).getCurrency());
        if (selectedWallet.equals("*")) {
            viewModels.getAllTasksByDate(models.get(position).getDate())
                    .observe((LifecycleOwner) context, new Observer<List<Task>>() {
                        @Override
                        public void onChanged(List<Task> tasks) {
                            Float n_price = 0f;
                            Float p_price = 0f;
                            for (int i = 0; i < tasks.size(); i++) {
                                if (tasks.get(i).isAddedAtWallet()) {
                                    p_price = p_price + tasks.get(i).getPrice();
                                } else {
                                    n_price = n_price + tasks.get(i).getPrice();
                                }
                            }
                            holder.P_price.setText("" + p_price);
                            holder.N_price.setText("" + n_price);
                            holder.P_price.setTextColor(Color.GREEN);
                            holder.N_price.setTextColor(Color.RED);
                        }
                    });
        }else {
            viewModels.getAllTasksByDate(models.get(position).getDate())
                    .observe((LifecycleOwner) context, new Observer<List<Task>>() {
                        @Override
                        public void onChanged(List<Task> tasks) {
                        List<Task> myTasks = new ArrayList<>();
                            for (int i = 0; i < tasks.size(); i++) {
                                if (tasks.get(i).getWallet().equals(selectedWallet)){
                                    myTasks.add(tasks.get(i));
                                }
                            }
                            Float n_price = 0f;
                            Float p_price = 0f;
                            for (int i = 0; i < myTasks.size(); i++) {
                                if (myTasks.get(i).isAddedAtWallet()) {
                                    p_price = p_price + myTasks.get(i).getPrice();
                                } else {
                                    n_price = n_price + myTasks.get(i).getPrice();
                                }
                            }
                            holder.P_price.setText("" + p_price);
                            holder.N_price.setText("" + n_price);
                            holder.P_price.setTextColor(Color.GREEN);
                            holder.N_price.setTextColor(Color.RED);
                        }
                    });
        }

    }

    private Long getDate() {
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat day = new SimpleDateFormat("dd");

        try {
            return toLong(sdf.parse(year.format(new Date().toString())+"-"+day.format(new Date().toString())+"-"+month.format(new Date().toString())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView day2,date,currency,P_price,N_price,price;
        private FButton day;
        private RecyclerView rv;
        private ImageView icDown;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rv = itemView.findViewById(R.id.rvRV);
            day = itemView.findViewById(R.id.mainItemTxtDayNumber);
            day2 = itemView.findViewById(R.id.mainItemDay);
            date = itemView.findViewById(R.id.mainItemTxtDate);
            currency = itemView.findViewById(R.id.mainItemTxtCurrency);
            N_price = itemView.findViewById(R.id.mainItemTxtNPrice);
            P_price = itemView.findViewById(R.id.mainItemTxtPrice);
            icDown = itemView.findViewById(R.id.mainItemIcDown);


        }


    }

}