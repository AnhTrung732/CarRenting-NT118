package com.example.carrenting.FragmentPages.Owner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carrenting.Adapter.OwnerVehicleAdapter;
import com.example.carrenting.Model.User;
import com.example.carrenting.Model.Vehicle;
import com.example.carrenting.Model.onClickInterface;
import com.example.carrenting.R;
import com.example.carrenting.Service.Vehicle.AddVehicleActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OwnerVehicleFragment extends Fragment {

    ImageView imageView;
    DocumentReference imageRef;
    RecyclerView recyclerView;
    ArrayList<Vehicle> vehicles;
    OwnerVehicleAdapter adapter;

    FirebaseFirestore dtb_vehicle;
    private FirebaseUser firebaseUser;
    private User user = new User();
    private onClickInterface onclickInterface;

    ProgressDialog progressDialog;
    private View view;
    private Button btnAdd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.owner_fragment_vehicle, container, false);
        btnAdd = (Button) view.findViewById(R.id.btn_add);
        onclickInterface = new onClickInterface() {
            @Override
            public void setClick(int position) {
                vehicles.indexOf(position);
                Log.d("Position: ","Position is " + position);
                adapter.notifyDataSetChanged();
            }
        };
        btnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getActivity(), AddVehicleActivity.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Đang lấy dữ liệu...");
        progressDialog.show();

        recyclerView = view.findViewById(R.id.vehicle_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        dtb_vehicle = FirebaseFirestore.getInstance();
        vehicles = new ArrayList<Vehicle>();
        adapter = new OwnerVehicleAdapter(OwnerVehicleFragment.this, vehicles, onclickInterface);
        recyclerView.setAdapter(adapter);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user.setUser_id(firebaseUser.getUid());

        try {
            EventChangeListener();
            //Toast.makeText(getContext(),"Catching...", Toast.LENGTH_LONG).show();
        } catch (Exception exception){
            Toast.makeText(getContext(), "Exception", Toast.LENGTH_LONG).show();
        }
        progressDialog.cancel();
        return view;
    }

    private void LoadImage(String docId) {
        if (docId == null) {
            Log.e("LoadImage", "docId is null, returning");
            return;
        }
        // Reference to the image document in Firestore
        imageRef = FirebaseFirestore.getInstance().collection("Image").document(docId);

        // Get the image document
        imageRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    // Get the image URL from the document
                    String imageUrl = documentSnapshot.getString("Image");

                    // Load the image into the ImageView
                    Glide.with(getView()).load(imageUrl).into(imageView);
                }else{
                    Log.e("LoadImage", "documentSnapshot is null or does not exists");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("LoadImage", "Error: " + e.getMessage());
            }
        });
    }
    private void EventChangeListener()
    {
        dtb_vehicle.collection("Vehicles")
                .whereEqualTo("provider_id", user.getUser_id())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Vehicle temp = new Vehicle();
                                temp.setVehicle_id(document.getId());
                                temp.setVehicle_name(document.get("vehicle_name").toString());
                                temp.setVehicle_price(document.get("vehicle_price").toString());
                                temp.setVehicle_imageURL(document.get("vehicle_imageURL").toString());
                                temp.setProvider_name(document.get("provider_name").toString());
                                vehicles.add(temp);
                                adapter.notifyDataSetChanged();
/*                                progressDialog.cancel();*/
                            }
                        } else {
                            Toast.makeText(getContext(), "Không thể lấy thông tin xe", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}