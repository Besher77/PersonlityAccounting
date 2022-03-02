package com.qashar.mypersonalaccounting.Category;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.qashar.mypersonalaccounting.InterFace.PickImage;
import com.qashar.mypersonalaccounting.R;
import com.qashar.mypersonalaccounting.databinding.ABinding;
import com.qashar.mypersonalaccounting.databinding.FragmentItemImagesListDialogBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     ItemImages.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class ItemImages extends BottomSheetDialogFragment implements PickImage {

    // TODO: Customize parameter argument names
    private static final String ARG_ITEM_COUNT = "item_count";
    private FragmentItemImagesListDialogBinding binding;
    private PickImage pickImage;

    public ItemImages(PickImage pickImage) {
        this.pickImage = pickImage;
    }

    // TODO: Customize parameters
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentItemImagesListDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),5));
        recyclerView.setAdapter(new ItemAdapter(getAllImages(),this));
    }

    private List<Integer> getAllImages() {
        List<Integer> integerList =new ArrayList<>();
        integerList.add(R.drawable.i1);
        integerList.add(R.drawable.i2);
        integerList.add(R.drawable.i3);
        integerList.add(R.drawable.i4);
        integerList.add(R.drawable.i5);
        integerList.add(R.drawable.i6);
        integerList.add(R.drawable.icon_1);
        integerList.add(R.drawable.icon_2);
        integerList.add(R.drawable.icon_7);
        integerList.add(R.drawable.icon_10);
        integerList.add(R.drawable.icon_13);
        integerList.add(R.drawable.icon_14);
        integerList.add(R.drawable.icon_18);
        integerList.add(R.drawable.icon_19);
        integerList.add(R.drawable.icon_20);
        integerList.add(R.drawable.icon_21);
        integerList.add(R.drawable.icon_34);
        integerList.add(R.drawable.icon_36);
        integerList.add(R.drawable.icon_37);
        integerList.add(R.drawable.icon_41);
        integerList.add(R.drawable.icon_42);
        integerList.add(R.drawable.icon_43);
        integerList.add(R.drawable.icon_44);
        integerList.add(R.drawable.icon_45);
        integerList.add(R.drawable.icon_46);
        integerList.add(R.drawable.icon_48);
        integerList.add(R.drawable.icon_50);
        integerList.add(R.drawable.icon_52);
        integerList.add(R.drawable.icon_60);
        integerList.add(R.drawable.icon_62);
        integerList.add(R.drawable.icon_65);
        integerList.add(R.drawable.icon_68);
        integerList.add(R.drawable.icon_69);
        integerList.add(R.drawable.icon_78);
        integerList.add(R.drawable.icon_79);
        integerList.add(R.drawable.icon_83);
        integerList.add(R.drawable.icon_85);
        integerList.add(R.drawable.icon_86);
        integerList.add(R.drawable.icon_87);
        integerList.add(R.drawable.icon_89);
        integerList.add(R.drawable.icon_100);
        integerList.add(R.drawable.icon_102);
        integerList.add(R.drawable.icon_103);
        integerList.add(R.drawable.icon_104);
        integerList.add(R.drawable.icon_123);

        return integerList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPick(Integer res) {
     pickImage.onPick(res);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView text;

        ViewHolder(ABinding binding) {
            super(binding.getRoot());
            text = binding.text;
        }

    }

    private class ItemAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<Integer> list = new ArrayList<>();
        private PickImage pickImage;

        ItemAdapter(List<Integer> list,PickImage pickImage) {
            this.list = list;
            this.pickImage = pickImage;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new ViewHolder(ABinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.text.setImageResource(list.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pickImage.onPick(list.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }
}