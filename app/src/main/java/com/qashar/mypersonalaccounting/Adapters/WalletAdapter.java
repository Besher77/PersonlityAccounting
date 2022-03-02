package com.qashar.mypersonalaccounting.Adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.qashar.mypersonalaccounting.Models.Task;
import com.qashar.mypersonalaccounting.InterFace.SelectWallet;
import com.qashar.mypersonalaccounting.R;
import com.qashar.mypersonalaccounting.RoomDB.MyViewModels;
import com.qashar.mypersonalaccounting.Models.Wallet;

import java.util.List;


public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.MyViewHolder> {
    final Context context;
    final List<Wallet> wallets;
    private String a,b,c;
    private SelectWallet selectWallet;
    private MyViewModels viewModels;
    Float off = 0f;
    Float on = 0f;
    Float op = 0f;

    public WalletAdapter(MyViewModels viewModels,SelectWallet selectWallet, Context context, List<Wallet> wallets) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        viewModels.getAllTasksByWallet(wallets.get(position).getName()).observe((LifecycleOwner) context, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> walletOperations) {
                off = 0f;
                on = 0f;
                op = 0f;
                for (int i = 0; i < walletOperations.size(); i++) {
                if (walletOperations.get(i).isAddedAtWallet()){
                    off = off + walletOperations.get(i).getPrice();
                }else {
                    on = on + walletOperations.get(i).getPrice();
                } }
                op = on-(off);

            holder.name.setText(wallets.get(position).getName()+"");
            holder.price.setText((wallets.get(position).getPrice()+op)+"");
            holder.type.setText(wallets.get(position).getType()+"");
            holder.currency.setText(wallets.get(position).getCurrency()+"");
        if (holder.currency.equals(a)){
            holder.imageView.setImageResource(R.drawable.ic);
        }else if (holder.currency.equals(b)){
            holder.imageView.setImageResource(R.drawable.cre);
        }else if (holder.currency.equals(c)){
            holder.imageView.setImageResource(R.drawable.more);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectWallet.selectWallet(wallets.get(position));
            }
        });
            }
        });

    }
    @Override
    public int getItemCount() {
        return wallets.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name,price,currency,type;
        private ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.walletTxtName);
            price = itemView.findViewById(R.id.walletTxtPrice);
            currency = itemView.findViewById(R.id.walletTxtCurrency);
            type = itemView.findViewById(R.id.walletTxtType);
            imageView = itemView.findViewById(R.id.imageView);
        }


    }

}