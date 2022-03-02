package com.qashar.mypersonalaccounting.Adapters;


import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.qashar.mypersonalaccounting.Models.Group;
import com.qashar.mypersonalaccounting.InterFace.SelectString;
import com.qashar.mypersonalaccounting.R;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.MyViewHolder> {
    final Context context;
    final List<Group> groups;
    private SelectString selectString;

    public GroupAdapter(SelectString selectString,Context context, List<Group> groups) {
        this.context = context;
        this.selectString = selectString;
        this.groups = groups;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.name.setText(groups.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectString.select(groups.get(position).getName());
            }
        });

    }
    @Override
    public int getItemCount() {

        return groups.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtGroup);



        }


    }

}