package com.example.myadv.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myadv.databinding.FragmentStaffBinding;

public class StaffFragment extends Fragment {
    public StaffFragment() {}

    private UsernameProvider usernameProvider;
    private FragmentStaffBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof UsernameProvider) {
            this.usernameProvider = (UsernameProvider) context;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStaffBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.fabCreate.setOnClickListener(v -> showCustomDialog());

    }

    private void showCustomDialog() {
        CreateFragment createFragment = new CreateFragment();
        createFragment.setUsernameProvider(this.usernameProvider);
        createFragment.show(getChildFragmentManager(), "create_dialog");
    }

    public interface UsernameProvider {
        String getUsername();
    }
}