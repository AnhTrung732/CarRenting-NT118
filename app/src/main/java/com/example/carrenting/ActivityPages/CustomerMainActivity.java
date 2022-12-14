package com.example.carrenting.ActivityPages;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import com.example.carrenting.FragmentPages.Customer.CustomerActivityFragment;
import com.example.carrenting.FragmentPages.Customer.CustomerHomeFragment;
import com.example.carrenting.FragmentPages.Customer.CustomerMessageFragment;
import com.example.carrenting.FragmentPages.Customer.CustomerSettingFragment;
import com.example.carrenting.FragmentPages.Customer.UserInfor.MyProfileFragment;
import com.example.carrenting.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;


public class CustomerMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int MY_REQUEST_CODE = 10;
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
    final private MyProfileFragment myProfileFragment = new MyProfileFragment();

    private BottomNavigationView mbottomNavigationView;


    final private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK)
                    {
                        Intent intent = result.getData();
                        if (intent == null)
                        {
                            return;
                        }
                        Uri uri = intent.getData();
                        myProfileFragment.setUri(uri);
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            myProfileFragment.setBitmapImageView(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

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
                    replaceFragment(new CustomerHomeFragment());
                    mNavigationView.setCheckedItem(R.id.nav_home);
                    mCurrentFragment = FRAGMENT_HOME;

                    break;
                case R.id.activity:
                    replaceFragment(new CustomerActivityFragment());
                    mNavigationView.setCheckedItem(R.id.nav_activity);
                    mCurrentFragment = FRAGMENT_ACTIVITY;
                    break;
/*                case R.id.user:
                    replaceFragment(new UserFragment());
                    break;*/
                case R.id.message:
                    replaceFragment(new CustomerMessageFragment());
                    mNavigationView.setCheckedItem(R.id.nav_message);
                    mCurrentFragment = FRAGMENT_MESSAGE;
                    break;
                case R.id.setting:
                    replaceFragment(new CustomerSettingFragment());
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
        replaceFragment(new CustomerHomeFragment());
        mNavigationView.setCheckedItem(R.id.nav_home);
        mbottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
        setTitleToolbar();

        initUI();
        showUserInformation();
    }


    // Ti???n h??nh t???ng trang

    private void openHomeFragment() {
        if (mCurrentFragment != FRAGMENT_HOME)
        {
            replaceFragment(new CustomerHomeFragment());
            mCurrentFragment = FRAGMENT_HOME;
        }
    }
    private void openActivityFragment() {
        if (mCurrentFragment != FRAGMENT_ACTIVITY)
        {
            replaceFragment(new CustomerActivityFragment());
            mCurrentFragment = FRAGMENT_ACTIVITY;
        }
    }
    private void openMessageFragment() {
        if (mCurrentFragment != FRAGMENT_MESSAGE)
        {
            replaceFragment(new CustomerMessageFragment());
            mCurrentFragment = FRAGMENT_MESSAGE;
        }
    }
    private void openSettingFragment() {
        if (mCurrentFragment != FRAGMENT_SETTING)
        {
            replaceFragment(new CustomerSettingFragment());
            mCurrentFragment = FRAGMENT_SETTING;
        }
    }
    private void openMyProfileFragment() {
        if (mCurrentFragment != FRAGMENT_MY_PROFILE)
        {
            replaceFragment(myProfileFragment);
            mCurrentFragment = FRAGMENT_MY_PROFILE;
        }
    }
    // H??m thay trang

    private void replaceFragment(Fragment fragment)
    {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_customer, fragment);
        fragmentTransaction.commit();
    }

    //N???u t???ng ph???n t??? side navigation ???????c ch???n

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
            builder1.setMessage("B???n mu???n ????ng xu???t t??i kho???n ?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "C??",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(CustomerMainActivity.this, StartAppActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

            builder1.setNegativeButton(
                    "Kh??ng",
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


    // ?????t t??n toolbar

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
    public void showUserInformation() {
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE)
        {
            if (grantResults.length >  0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                openGallery();
            }

        }
    }
    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }


}