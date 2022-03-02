package com.qashar.mypersonalaccounting.ui;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.google.android.material.datepicker.MaterialDatePicker;
import com.qashar.mypersonalaccounting.Models.Task;
import com.qashar.mypersonalaccounting.Activities.ShowDetailsActivity;
import com.qashar.mypersonalaccounting.InterFace.SelectWallet;
import com.qashar.mypersonalaccounting.R;
import com.qashar.mypersonalaccounting.RoomDB.MyViewModels;
import com.qashar.mypersonalaccounting.Adapters.SelectWalletAdapter;
import com.qashar.mypersonalaccounting.Models.Wallet;
import com.qashar.mypersonalaccounting.databinding.FragmentSecondBinding;

import org.eazegraph.lib.models.PieModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressLint("ValidFragment")
public class SecondFragment extends android.app.Fragment {
    private FragmentSecondBinding binding;
    private String selectedWallet = "*";
    private MyViewModels models;
    private Long fromDate ,toDate;
    private Date Dfr , Dto;
    private List<Wallet> walletList ;
    private Float _startPrice = 0f;
    private Float _finalPrice = 0f;
    private Float _income = 0f;
    private Float _outgoings = 0f;
    private Float prBasice = 0f;
    private Float prMiddle = 0f;
    private Float prBad = 0f;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
    SimpleDateFormat firstFormatter = new SimpleDateFormat("yyyy-dd-MM");
    FragmentManager getSupportFragmentManager;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd");

    public SecondFragment(FragmentManager supportFragmentManager) {
        this.getSupportFragmentManager = supportFragmentManager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSecondBinding.inflate(getLayoutInflater());
        models = new MyViewModels(getActivity().getApplication());
        try {
            selectFirstDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        binding.to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                pickTo();
                selectDate();
            }
        });
        binding.from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//    getChildFragmentManager().beginTransaction().add(new FragmentSelectDate(),"Fragmnt").commit();

