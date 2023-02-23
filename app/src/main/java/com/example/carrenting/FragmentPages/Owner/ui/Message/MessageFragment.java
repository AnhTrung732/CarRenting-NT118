package com.example.carrenting.FragmentPages.Owner.ui.Message;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.carrenting.databinding.CustomerFragmentMessageBinding;

public class MessageFragment extends Fragment {

    private CustomerFragmentMessageBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MessageViewModel VehicleViewModel =
                new ViewModelProvider(this).get(MessageViewModel.class);

        binding = CustomerFragmentMessageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.tvTitle;
        VehicleViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}