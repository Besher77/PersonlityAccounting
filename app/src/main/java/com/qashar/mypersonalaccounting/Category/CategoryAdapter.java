package com.qashar.mypersonalaccounting.Category;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qashar.mypersonalaccounting.InterFace.SelectItem;
import com.qashar.mypersonalaccounting.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    final Context context;
    final List<Category> categories;
    private SelectItem selectItem;

    public CategoryAdapter(SelectItem selectItem,Context context, List<Category> categories) {
        this.context = context;
        this.selectItem = selectItem;
        this.categories = categories;


    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.txtFather.setText(category.getName());
        holder.txtSon.setText(category.getName());
        holder.imageFather.setImageResource(category.getImage());
        holder.imageSon.setImageResource(category.getImage());
            if (category.isParent()){
                holder.son.setVisibility(View.GONE);
            }else {
                holder.father.setVisibility(View.GONE);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectItem.select(category);
                }
            });

    }
    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtFather;
        private TextView txtSon;
        private ImageView imageFather;
        private ImageView imageSon;
        private LinearLayout father,son;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFather = itemView.findViewById(R.id.categoryFtxt);
            txtSon = itemView.findViewById(R.id.categoryStxt);
            imageFather = itemView.findViewById(R.id.categoryFimage);
            imageSon = itemView.findViewById(R.id.categorySimage);
            father = itemView.findViewById(R.id.father);
            son = itemView.findViewById(R.id.son);
        }


    }

}