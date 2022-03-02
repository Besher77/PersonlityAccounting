package com.qashar.mypersonalaccounting.Fragments;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.qashar.mypersonalaccounting.InterFace.SelectType;
import com.qashar.mypersonalaccounting.R;
import com.qashar.mypersonalaccounting.databinding.ItemBottomsheetBinding;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     ItemListDialogFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class ItemListDialogFragment extends BottomSheetDialogFragment {


    private ItemBottomsheetBinding binding;
    private SelectType selectType;

    public ItemListDialogFragment(SelectType selectType) {
        this.selectType = selectType;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding = ItemBottomsheetBinding.inflate(inflater, container, false);
        binding.t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectType.onSelect(getResources().getString(R.string.Nagdy));
            }
        });
        binding.t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectType.onSelect(getResources().getString(R.string.CreditCard));
            }
        });
        binding.t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectType.onSelect(getResources().getString(R.string.More));
            }
        });
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return binding.getRoot();

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}