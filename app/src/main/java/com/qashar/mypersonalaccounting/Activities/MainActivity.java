package com.qashar.mypersonalaccounting.Activities;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.qashar.mypersonalaccounting.Else.Settings;
import com.qashar.mypersonalaccounting.R;
import com.qashar.mypersonalaccounting.databinding.ActivityMainBinding;
import com.qashar.mypersonalaccounting.ui.HomeFragment;
import com.qashar.mypersonalaccounting.ui.SecondFragment;
import com.qashar.mypersonalaccounting.ui.ThirdFragment;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Fragment fragment;
    private FragmentTransaction tr;
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActivityResultLauncher<String> arl = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
            }
        });
        arl.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        startActivity(new Intent(getApplicationContext(), LockActivity.class));
//        getSupportFragmentManager().beginTransaction().add(new BlankFragment(),"Fragmnt").commit();
//        getParentFragmentManager().beginTransaction().add(new BlankFragment4(),"Fragmnt").commit();

        try {
            fragment = new HomeFragment(getSupportFragmentManager());
            tr = getFragmentManager().beginTransaction();
            tr.add(R.id.fram,fragment).commit();
            binding.nav.setNavigationChangeListener(new BubbleNavigationChangeListener() {
                @Override
                public void onNavigationChanged(View view, int position) {
                    switch (position){
                        case 0:
                            fragment = new HomeFragment(getSupportFragmentManager());
                            break;
                        case 1:
                            fragment = new SecondFragment(getSupportFragmentManager());
                            break;
                        case 2:
                            fragment = new ThirdFragment(getSupportFragmentManager());
                            break; }
                    tr = getFragmentManager().beginTransaction();
                    tr.replace(R.id.fram,fragment).commit();
                }
            });



        }catch (Exception e){

        }


    }

    private void putColors(int i) {
        switch (i){
            case 2:
//                putThem3();
                break;
        }
    }

    private void putThem2() {
//        binding.nav.setColor
        new Settings(getApplicationContext()).setTheme(2);
        binding.nav.setBackgroundResource(R.color.nav2);
        binding.fram.setBackgroundResource(R.color.bg2);

    }
    private void putThem3() {
//        binding.nav.setColor
//        new Settings(getApplicationContext()).setTheme(3);
//        binding.nav.setBackgroundResource(R.color.nav3);
//        binding.fram.setBackgroundResource(R.color.bg3);

    }

}