package com.qashar.mypersonalaccounting.Fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.qashar.mypersonalaccounting.databinding.SelectDateFegmentBinding;

import org.joda.time.DateTime;

public class FragmentSelectDate extends DialogFragment {
    private SelectDateFegmentBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SelectDateFegmentBinding.inflate(getLayoutInflater());


        binding.datePicker.setListener(new DatePickerListener() {
            @Override
            public void onDateSelected(DateTime dateSelected) {
                Toast.makeText(getActivity().getApplicationContext(), ""+dateSelected.toString(), Toast.LENGTH_SHORT).show();
            }
        }).init();
        return binding.getRoot();
    }
}
