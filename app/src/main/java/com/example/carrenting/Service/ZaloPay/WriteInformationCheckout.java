package com.example.carrenting.Service.ZaloPay;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carrenting.R;

public class WriteInformationCheckout extends AppCompatActivity {
    Button btn_request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_infor_booking_car);

        btn_request=findViewById(R.id.btn_requestbooking);
        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
