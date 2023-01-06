package com.example.carrenting.ActivityPages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.carrenting.FragmentPages.Customer.HomeFragment;
import com.example.carrenting.FragmentPages.Owner.VehiclesOwner_ActivityFragment;
import com.example.carrenting.FragmentPages.Owner.OwnerHomeFragment;
import com.example.carrenting.FragmentPages.Owner.OwnerMessageFragment;
import com.example.carrenting.FragmentPages.Owner.OwnerSettingFragment;
import com.example.carrenting.FragmentPages.Owner.OwnerVehicleFragment;
import com.example.carrenting.R;
import com.example.carrenting.databinding.ActivityMainBinding;

public class VehiclesOwner_MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @SuppressLint("NonConstantResourceId")
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
                    replaceFragment(new OwnerHomeFragment());
                    break;
                case R.id.activity:
                    replaceFragment(new VehiclesOwner_ActivityFragment());
                    break;
                case R.id.vehicle:
                    replaceFragment(new OwnerVehicleFragment());
                    break;
                case R.id.message:
                    replaceFragment(new OwnerMessageFragment());
                    break;
                case R.id.setting:
                    replaceFragment(new OwnerSettingFragment());
            }
            return true;
        });
    }
    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_vehicle_owner, fragment);
        fragmentTransaction.commit();
    }
}