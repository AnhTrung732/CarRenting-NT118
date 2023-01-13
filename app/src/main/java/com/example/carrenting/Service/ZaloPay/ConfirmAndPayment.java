package com.example.carrenting.Service.ZaloPay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carrenting.R;

import org.checkerframework.common.subtyping.qual.Bottom;

public class ConfirmAndPayment extends AppCompatActivity {
    Button btn_payment;
    Button btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_payment);

        //animation
        overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_right);

        btn_payment = findViewById(R.id.btn_Payment);
        btn_back=findViewById(R.id.back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Writeinfor=new Intent(ConfirmAndPayment.this,Notification.class);
                startActivity(Writeinfor);

            }
        });
    }
}
