package com.example.carrenting.ActivityPages;

import static java.lang.Integer.parseInt;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.carrenting.Model.User;
import com.example.carrenting.R;
import com.example.carrenting.Service.UserAuthentication.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.io.IOException;


public class CustomerMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int AVATAR_REQUEST_CODE = 99;
    public static final int FRONT_CICARD_REQUEST_CODE = 100;
    public static final int BEHIND_CICARD_REQUEST_CODE = 101;
    //Side Navigation
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_ACTIVITY = 1;
    private static final int FRAGMENT_MESSAGE = 2;

    private static final int FRAGMENT_MY_PROFILE = 4;
    private static final int FRAGMENT_OWNER_STATE = 5;


    private int mCurrentFragment = FRAGMENT_HOME;

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private ImageView imgAvatar;
    private TextView tvName, tvEmail;
    final private MyProfileFragment myProfileFragment = new MyProfileFragment();

    private BottomNavigationView mbottomNavigationView;
    private FirebaseFirestore mDb;
    User mUser;
    private CustomerMainActivity mMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDb = FirebaseFirestore.getInstance();
        mMainActivity = this;

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


    // Tiến hành từng trang

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
    private void openMyProfileFragment() {
        if (mCurrentFragment != FRAGMENT_MY_PROFILE)
        {
            replaceFragment(myProfileFragment);
            mCurrentFragment = FRAGMENT_MY_PROFILE;
        }
    }
    private void openOwnerStateFragment() {
        if (mCurrentFragment != FRAGMENT_OWNER_STATE)
        {
            Intent i = new Intent(CustomerMainActivity.this, OwnerMainActivity.class);
            startActivity(i);
            ((Activity) CustomerMainActivity.this).overridePendingTransition(0, 0);
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

        else if (id == R.id.nav_infor)
        {

            openMyProfileFragment();
            mbottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
        }
        else if (id == R.id.nav_sign_owner)
        {
            openOwnerStateFragment();
            mbottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
        }
        else if (id == R.id.nav_security_setup)
        {

        }
        else if (id == R.id.nav_notification_setup)
        {

        }
        else if (id == R.id.nav_delete_account)
        {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Bạn thực sự muốn xóa tài khoản ?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Có",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                user.delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(CustomerMainActivity.this, "Tài khoản đã bị xóa.", Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(CustomerMainActivity.this, LoginActivity.class);
                                                    startActivity(intent);
                                                    ((Activity) CustomerMainActivity.this).overridePendingTransition(0, 0);
                                                } else {
                                                    Toast.makeText(CustomerMainActivity.this, "Xóa tài khoản không thành công", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                            }
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
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .build();
        mDb.setFirestoreSettings(settings);
        DocumentReference newUserRef = mDb
                .collection(getString(R.string.collection_users))
                .document(FirebaseAuth.getInstance().getUid());
        newUserRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                mUser = documentSnapshot.toObject(User.class);
            }
        }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (mUser == null)
                {
                    return;
                }
                String name = mUser.getUsername();
                String email = mUser.getEmail();
                if (mUser.getAvatarURL() != null)
                {
                    Uri photoUrl = Uri.parse(mUser.getAvatarURL());
                    Glide.with(mMainActivity).load(photoUrl).error(R.drawable.ic_avatar_default).into(imgAvatar);
                }

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

            }
        });

    }
    public void onClickRequestPermission(int CodeRequest)
    {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            this.openGallery();
            return;
        }
        if (this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            this.openGallery();
        }
        else
        {
            String [] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            this.requestPermissions(permission, CodeRequest);
        }
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

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
                        if (myProfileFragment.getFlag_image() == 0)
                        {
                            myProfileFragment.setAvatarUri(uri);
                            Glide.with(CustomerMainActivity.this).load(uri).error(R.drawable.ic_avatar_default).into(imgAvatar);
                            Glide.with(CustomerMainActivity.this).load(uri).into(myProfileFragment.getImgAvatar());
                        }
                        else if (myProfileFragment.getFlag_image() == 1)
                        {
                            myProfileFragment.setFrontUri(uri);
                            Glide.with(CustomerMainActivity.this).load(uri).into(myProfileFragment.getIvFrontCiCard());
                        }
                        else if (myProfileFragment.getFlag_image() == 2)
                        {
                            myProfileFragment.setBehindUri(uri);
                            Glide.with(CustomerMainActivity.this).load(uri).into(myProfileFragment.getIvBehindCiCard());
                        }
                    }
                }
            }
    );

    public String getFileExtension(Uri uri)
    {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

}