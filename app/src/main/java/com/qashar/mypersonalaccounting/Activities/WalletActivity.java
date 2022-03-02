package com.qashar.mypersonalaccounting.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.qashar.mypersonalaccounting.Adapters.MangeAdapter;
import com.qashar.mypersonalaccounting.Fragments.BlankFragment;
import com.qashar.mypersonalaccounting.Models.Wallet;
import com.qashar.mypersonalaccounting.RoomDB.MyViewModels;
import com.qashar.mypersonalaccounting.databinding.ActivityWalletBinding;

import java.util.Collections;
import java.util.List;

public class WalletActivity extends AppCompatActivity  {
    private ActivityWalletBinding binding;
    private MyViewModels viewModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWalletBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModels = new MyViewModels(getApplication());
        binding.newWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(new BlankFragment(),"Fragmnt").commit();
            }
        });
        try {

        viewModels.getAllWallets().observe(this, new Observer<List<Wallet>>() {
            @Override
            public void onChanged(List<Wallet> wallets) {

                binding.mageRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                binding.mageRV.setAdapter(new MangeAdapter(getSupportFragmentManager(),WalletActivity.this,viewModels,WalletActivity.this::selectWallet,WalletActivity.this,wallets));

//                binding.mageRV.setHasFixedSize(true);


                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
                binding.mageRV.addItemDecoration(dividerItemDecoration);


                ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                        int fromPosition = viewHolder.getAdapterPosition();
                        int toPosition = target.getAdapterPosition();

//                        viewModels.getAllWalletById(viewHolder.itemView);
                        Collections.swap(wallets, fromPosition, toPosition);
                        Log.i("DDDD",fromPosition+":"+toPosition);
                        recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                    }
                };
                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
                itemTouchHelper.attachToRecyclerView(binding.mageRV);

              }
        });

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), ""+e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    private void insRV() {



    }


    private void selectWallet(Wallet wallet) {
    }


}