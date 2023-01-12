package com.example.carrenting.Service;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carrenting.R;

import org.checkerframework.common.subtyping.qual.Bottom;

public class ConfirmAndPayment extends AppCompatActivity {
    Button btn_payment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_payment);
        btn_payment = findViewById(R.id.btn_Payment);


    }
}
