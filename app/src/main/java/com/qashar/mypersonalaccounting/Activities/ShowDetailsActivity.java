package com.qashar.mypersonalaccounting.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.qashar.mypersonalaccounting.Adapters.DetailsAdapter;
import com.qashar.mypersonalaccounting.Models.Task;
import com.qashar.mypersonalaccounting.RoomDB.MyViewModels;
import com.qashar.mypersonalaccounting.Models.Wallet;
import com.qashar.mypersonalaccounting.databinding.ActivityShowDetailsBinding;

import java.util.List;

public class ShowDetailsActivity extends AppCompatActivity  {
    private ActivityShowDetailsBinding binding;
    private MyViewModels viewModels;
    private String priority = "null";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModels = new MyViewModels(getApplication());
        if (getIntent().getStringExtra("chart").equals("yes")) {
            String type = getIntent().getStringExtra("type");
            String wallet = getIntent().getStringExtra("wallet");
            Long fromDate = getIntent().getLongExtra("from", 0);
            Long toDate = getIntent().getLongExtra("to", 0);
            if (type.equals("bad")) {
                priority = "sad";
            } else if (type.equals("middle")) {
                priority = "middle";
            } else if (type.equals("good")) {
                priority = "basic";

            }
        getAllTasks(fromDate,toDate,wallet);
        } else {
                if (getIntent().getStringExtra("wallet").equals("*")) {
                    getAllDataByDate(getIntent().getStringExtra("date"));
                } else {
                    getSingleData(getIntent().getStringExtra("wallet"), getIntent().getStringExtra("date"));
                }
            }


        binding.fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModels.getAllTasksByPriority(priority).observe(ShowDetailsActivity.this, new Observer<List<Task>>() {
                    @Override
                    public void onChanged(List<Task> tasks) {
                        for (int i = 0; i < tasks.size(); i++) {
                            viewModels.deleteTask(tasks.get(i));
                        }

                    }
                });
            }
        });

    }

    private void getAllTasks(Long from, Long to, String wallet) {
        if (wallet.equals("*")){
            viewModels.getAllWalletByDate(from,to).observe((LifecycleOwner) ShowDetailsActivity.this,
                    new Observer<List<Wallet>>() {
                @Override
                public void onChanged(List<Wallet> wallets) {
                    for (int i = 0; i < wallets.size(); i++) {
                        viewModels.getAllTasksByDateAndWalletNameAndPrior(from,to,wallets.get(i).getName(),priority)
                                .observe((LifecycleOwner) ShowDetailsActivity.this, new Observer<List<Task>>() {
                                    @Override
                                    public void onChanged(List<Task> tasks) {
                                        Toast.makeText(getApplicationContext(), ""+tasks.size(), Toast.LENGTH_SHORT).show();
                                    }
                                });


                    }

                }
            });
        }else {
            viewModels.getAllTasksByDateAndWalletNameAndPrior(from,to,wallet,priority)
                                .observe((LifecycleOwner) ShowDetailsActivity.this, new Observer<List<Task>>() {
                                    @Override
                                    public void onChanged(List<Task> tasks) {
                                        Toast.makeText(getApplicationContext(), ""+tasks.size(), Toast.LENGTH_SHORT).show();
                                    }
                                });
        }

/*
        models.getAllWalletOPByDates(from,to).observe((LifecycleOwner) getActivity(), new Observer<List<WalletOperation>>() {
            @Override
            public void onChanged(List<WalletOperation> operations) {
                List<Wallet> list1 = new ArrayList<>();
                models.getAllWalletByDate(from,to).observe((LifecycleOwner) getActivity(), new Observer<List<Wallet>>() {
                    @Override
                    public void onChanged(List<Wallet> wallets) {
                        list1.add(new Wallet(getResources().getString(R.string.all),0f,"","",true,from));
                        for (int i = 0; i <wallets.size() ; i++) {
                            list1.add(wallets.get(i));
                        }
                        putData(operations,list1);
                    }
                });

            }
        });
*/
    }


    private void getSingleData(String wallet, String date) {
        viewModels.getSingleData(wallet,date).observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                binding.detilsRV.setAdapter(new DetailsAdapter(ShowDetailsActivity.this,tasks));
                binding.detilsRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }
        });
    }

    private void getAllDataByDate(String date) {
        viewModels.getAllTasksByDate(date).observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                binding.detilsRV.setAdapter(new DetailsAdapter(ShowDetailsActivity.this,tasks));
                binding.detilsRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.detilsRV.setHasFixedSize(true);
            }
        });
    }

    private void loadDataByPriority(String type) {
        viewModels.getAllTasksByPriority(type).observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                binding.detilsRV.setAdapter(new DetailsAdapter(ShowDetailsActivity.this,tasks));
                binding.detilsRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.detilsRV.setHasFixedSize(true);
            }
        });
    }
}