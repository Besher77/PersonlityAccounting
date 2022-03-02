package com.qashar.mypersonalaccounting.Fragments;

import static com.qashar.mypersonalaccounting.ui.HomeFragment.toLong;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.qashar.mypersonalaccounting.Else.Print;
import com.qashar.mypersonalaccounting.R;
import com.qashar.mypersonalaccounting.RoomDB.MyViewModels;
import com.qashar.mypersonalaccounting.Models.Wallet;
import com.qashar.mypersonalaccounting.databinding.FragmentPrintBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressLint("ValidFragment")
public class PrintFragment extends DialogFragment {
    private FragmentPrintBinding binding;
    private MyViewModels viewModels;
    private Long fromDate ,toDate;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
    private FragmentManager getSupportFragmentManager;
    private boolean isSpDate = false;//Check if user need to print data in specific dates
    private Print print;
    @SuppressLint("ValidFragment")
    public PrintFragment(FragmentManager getSupportFragmentManager) {
        this.getSupportFragmentManager = getSupportFragmentManager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding = FragmentPrintBinding.inflate(getLayoutInflater());
        viewModels = new MyViewModels(getActivity().getApplication());
        print = new Print(getActivity(),getActivity().getApplication());
//        Print print = new Print(getContext(),getActivity().getApplication());
//        print.getDataAndPrint(toLong(new Date()),toLong(new Date()));
        putWalletToET();
        binding.radioSpDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    isSpDate = true;
                    binding.selectDate.setVisibility(View.VISIBLE);
                }else {
                    isSpDate = false;
                    binding.selectDate.setVisibility(View.GONE);
                }
            }
        });
        binding.btnFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });
        binding.btnToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });
        binding.btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                print();
            }
        });
    return binding.getRoot();
    }

    private void print() {
        String wallet = binding.etWallet.getText().toString();
        if (wallet.equals(getResources().getString(R.string.all))){
            printAll();
        }else {
            printSpWallet(wallet);
        }

    }

    private void printSpWallet(String wallet) {
        if (isSpDate){
            print.getDataByWalletAndPrint(fromDate,toDate,wallet);
        }else {
            print.getDataAndPrint(wallet);
        }
    }

    private void printAll() {
        if (isSpDate){
            print.getDataAndPrint(fromDate,toDate);
        }else {
            print.getData();
        }
    }
    private void selectDate() {
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT From DATE");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        materialDatePicker.show(getSupportFragmentManager, "MATERIAL_DATE_PICKER");
        materialDatePicker.addOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("LLL dd,yyyy");
                    Date s = format.parse(materialDatePicker.getHeaderText());
                    SimpleDateFormat month = new SimpleDateFormat("MM");
                    SimpleDateFormat day = new SimpleDateFormat("dd");
                    SimpleDateFormat year = new SimpleDateFormat("yyyy");
                    binding.btnFromDate.setText(month.format(s)+":"+ day.format(s)+":"+year.format(s));
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
                                binding.btnToDate.setText(month.format(s)+":"+ day.format(s)+":"+year.format(s));
                                toDate = toLong(sdf.parse(year.format(s)+"-"+month.format(s)+"-"+day.format(s)));
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

    private void putWalletToET() {
        viewModels.getAllTrueWallets().observe((LifecycleOwner) getActivity(), new Observer<List<Wallet>>() {
            @Override
            public void onChanged(List<Wallet> wallets) {
                List<String> strings  = new ArrayList<>();
                strings.add(getResources().getString(R.string.all));
                binding.etWallet.setText(getResources().getString(R.string.all));
                for (int i = 0; i < wallets.size(); i++) {
                    strings.add(wallets.get(i).getName());
                }
                ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item,strings);
                binding.etWallet.setAdapter(stringArrayAdapter);
                binding.etWallet.setThreshold(1);
            }
        });

    }
}
