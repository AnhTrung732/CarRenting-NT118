package com.example.carrenting.ActivityPages;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.carrenting.FragmentPages.Customer.Customer_ActivityFragment;
import com.example.carrenting.FragmentPages.Customer.Customer_HomeFragment;
import com.example.carrenting.FragmentPages.Customer.Customer_MessageFragment;
import com.example.carrenting.FragmentPages.Customer.Customer_SettingFragment;
import com.example.carrenting.FragmentPages.Owner.Owner_ActivityFragment;
import com.example.carrenting.FragmentPages.Owner.Owner_BankingFragment;
import com.example.carrenting.FragmentPages.Owner.Owner_MessageFragment;
import com.example.carrenting.FragmentPages.Owner.Owner_SettingFragment;
import com.example.carrenting.FragmentPages.Owner.Owner_VehicleFragment;
import com.example.carrenting.R;
import com.example.carrenting.databinding.ActivityMainBinding;

public class OwnerMainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new Customer_HomeFragment());
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.vehicle:
                    replaceFragment(new Owner_VehicleFragment());
                    break;
                case R.id.activity:
                    replaceFragment(new Owner_ActivityFragment());
                    break;
                case R.id.banking:
                    replaceFragment(new Owner_BankingFragment());
                    break;
                case R.id.message:
                    replaceFragment(new Owner_MessageFragment());
                    break;
                case R.id.setting:
                    replaceFragment(new Owner_SettingFragment());
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_owner, fragment);
        fragmentTransaction.commit();
    }
}