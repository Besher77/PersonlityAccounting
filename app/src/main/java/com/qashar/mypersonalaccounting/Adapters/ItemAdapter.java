package com.qashar.mypersonalaccounting.Adapters;


import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.qashar.mypersonalaccounting.InterFace.SelectItem;
import com.qashar.mypersonalaccounting.R;
import com.qashar.mypersonalaccounting.RoomDB.MyViewModels;
import com.qashar.mypersonalaccounting.Models.Task;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    final Context context;
    final List<Task> models;
    private SelectItem selectItem;
    private MyViewModels viewModels;

    public ItemAdapter(Context context, List<Task> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.aa, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Task model = models.get(position);
        holder.type.setText(model.getType());
        holder.icon.setImageResource(model.getIcon());
    }
    @Override
    public int getItemCount() {
        if (models.size()>3){
            return 3;
        }else
        return models.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView type;
        private ImageView icon;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.itemType);
            icon = itemView.findViewById(R.id.itemIcon);


        }


    }

}