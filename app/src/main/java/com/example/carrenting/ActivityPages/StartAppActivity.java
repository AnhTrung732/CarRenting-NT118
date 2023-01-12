package com.example.carrenting.ActivityPages;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carrenting.Model.User;
import com.example.carrenting.Model.UserClient;
import com.example.carrenting.R;
import com.example.carrenting.Service.UserAuthentication.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class StartAppActivity extends AppCompatActivity {
    private Button btn_startApp;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_app);

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                nextActivity();
//            }
//        }, 2000);

        btn_startApp = findViewById(R.id.btn_startApp);

        btn_startApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity();
            }
        });

    }

    private void nextActivity() {

        overridePendingTransition(R.anim.anim_in_right,R.anim.anim_out_left);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            //Chưa login
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else
        {
            // Da login
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .build();
            db.setFirestoreSettings(settings);

            DocumentReference userRef = db.collection(getString(R.string.collection_users))
                    .document(user.getUid());

            userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        Log.d(TAG, "onComplete: successfully set the user client.");
                        User user = task.getResult().toObject(User.class);
                        ((UserClient)(getApplicationContext())).setUser(user);
                    }
                }
            });
            Intent intent = new Intent(this, CustomerMainActivity.class);
            startActivity(intent);
        }

        finish();

    }
}
