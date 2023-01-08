package com.example.carrenting.FragmentPages.Customer.UserInfor;

import static android.content.ContentValues.TAG;
import static com.example.carrenting.ActivityPages.CustomerMainActivity.MY_REQUEST_CODE;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.carrenting.ActivityPages.CustomerMainActivity;
import com.example.carrenting.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class MyProfileFragment extends Fragment {

    private View mView;
    private ImageView imgAvatar;
    private EditText dataName, dataPhone, dataEmail, dataAdress, dataBankNumber;
    private Button btnUpdateProfile;
    private Uri mUri;
    private CustomerMainActivity mMainActivity;
    private ProgressDialog progressDialog;

    public void setUri(Uri mUri) {
        this.mUri = mUri;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_my_profile, container, false);
        initUI();
        mMainActivity = (CustomerMainActivity) getActivity();
        progressDialog = new ProgressDialog(getActivity());
        setUserInformation();
        initListener();


        return mView;
    }

    private void initListener()
    {
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickUpdateProfile();
            }
        });
    }

    private void onClickUpdateProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null)
        {
            return;
        }

        String strFullname = dataName.getText().toString().trim();
        progressDialog.show();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(strFullname)
                .setPhotoUri(mUri)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Upload profile successfull", Toast.LENGTH_LONG).show();
                            mMainActivity.showUserInformation();
                        }
                    }
                });

    }


    private void onClickRequestPermission()
    {
        if (mMainActivity == null)
        {
            return;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            mMainActivity.openGallery();
            return;
        }
        if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            mMainActivity.openGallery();
        }
        else
        {
            String [] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            getActivity().requestPermissions(permission, MY_REQUEST_CODE);
        }
    }

    private void setUserInformation()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null)
        {
            return;
        }
        if (user.getDisplayName() != null)
        {
            dataName.setText(user.getDisplayName());
        }
        dataEmail.setText(user.getEmail());
        dataPhone.setText(user.getPhoneNumber());
        Glide.with(getActivity()).load(user.getPhotoUrl()).error(R.drawable.ic_avatar_default).into(imgAvatar);
    }

    private void initUI() {
        imgAvatar = mView.findViewById(R.id.img_avatar);
        dataName = mView.findViewById(R.id.data_name);
        dataPhone = mView.findViewById(R.id.data_phone);
        dataEmail = mView.findViewById(R.id.data_email);
        dataAdress = mView.findViewById(R.id.data_address);
        dataBankNumber = mView.findViewById(R.id.data_bank_number);
        btnUpdateProfile = mView.findViewById(R.id.btn_update_profile);
    }
    public void setBitmapImageView(Bitmap bitmapImageView)
    {
        imgAvatar.setImageBitmap(bitmapImageView);
    }
}