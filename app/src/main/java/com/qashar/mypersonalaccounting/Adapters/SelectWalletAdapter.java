package com.qashar.mypersonalaccounting.Adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qashar.mypersonalaccounting.InterFace.SelectWallet;
import com.qashar.mypersonalaccounting.R;
import com.qashar.mypersonalaccounting.RoomDB.MyViewModels;
import com.qashar.mypersonalaccounting.Models.Wallet;

import java.util.List;


public class SelectWalletAdapter extends RecyclerView.Adapter<SelectWalletAdapter.MyViewHolder> {
    final Context context;
    final List<Wallet> wallets;
    private String a,b,c;
    private SelectWallet selectWallet;
    private MyViewModels viewModels;

    private static int lastCheckedPos = 0;

    public SelectWalletAdapter(String selectedWallet, MyViewModels viewModels, SelectWallet selectWallet, Context context, List<Wallet> wallets) {
        this.context = context;
        this.viewModels = viewModels;
        this.selectWallet = selectWallet;
        this.wallets = wallets;
        a = context.getResources().getString(R.string.Nagdy);
        b = context.getResources().getString(R.string.CreditCard);
        c = context.getResources().getString(R.string.More);

    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_select, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(wallets.get(position).getName());
        holder.name.setTypeface(Typeface.DEFAULT_BOLD);

        if (lastCheckedPos == position) {
            holder.itemView.setBackgroundResource(R.drawable.selected_3);
            holder.name.setTextColor(Color.WHITE);

        } else {
            holder.itemView.setBackgroundResource(R.drawable.unslected3);
            holder.name.setTextColor(Color.GRAY);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectWallet.selectWallet(wallets.get(position));
                if (lastCheckedPos >= 0)
                    notifyItemChanged(lastCheckedPos);
                lastCheckedPos = holder.getPosition();
                notifyItemChanged(lastCheckedPos);
            }
        });

    }
    @Override
    public int getItemCount() {
        return wallets.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.selectWalletTXT);

        }


    }

}