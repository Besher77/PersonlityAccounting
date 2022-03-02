package com.qashar.mypersonalaccounting.Else;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.qashar.mypersonalaccounting.InterFace.SelectString;
import com.qashar.mypersonalaccounting.R;
import com.qashar.mypersonalaccounting.databinding.AlertBinding;

public class AlertTime extends BottomSheetDialogFragment {
    private AlertBinding alertBinding;
    private SelectString selectString;

    public AlertTime(SelectString selectString) {
        this.selectString = selectString;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        alertBinding = AlertBinding.inflate(getLayoutInflater());
        alertBinding.timeDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            select(getResources().getString(R.string.Daily));
            }
        });    alertBinding.timeWeekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select(getResources().getString(R.string.Weekly));
            }
        });    alertBinding.timeMonthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select(getResources().getString(R.string.Monthly));
            }
        });    alertBinding.timeYearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select(getResources().getString(R.string.Yearly));
            }
        });
        return alertBinding.getRoot();
    }
    private void select(String s){
        selectString.select(s);
    }
}
