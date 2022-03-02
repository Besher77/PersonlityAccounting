package com.qashar.mypersonalaccounting.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.qashar.mypersonalaccounting.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.animationView.animate();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }, 3000);

    }
}