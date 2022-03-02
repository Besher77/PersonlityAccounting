package com.qashar.mypersonalaccounting.Adapters;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qashar.mypersonalaccounting.Models.Task;
import com.qashar.mypersonalaccounting.InterFace.SelectItem;
import com.qashar.mypersonalaccounting.R;
import com.qashar.mypersonalaccounting.Activities.UpdateModelActivity;

import java.text.SimpleDateFormat;
import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.MyViewHolder> {
    final Context context;
    final List<Task> tasks;
    private SelectItem selectItem;

    public DetailsAdapter( Context context, List<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.icon.setImageResource(task.getIcon());
        holder.txtCurrency.setText(""+task.getCurrency());
        holder.txtType.setText(""+task.getType());
        holder.txtPrice.setText(""+task.getPrice());
        holder.txtDate.setText(new SimpleDateFormat("yyyy:MM:dd/hh:mm:aaa").format(task.getAddedAt()));
        if (task.isAddedAtWallet()){
            holder.txtPrice.setTextColor(Color.GREEN);
        }else holder.txtPrice.setTextColor(Color.RED);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateModelActivity.class);
                intent.putExtra("id",task.getId());
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtType,txtPrice,txtCurrency,txtDate;
        private ImageView icon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtType = itemView.findViewById(R.id.detailsItemtxtType);
            txtPrice = itemView.findViewById(R.id.detailsItemtxtPrice);
            txtCurrency = itemView.findViewById(R.id.detailsItemtxtCurrency);
            icon = itemView.findViewById(R.id.detailsItemIcon);
            txtDate = itemView.findViewById(R.id.detailsItemtxtDate);

        }


    }

}