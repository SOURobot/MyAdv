package com.example.myadv.post;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myadv.R;
import com.example.myadv.databinding.ItemPostDetailsBinding;

public class PostItemDialog extends DialogFragment {
    private static final String ARG_POST = "post";
    private ItemPostDetailsBinding binding;

    public static PostItemDialog newInstance(Post post) {
        PostItemDialog fragment = new PostItemDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARG_POST, post);
        fragment.setArguments(args);
        return fragment;
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
        binding = ItemPostDetailsBinding.inflate(getLayoutInflater(), container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        Post post = (Post) getArguments().getSerializable(ARG_POST);

        assert post != null;
        binding.textDetailHeader.setText(post.getUsername());
        binding.textDetailUsername.setText(post.getUsername());
        binding.textDetailDate.setText(post.getDate());
        binding.textDetailInfo.setText(post.getInfo());
        binding.textDetailContact.setText(post.getContact());
        binding.textDetailCategory.setText(post.getCategory());

        binding.buttonClose.setOnClickListener(v -> dismiss());
    }
}
