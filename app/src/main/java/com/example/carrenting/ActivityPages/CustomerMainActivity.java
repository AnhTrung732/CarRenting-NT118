package com.example.carrenting.ActivityPages;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.carrenting.FragmentPages.Customer.ActivityFragment;
import com.example.carrenting.FragmentPages.Customer.HomeFragment;
import com.example.carrenting.FragmentPages.Customer.MessageFragment;
import com.example.carrenting.FragmentPages.Customer.SettingFragment;
import com.example.carrenting.FragmentPages.Customer.UserInfor.MyProfileFragment;
import com.example.carrenting.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CustomerMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Side Navigation
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_ACTIVITY = 1;
    private static final int FRAGMENT_MESSAGE = 2;
    private static final int FRAGMENT_SETTING = 3;

    private static final int FRAGMENT_MY_PROFILE = 4;

    private int mCurrentFragment = FRAGMENT_HOME;

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private ImageView imgAvatar;
    private TextView tvName, tvEmail;

    private BottomNavigationView mbottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bottom Navigation
        mbottomNavigationView = findViewById(R.id.bottomNavigationView);
        mbottomNavigationView.setBackground(null);
        mbottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    mNavigationView.setCheckedItem(R.id.nav_home);
                    mCurrentFragment = FRAGMENT_HOME;

                    break;
                case R.id.activity:
                    replaceFragment(new ActivityFragment());
                    mNavigationView.setCheckedItem(R.id.nav_activity);
                    mCurrentFragment = FRAGMENT_ACTIVITY;
                    break;
/*                case R.id.user:
                    replaceFragment(new UserFragment());
                    break;*/
                case R.id.message:
                    replaceFragment(new MessageFragment());
                    mNavigationView.setCheckedItem(R.id.nav_message);
                    mCurrentFragment = FRAGMENT_MESSAGE;
                    break;
                case R.id.setting:
                    replaceFragment(new SettingFragment());
                    mNavigationView.setCheckedItem(R.id.nav_setting);
                    mCurrentFragment = FRAGMENT_SETTING;
            }
            setTitleToolbar();
            return true;
        }
        );
        //Side Navigation
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mNavigationView = findViewById(R.id.navigation_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,toolbar,R.string.open_drawer, R.string.close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);

        //
        replaceFragment(new HomeFragment());
        mNavigationView.setCheckedItem(R.id.nav_home);
        mbottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
        setTitleToolbar();

        initUI();
        showUserInformation();
    }


    // Tiến hành từng trang
    private void openHomeFragment() {
        if (mCurrentFragment != FRAGMENT_HOME)
        {
            replaceFragment(new HomeFragment());
            mCurrentFragment = FRAGMENT_HOME;
        }
    }
    private void openActivityFragment() {
        if (mCurrentFragment != FRAGMENT_ACTIVITY)
        {
            replaceFragment(new ActivityFragment());
            mCurrentFragment = FRAGMENT_ACTIVITY;
        }
    }
    private void openMessageFragment() {
        if (mCurrentFragment != FRAGMENT_MESSAGE)
        {
            replaceFragment(new MessageFragment());
            mCurrentFragment = FRAGMENT_MESSAGE;
        }
    }
    private void openSettingFragment() {
        if (mCurrentFragment != FRAGMENT_SETTING)
        {
            replaceFragment(new SettingFragment());
            mCurrentFragment = FRAGMENT_SETTING;
        }
    }
    private void openMyProfileFragment() {
        if (mCurrentFragment != FRAGMENT_MY_PROFILE)
        {
            replaceFragment(new MyProfileFragment());
            mCurrentFragment = FRAGMENT_MY_PROFILE;
        }
    }
    // Hàm thay trang
    private void replaceFragment(Fragment fragment)
    {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_customer, fragment);
        fragmentTransaction.commit();
    }

    //Nếu từng phần tử side navigation được chọn
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.nav_home)
        {
            openHomeFragment();
            mbottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
        }
        else if (id == R.id.nav_activity)
        {
            openActivityFragment();
            mbottomNavigationView.getMenu().findItem(R.id.activity).setChecked(true);
        }
        else if (id == R.id.nav_message)
        {
            openMessageFragment();
            mbottomNavigationView.getMenu().findItem(R.id.message).setChecked(true);
        }
        else if (id == R.id.nav_setting)
        {
            openSettingFragment();
            mbottomNavigationView.getMenu().findItem(R.id.setting).setChecked(true);
        }
        else if (id == R.id.nav_infor)
        {
            openMyProfileFragment();
            mbottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
        }
        else if (id == R.id.nav_sign_owner)
        {

        }
        else if (id == R.id.nav_payment_infor)
        {

        }
        else if (id == R.id.nav_sign_out)
        {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Bạn muốn đăng xuất tài khoản ?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Có",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(CustomerMainActivity.this, StartAppActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

            builder1.setNegativeButton(
                    "Không",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }

        setTitleToolbar();
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // Đặt tên toolbar
    private void setTitleToolbar() {
        String title = "";
        switch (mCurrentFragment) {
            case FRAGMENT_HOME:
                title = getString(R.string.nav_home);
                break;
            case FRAGMENT_ACTIVITY:
                title = getString(R.string.nav_activity);
                break;
            case FRAGMENT_MESSAGE:
                title = getString(R.string.nav_message);
                break;
            case FRAGMENT_SETTING:
                title = getString(R.string.nav_setting);
                break;
            case FRAGMENT_MY_PROFILE:
                title = getString(R.string.nav_infor);
                break;
        }
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle(title);
        }
    }
    private void initUI() {
        imgAvatar = mNavigationView.getHeaderView(0).findViewById(R.id.img_avatar);
        tvName = mNavigationView.getHeaderView(0).findViewById(R.id.tv_name_header);
        tvEmail = mNavigationView.getHeaderView(0).findViewById(R.id.tv_email_header);
    }
    private void showUserInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null)
        {
            return;
        }
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();

        if (name == null)
        {
            tvName.setVisibility(View.GONE);
        }
        else
        {
            tvName.setVisibility(View.VISIBLE);
            tvName.setText(name);
        }
        tvEmail.setText(email);
        Glide.with(this).load(photoUrl).error(R.drawable.ic_avatar_default).into(imgAvatar);

    }
}