package com.example.carrenting.FragmentPages.Customer;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.carrenting.R;
import com.example.carrenting.Service.Map.MapMainActivity;


public class HomeFragment extends Fragment {

    private CardView btnMap;
    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        initUI();
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMapView();
            }
        });
        return  mView;
    }

    private void goToMapView() {
        Intent intent = new Intent(HomeFragment.this.getActivity(), MapMainActivity.class);
        startActivity(intent);
    }

    private void initUI() {
        btnMap =  mView.findViewById(R.id.locate_card);
    }
}