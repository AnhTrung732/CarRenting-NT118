package com.example.carrenting_nt118_nhom01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.carrenting_nt118_nhom01.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setBackground(null);
        
    }
}