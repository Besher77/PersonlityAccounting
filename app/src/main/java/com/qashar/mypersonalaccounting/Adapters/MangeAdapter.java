package com.qashar.mypersonalaccounting.Adapters;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.qashar.mypersonalaccounting.Models.Task;
import com.qashar.mypersonalaccounting.Fragments.EditWalletFragment;
import com.qashar.mypersonalaccounting.InterFace.SelectWallet;
import com.qashar.mypersonalaccounting.R;
import com.qashar.mypersonalaccounting.RoomDB.MyViewModels;
import com.qashar.mypersonalaccounting.Models.Wallet;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.util.List;


public class MangeAdapter extends RecyclerView.Adapter<MangeAdapter.MyViewHolder> {
    final Context context;
    final List<Wallet> wallets;
    private SelectWallet selectWallet;
    private MyViewModels viewModels;
    private Activity activity;
    private FragmentManager manager;

    public MangeAdapter(FragmentManager manager,Activity activity,MyViewModels viewModels, SelectWallet selectWallet, Context context, List<Wallet> wallets) {
        this.context = context;
        this.manager = manager;
        this.activity=activity;
        this.viewModels = viewModels;
        this.selectWallet = selectWallet;
        this.wallets = wallets;

    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mangment_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        viewModels.getAllTasksByWallet(wallets.get(position).getName()).observe((LifecycleOwner) context, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> walletOperations) {
                holder.name.setText(wallets.get(position).getName());
                holder.count.setText(walletOperations.size()+"");
                holder.visible.setVisibility(View.VISIBLE);
                if (wallets.get(position).getStatus()){
                    holder.visible.setImageResource(R.drawable.ic_vis);
                }else {
                    holder.visible.setImageResource(R.drawable.ic_unvis);
                }
                holder.visible.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (wallets.get(position).getStatus()){
                            holder.visible.setImageResource(R.drawable.ic_unvis);
                            Wallet wallet = wallets.get(position);
                            viewModels.updateWallet(new Wallet(wallet.getId(),wallet.getName(),wallet.getPrice()
                                    ,wallet.getCurrency(),wallet.getType(),false,wallet.getAddedAt(),0));
                        }else {
                            Wallet wallet = wallets.get(position);
                            viewModels.updateWallet(new Wallet(wallet.getId(),wallet.getName(),wallet.getPrice()
                                    ,wallet.getCurrency(),wallet.getType(),true,wallet.getAddedAt(),0));
                            holder.visible.setImageResource(R.drawable.ic_vis);
                        }
                    }
                });
                holder.icMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu = new PopupMenu(context,holder.icMore);
                        popupMenu.getMenuInflater().inflate(R.menu.wallet_info, popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                switch (menuItem.getItemId()){
                                    case R.id.infoDeleteWallet:
                                        try {
                                            BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(activity)
                                                    .setTitle("Delete?")
                                                    .setMessage("Are you sure want to delete this file?")
                                                    .setCancelable(false).setAnimation(R.raw.a1)
                                                    .setPositiveButton("Delete", R.drawable.ic_down, new BottomSheetMaterialDialog.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int which) {
                                                            Toast.makeText(activity.getApplicationContext(), "Deleted!", Toast.LENGTH_SHORT).show();
                                                            viewModels.getAllTasksByWallet(wallets.get(position).getName()).observe((LifecycleOwner) context, new Observer<List<Task>>() {
                                                            @Override
                                                            public void onChanged(List<Task> walletOperations) {
                                                                viewModels.deleteWallet(wallets.get(position));
                                                                for (int i = 0; i < walletOperations.size(); i++) {
                                                                    viewModels.deleteTask(walletOperations.get(i));
                                                                }
                                                            }});
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


                                        }catch (Exception e){
                                        }



                                        break;
                                        case R.id.infoEditWallet:
                                            Bundle bundle = new Bundle();
                                            bundle.putInt("id",wallets.get(position).getId());
                                            EditWalletFragment fragment = new EditWalletFragment();
                                            fragment.setArguments(bundle);
                                            manager.beginTransaction().add(fragment,"Fragmnt").commit();

                                            break;
                                }
                                return true;
                            }
                        });
                        // Showing the popup menu
                        popupMenu.show();
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
        private TextView name,count;
        private ImageView visible,icMore;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.mangeWtxt);
            count = itemView.findViewById(R.id.mangeWCount);
            icMore = itemView.findViewById(R.id.mangeMore);
            visible = itemView.findViewById(R.id.visible);
        }


    }

}