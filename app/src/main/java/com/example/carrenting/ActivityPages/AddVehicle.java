package com.example.carrenting.ActivityPages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.carrenting.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class AddVehicle extends AppCompatActivity {

    private EditText vehicle_name, vehicle_seats, vehicle_price, vehicle_owner, vehicle_number, vehicle_img;
    private CheckBox vehicle_available;
    private Button btnAdd, btnLoad;
    private ImageView vehicle_imgView;
    private FirebaseFirestore dtb_vehicle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        findViewByIds();

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!vehicle_img.getText().toString().equals("")){
                    Picasso.get().load(vehicle_img.getText().toString()).into(vehicle_imgView);
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dtb_vehicle = FirebaseFirestore.getInstance();

                String vehicleName = vehicle_name.getText().toString();
                String vehicleSeats = vehicle_seats.getText().toString();
                String vehiclePrice = vehicle_price.getText().toString();
                String vehicleOwner = vehicle_owner.getText().toString();
                String vehicleNumber = vehicle_number.getText().toString();
                String vehicleImg = vehicle_img.getText().toString();
                String availability;
                if(vehicle_available.isChecked())
                {
                    availability = "available";
                }
                else
                {
                    availability = "unavailable";
                }
                Map<String,Object> vehicle = new HashMap<>( );
                vehicle.put("vehicle_name", vehicleName);
                vehicle.put("vehicle_seats", vehicleSeats);
                vehicle.put("vehicle_price", vehiclePrice);
                vehicle.put("vehicle_owner", vehicleOwner);
                vehicle.put("vehicle_number", vehicleNumber);
                vehicle.put("vehicle_imageURL", vehicleImg);
                vehicle.put("vehicle_available", availability);


                dtb_vehicle.collection("Vehicle")
                        .add(vehicle)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(AddVehicle.this, "Thêm phương tiện mới thành công", Toast.LENGTH_SHORT);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddVehicle.this, "Thêm phương tiện mới không thành công", Toast.LENGTH_SHORT);
                            }
                        });
            }
        });
    }

    public void findViewByIds()
    {
        vehicle_name = findViewById(R.id.et_name);
        vehicle_seats = findViewById(R.id.et_seats);
        vehicle_price = findViewById(R.id.et_price);
        vehicle_owner = findViewById(R.id.et_owner);
        vehicle_number = findViewById(R.id.et_number);
        vehicle_img = findViewById(R.id.et_image);
        vehicle_available = findViewById(R.id.cb_availability);
        vehicle_imgView = findViewById(R.id.img_view);
        btnAdd = findViewById(R.id.btn_add);
        btnLoad = findViewById(R.id.btn_load);
    }
}