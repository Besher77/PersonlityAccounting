package com.qashar.mypersonalaccounting.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.qashar.mypersonalaccounting.Adapters.GroupAdapter;
import com.qashar.mypersonalaccounting.InterFace.SelectString;
import com.qashar.mypersonalaccounting.Models.Group;
import com.qashar.mypersonalaccounting.RoomDB.MyViewModels;
import com.qashar.mypersonalaccounting.databinding.AddGroupDialogBinding;
import com.qashar.mypersonalaccounting.databinding.GroupFragmentBinding;

import java.util.List;

public class GroupFragment extends BlankFragment {
    private MyViewModels viewModels;
    private SelectString selectString;
    private GroupFragmentBinding binding;
    public GroupFragment(SelectString selectString) {
        this.selectString = selectString;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = GroupFragmentBinding.inflate(getLayoutInflater());
        viewModels = new MyViewModels(getActivity().getApplication());
        binding.addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });


        viewModels.getAllGroups().observe(this, new Observer<List<Group>>() {
            @Override
            public void onChanged(List<Group> groups) {
                groups.add(new Group("الاسرة"));
                groups.add(new Group("الاصدقاء"));
                groups.add(new Group("الاهل"));
                binding.groupRV.setAdapter(new GroupAdapter(new SelectString() {
                    @Override
                    public void select(String s) {
                        selectString.select(s);
                    }
                },getActivity(), groups));
                binding.groupRV.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });

        return binding.getRoot();
    }
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AddGroupDialogBinding dialogBinding = AddGroupDialogBinding.inflate(getLayoutInflater());
        builder.setView(dialogBinding.getRoot());
        AlertDialog dialog = builder.create();
        dialogBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModels.insertGroup(new Group(dialogBinding.etName.getText().toString()));
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
