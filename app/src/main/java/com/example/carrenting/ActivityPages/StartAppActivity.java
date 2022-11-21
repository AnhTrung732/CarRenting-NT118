package com.example.carrenting.ActivityPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carrenting.R;
import com.example.carrenting.Service.UserAuthentication.LoginActivity;
import com.example.carrenting.Service.UserAuthentication.RegisterActivity;

public class StartAppActivity extends AppCompatActivity {
    private Button btn_signIn, btn_register;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_app);

        btn_signIn = findViewById(R.id.btn_signIn_startapp);
        btn_register = findViewById(R.id.btn_register_startapp);

        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartAppActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartAppActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
