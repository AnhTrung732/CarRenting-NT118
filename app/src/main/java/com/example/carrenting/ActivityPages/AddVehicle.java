package com.example.carrenting.ActivityPages;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddVehicle extends AppCompatActivity {

    private String documentId;
    private Uri mImageURI = null;
    private EditText vehicle_name, vehicle_seats, vehicle_price, vehicle_owner, vehicle_number, vehicle_img;
    private CheckBox vehicle_available;
    private Button btnAdd;
    private ImageView vehicle_imgView;
    private FirebaseFirestore dtb_vehicle;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    public void findViewByIds() {
        vehicle_name = findViewById(R.id.et_name);
        vehicle_seats = findViewById(R.id.et_seats);
        vehicle_price = findViewById(R.id.et_price);
        vehicle_owner = findViewById(R.id.et_owner);
        vehicle_number = findViewById(R.id.et_number);
        /*        vehicle_img = findViewById(R.id.et_image);*/
        vehicle_available = findViewById(R.id.cb_availability);
        vehicle_imgView = findViewById(R.id.img_view);
        btnAdd = findViewById(R.id.btn_add);
    }

    private ActivityResultLauncher<String> pickImageFromGallery = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri uri) {
            mImageURI = uri;
            Picasso.get().load(uri).into(vehicle_imgView);
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        findViewByIds();

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();


        vehicle_imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGallery.launch("image/*");
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vehicleName = vehicle_name.getText().toString();
                String vehicleSeats = vehicle_seats.getText().toString();
                String vehiclePrice = vehicle_price.getText().toString();
                String vehicleOwner = vehicle_owner.getText().toString();
                String vehicleNumber = vehicle_number.getText().toString();
                String availability = vehicle_available.isChecked() ? "available" : "unavailable";

                dtb_vehicle = FirebaseFirestore.getInstance();
                Map<String, Object> vehicle = new HashMap<>();
                vehicle.put("name", vehicleName);
                vehicle.put("seats", vehicleSeats);
                vehicle.put("price", vehiclePrice);
                vehicle.put("owner", vehicleOwner);
                vehicle.put("number", vehicleNumber);
                vehicle.put("availability", availability);
                dtb_vehicle.collection("vehicles")
                        .add(vehicle)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                documentId = documentReference.getId();
                                uploadImage();
                                Toast.makeText(AddVehicle.this, "Vehicle added successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Error adding document", e);
                            }
                        });
            }
        });
    }

    private void uploadImage() {
        if (mImageURI != null) {
            try {
                final Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mImageURI);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageInBytes = baos.toByteArray();

                StorageReference imageRef = storageRef.child("vehicles").child(documentId + ".jpg");
                UploadTask uploadTask = imageRef.putBytes(imageInBytes);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(AddVehicle.this, "Upload failed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(AddVehicle.this, "Upload successful", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException e) {
                Log.e("Loi", "loi", e);
            }
        }
    }


            private void uploadImageToFirebase() {
        if (mImageURI != null) {
            // Create a file name for the image
            final String fileName = "vehicle_" + System.currentTimeMillis();

            // Create a storage reference
            final StorageReference imageRef = storageRef.child("vehicles/" + fileName);

            // Upload the image to Firebase
            imageRef.putFile(mImageURI)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get the image URL from Firebase
                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();

                                    // update the image URL in Firestore
                                    Map<String, Object> vehicle = new HashMap<>();
                                    vehicle.put("vehicle_image", imageUrl);
                                    dtb_vehicle.collection("Vehicle").document(documentId).update(vehicle);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Upload Image", e.getMessage());
                        }
                    });
        }
    }

}