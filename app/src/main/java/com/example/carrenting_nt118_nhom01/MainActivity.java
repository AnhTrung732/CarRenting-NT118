package com.example.carrenting_nt118_nhom01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.carrenting_nt118_nhom01.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.activity:
                    replaceFragment(new ActivityFragment());
                    break;
                /*case R.id.user:
                    replaceFragment(new UserFragment());
                    break;*/
                case R.id.message:
                    replaceFragment(new MessageFragment());
                    break;
                case R.id.setting:
                    replaceFragment(new SettingFragment());
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}