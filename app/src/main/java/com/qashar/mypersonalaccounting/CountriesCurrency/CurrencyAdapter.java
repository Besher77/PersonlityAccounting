package com.qashar.mypersonalaccounting.CountriesCurrency;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qashar.mypersonalaccounting.InterFace.SelectCurrency;
import com.qashar.mypersonalaccounting.R;

import java.util.ArrayList;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.MyViewHolder> {
    final Context context;
    private SelectCurrency selectCurrency;
    final ArrayList<Currency> currencyArrayList;

    public CurrencyAdapter(SelectCurrency selectCurrency,Context context, ArrayList<Currency> currencyArrayList) {
        this.context = context;
        this.selectCurrency = selectCurrency;
        this.currencyArrayList = currencyArrayList;


    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.textView.setText(""+currencyArrayList.get(position).getName());
        holder.imageView.setImageResource(currencyArrayList.get(position).getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCurrency.select(currencyArrayList.get(position));
            }
        });
    }
    @Override
    public int getItemCount() {
        return currencyArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.currencyItemTxt);
            imageView = itemView.findViewById(R.id.currencyItemImage);
        }


    }

}