package com.example.carrenting.ActivityPages;

import androidx.appcompat.app.AppCompatActivity;

public class OwnerMainActivity extends AppCompatActivity {
    /*ActivityMainBinding binding;
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
                case R.id.home:
                    replaceFragment(new Owner_VehicleFragment());
                    break;
                case R.id.activity:
                    replaceFragment(new Owner_ActivityFragment());
                    break;
                case R.id.vehicle:
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
    }*/
}