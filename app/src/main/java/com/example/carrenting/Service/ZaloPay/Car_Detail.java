package com.example.carrenting.Service.ZaloPay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carrenting.R;


public class Car_Detail extends AppCompatActivity {
    Button btn_Chon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_car);

        btn_Chon=findViewById(R.id.btn_chon);
        btn_Chon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it1=new Intent(Car_Detail.this,WriteInformationCheckout.class);
            }
        });
    }
}
