package com.example.myadv.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myadv.R;
import com.example.myadv.databases.PostsDBHelper;
import com.example.myadv.databinding.FragmentCreateBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class CreateFragment extends DialogFragment {

    private FragmentCreateBinding binding;
    private StaffFragment.UsernameProvider usernameProvider;
    private PostsDBHelper helper;

    public void setUsernameProvider(StaffFragment.UsernameProvider provider) {
        this.usernameProvider = provider;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentDialog);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateBinding.inflate(getLayoutInflater(), container, false);

        String[] items = getResources().getStringArray(R.array.post_categories);;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item, items);
        binding.spinnerCategory.setAdapter(adapter);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        helper = new PostsDBHelper(requireContext());

        binding.buttonPublish.setOnClickListener(v -> publishPost());
        binding.buttonClose.setOnClickListener(v -> dismiss());
    }

    protected void publishPost() {
        String username = usernameProvider.getUsername();
        @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
        String header = binding.textHeader.getText().toString().trim();
        String info = binding.textInfo.getText().toString().trim();
        String contact = binding.textContact.getText().toString().trim();
        String category = binding.spinnerCategory.getSelectedItem().toString().trim();

        if (checkFields()) {
            showToast(R.string.empty_fields);
            return;
        }

        long result = helper.addPost(username, date, header, info, contact, category);

        if (result != -1) {
            showToast(R.string.post_published);
            clearFields();
        } else {
            showToast(R.string.save_error);
        }
    }

    private boolean checkFields() {
        return binding.textHeader.getText().toString().trim().isEmpty() ||
                binding.textInfo.getText().toString().trim().isEmpty() ||
                binding.textContact.getText().toString().trim().isEmpty() ||
                binding.spinnerCategory.getSelectedItem().toString().equals("Выберите категорию");
    }

    private void showToast(int message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void clearFields() {
        binding.textHeader.setText("");
        binding.textInfo.setText("");
        binding.textContact.setText("");
        binding.spinnerCategory.setSelection(0);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);
            dialog.getWindow().setGravity(Gravity.CENTER);
        }
    }

    @Override
    public void onDestroyView() {
        if (helper != null) {
            helper.close();
        }
        binding = null;
        super.onDestroyView();
    }
}