//                MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
//                CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
//                builder.setTheme(R.style.CustomThemeOverlay_MaterialCalendar_Fullscreen);
//                builder.setCalendarConstraints(constraintsBuilder.build());
//                MaterialDatePicker  picker = builder.build();
//                assert getFragmentManager() != null;
//                picker.show(getSupportFragmentManager, picker.toString());
//                picker.addOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialog) {
//                        Log.i("UUUU",getFrom(picker.getHeaderText())+"");
//                        Log.i("UUUU",Long.parseLong(dateFormat.format(new Date()))+"");
//
//                    }
//                });
                    selectDate();

            }
        });

        pudWallets();

        binding.rlBad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowDetailsActivity.class);
                intent.putExtra("type","bad");
                intent.putExtra("chart","yes");
                intent.putExtra("wallet",selectedWallet);
                intent.putExtra("from",fromDate);
                intent.putExtra("to",toDate);
                startActivity(intent);
            }
        });
        binding.rlMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowDetailsActivity.class);
                intent.putExtra("type","middle");
                intent.putExtra("chart","yes");
                intent.putExtra("wallet",selectedWallet);
                intent.putExtra("from",fromDate);
                intent.putExtra("to",toDate);
                startActivity(intent);
            }
        });
        binding.rlGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowDetailsActivity.class);
                intent.putExtra("type","good");
                intent.putExtra("chart","yes");
                intent.putExtra("wallet",selectedWallet);
                intent.putExtra("from",fromDate);
                intent.putExtra("to",toDate);
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }

    private void selectFirstDate() throws ParseException {
        Date s = new Date();
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        binding.from.setText(year.format(s)+"-"+month.format(s)+"-"+1);
        binding.to.setText(year.format(s)+"-"+month.format(s)+"-"+28);
        fromDate = toLong(sdf.parse(year.format(s)+"-"+month.format(s)+"-"+1));
        toDate = toLong(sdf.parse(year.format(s)+"-"+month.format(s)+"-"+28));
        getAllWalletOpByDate(fromDate,toDate);

    }

    private void selectDate() {
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT From DATE");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        materialDatePicker.show(getSupportFragmentManager, "MATERIAL_DATE_PICKER");
        materialDatePicker.addOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.i("QQQQ",""+materialDatePicker.getHeaderText());
                try {
                    SimpleDateFormat format = new SimpleDateFormat("LLL dd,yyyy");
                    Date s = format.parse(materialDatePicker.getHeaderText());
                    SimpleDateFormat month = new SimpleDateFormat("MM");
                    SimpleDateFormat day = new SimpleDateFormat("dd");
                    SimpleDateFormat year = new SimpleDateFormat("yyyy");
                    binding.from.setText(month.format(s)+":"+ day.format(s)+":"+year.format(s));
                    fromDate = toLong(sdf.parse(year.format(s)+"-"+month.format(s)+"-"+day.format(s)));

                    MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
                    materialDateBuilder.setTitleText("SELECT To DATE");
                    final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

                    materialDatePicker.show(getSupportFragmentManager, "MATERIAL_DATE_PICKER");
                    materialDatePicker.addOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            Log.i("QQQQ",""+materialDatePicker.getHeaderText());
                            try {
                                SimpleDateFormat format = new SimpleDateFormat("LLL dd,yyyy");
                                Date s = format.parse(materialDatePicker.getHeaderText());
                                SimpleDateFormat month = new SimpleDateFormat("MM");
                                SimpleDateFormat day = new SimpleDateFormat("dd");
                                SimpleDateFormat year = new SimpleDateFormat("yyyy");
                                binding.to.setText(month.format(s)+":"+ day.format(s)+":"+year.format(s));

                                toDate = toLong(sdf.parse(year.format(s)+"-"+month.format(s)+"-"+day.format(s)));
                                getAllWalletOpByDate(fromDate,toDate);
                                pudWallets(fromDate,toDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                    });


                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private Long getFrom(String headerText) {
        Date date = new Date();
        Date newDate = null;
        SimpleDateFormat formatB = new SimpleDateFormat("LLL dd");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat aa = new SimpleDateFormat("LLL dd, yyyy");
        String text[] = headerText.split(" - ");
        try {
            date = aa.parse(text[0]);
            newDate = aa.parse(text[0]);
            binding.from.setText(year.format(date)+":"+month.format(date));

        } catch (ParseException e) {
            try {
                date = formatB.parse(text[0]);
                binding.from.setText(day.format(date)+":"+month.format(date));
               newDate = aa.parse(month.format(date)+day.format(date)+", "+year.format(new Date())+"");
                binding.from.setText(year.format(newDate)+":"+day.format(newDate)+":"+month.format(newDate));
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
            e.printStackTrace();
        }

        return toLong(newDate);
    }
    private void getAllWalletOpByDate(Long from , Long to) {
        models.getAllWalletByDate(from,to).observe((LifecycleOwner) getActivity(), new Observer<List<Wallet>>() {
            @Override
            public void onChanged(List<Wallet> wallets) {
                Float totalOfWallets = 0f;
                for (int i = 0; i < wallets.size(); i++) {
                    totalOfWallets = totalOfWallets+wallets.get(i).getPrice();
                    int finalI = i;
                    Float finalTotalOfWallets = totalOfWallets;
                    models.getAllTasksByDateAndWalletName(from,to,wallets.get(i).getName())
                            .observe((LifecycleOwner) getActivity(), new Observer<List<Task>>() {
                                @Override
                                public void onChanged(List<Task> tasks) {
                                    prioritiesList(tasks);

                                    Float out = 0f;
                                    Float in = 0f;
                                    for (int i = 0; i <tasks.size() ; i++) {
                                      if (tasks.get(i).isAddedAtWallet()){
                                          in = in + tasks.get(i).getPrice();
                                      }else {
                                          out = out + tasks.get(i).getPrice();
                                      }

                                    }
           Float finalPrice = finalTotalOfWallets+(in-(out));
                                    putCharWithDetails(finalTotalOfWallets,finalPrice,in,out);
                                }
                            });


                }

                  }
        });
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
    private void aaa(String name,Long from , Long to) {
        models.getAllTasksByDateAndWalletName(from,to,name).observe((LifecycleOwner) getActivity(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                Toast.makeText(getActivity(), ""+tasks.size(), Toast.LENGTH_SHORT).show();
            }
        });
        models.getAllWalletByDateAndName(from,to,name).observe((LifecycleOwner) getActivity(), new Observer<List<Wallet>>() {
            @Override
            public void onChanged(List<Wallet> wallets) {
                Log.i("HHHH","tasks=:"+wallets.size());

                Float totalOfWallets = 0f;
                for (int i = 0; i < wallets.size(); i++) {
                    totalOfWallets = totalOfWallets+wallets.get(i).getPrice();
                    int finalI = i;
                    Float finalTotalOfWallets = totalOfWallets;
                    models.getAllTasksByWallet(name)
                            .observe((LifecycleOwner) getActivity(), new Observer<List<Task>>() {
                                @Override
                                public void onChanged(List<Task> tasks) {
                                    prioritiesList(tasks);
                                    Float out = 0f;
                                    Float in = 0f;
                                    Log.i("HHHH","tasks=:"+tasks.size());
                                    for (int i = 0; i <tasks.size() ; i++) {
                                      if (tasks.get(i).isAddedAtWallet()){
                                          in = in + tasks.get(i).getPrice();
                                      }else {
                                          out = out + tasks.get(i).getPrice();
                                      }

                                    }
           Float finalPrice = finalTotalOfWallets+(in-(out));
                                    putCharWithDetails(finalTotalOfWallets,finalPrice,in,out);
                                }
                            });


                }

                  }
        });
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
    private void getAllWalletOpByDateAndSingleWallet(String wallet,Long from , Long to) {
        models.getAllWalletByDateAndName(from,to,wallet).observe((LifecycleOwner) getActivity(), new Observer<List<Wallet>>() {
            @Override
            public void onChanged(List<Wallet> wallets) {
                Float totalOfWallets = 0f;
                for (int i = 0; i < wallets.size(); i++) {
                    totalOfWallets = totalOfWallets+wallets.get(i).getPrice();
                    int finalI = i;
                    Float finalTotalOfWallets = totalOfWallets;
                    models.getAllTasksByDateAndWalletName(from,to,wallet)
                            .observe((LifecycleOwner) getActivity(), new Observer<List<Task>>() {
                                @Override
                                public void onChanged(List<Task> tasks) {
                                    prioritiesList(tasks);

                                    Float out = 0f;
                                    Float in = 0f;
                                    for (int i = 0; i <tasks.size() ; i++) {
                                      if (tasks.get(i).isAddedAtWallet()){
                                          in = in + tasks.get(i).getPrice();
                                      }else {
                                          out = out + tasks.get(i).getPrice();
                                      }

                                    }
                                Float finalPrice = finalTotalOfWallets+(in-(out));
                                    putCharWithDetails(finalTotalOfWallets,finalPrice,in,out);
                                }
                            });


                }

                  }
        });
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
/*
    private void getAllWalletOpByDateAndSingleWallet(String wallet,Long from , Long to) {
                    models.getAllTasksByDateAndWalletName(from,to,wallet)
                            .observe((LifecycleOwner) getActivity(), new Observer<List<Task>>() {
                                @Override
                                public void onChanged(List<Task> tasks) {
                                    prioritiesList(tasks);
                                    Float out = 0f;
                                    Float totalOfWallet = 0f;

                                    totalOfWallet = totalOfWallet+tasks.get(0).getPrice();
                                    Float in = 0f;
                                    for (int i = 0; i <tasks.size() ; i++) {
                                      if (tasks.get(i).isAddedAtWallet()){
                                          in = in + tasks.get(i).getPrice();
                                      }else {
                                          out = out + tasks.get(i).getPrice();
                                      }

                                    }
           Float finalPrice = totalOfWallet+(in-(out));
                                    putCharWithDetails(totalOfWallet,finalPrice,in,out);
                                }
                            });



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

    private void prioritiesList(List<Task> tasks) {
        Float basic = 0f;
        Float middle = 0f;
        Float sad = 0f;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getEmoji().equals("basic")){
                middle =  middle+tasks.get(i).getPrice();
            }else if (tasks.get(i).getEmoji().equals("middle")){
                basic = basic+ tasks.get(i).getPrice();
            }else if (tasks.get(i).getEmoji().equals("sad")){
                sad = sad + tasks.get(i).getPrice();
            }

        }
        putPriorities(basic,middle,sad);
    }

    public static Long toLong(Date date){
        return date==null?null:date.getTime();
    }
    private void pudWallets() {
        walletList = new ArrayList<>();
        models.getAllTrueWallets().observe((LifecycleOwner) getActivity(), new Observer<List<Wallet>>() {
            @Override
            public void onChanged(List<Wallet> wallets) {
                walletList.clear();
                    walletList.add(new Wallet(getResources().getString(R.string.all),0f,"","",true,toLong(new Date()),0));
                for (int i = 0; i < wallets.size(); i++) {
                    if (wallets.get(i).getStatus()){
                        walletList.add(wallets.get(i));
                    }
                }

            setAdapter(wallets);
            }
        });
    }
    private void pudWallets(Long from,Long To) {
        walletList = new ArrayList<>();
        models.getAllWalletByDate(from,toDate).observe((LifecycleOwner) getActivity(), new Observer<List<Wallet>>() {
            @Override
            public void onChanged(List<Wallet> wallets) {
                walletList.clear();
                    walletList.add(new Wallet(getResources().getString(R.string.all),0f,"","",true,toLong(new Date()),0));
                for (int i = 0; i < wallets.size(); i++) {
                    if (wallets.get(i).getStatus()){
                        walletList.add(wallets.get(i));
                    }
                }

             setAdapter(walletList);
            }
        });
    }

    private void setAdapter(List<Wallet> list) {
        SelectWalletAdapter adapter = new SelectWalletAdapter(selectedWallet,models, new SelectWallet() {
            @Override
            public void selectWallet(Wallet wallet) {
                Toast.makeText(getActivity(), ""+wallet.getName(), Toast.LENGTH_SHORT).show();

                if (wallet.getName().equals(getResources().getString(R.string.all))){
                    getAllWalletOpByDate(fromDate,toDate);
                    selectedWallet = "*";
                }
                else {
                    selectedWallet = wallet.getName();
                    aaa(wallet.getName(),fromDate,toDate);
                }
            }
        }, getActivity(), list);
        binding.rv.setAdapter(adapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
    }

    private void putPriorities(Float prBasice, Float prMiddle, Float prBad) {
        binding.tvPRGOOD.setText(""+prBasice);
        binding.tvPRMiddle.setText(""+prMiddle);
        binding.tvPRBAD.setText(""+prBad);
    }
    private void putCharWithDetails(Float startPrice,Float finalPrice,Float income , Float outgoings) {
        // Set the percentage of language used
        binding.piechart.clearChart();
        binding.tvStartPrice.setText(Float.toString(startPrice));
        binding.tvIncom.setText(Float.toString(income));
        binding.tvOutgoings.setText(Float.toString(outgoings));
        binding.tvFinalPrice.setText(Float.toString(finalPrice));


        binding.piechart.addPieSlice(
                new PieModel(
                        "A",
                        Float.parseFloat(binding.tvStartPrice.getText().toString()),
                        Color.parseColor("#FFA726")));
        binding.piechart.addPieSlice(
                new PieModel(
                        "Python",
                        Float.parseFloat(binding.tvIncom.getText().toString()),
                        Color.parseColor("#66BB6A")));
        binding.piechart.addPieSlice(
                new PieModel(
                        "C++",
                        Float.parseFloat(binding.tvOutgoings.getText().toString()),
                        Color.parseColor("#EF5350")));
        binding.piechart.addPieSlice(
                new PieModel(
                        "Java",
                        Float.parseFloat(binding.tvFinalPrice.getText().toString()),
                        Color.parseColor("#29B6F6")));
        binding.piechart.startAnimation();

    }


}