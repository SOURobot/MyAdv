package com.example.myadv.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myadv.databases.PostsDBHelper;
import com.example.myadv.databinding.FragmentStaffBinding;
import com.example.myadv.post.Post;
import com.example.myadv.post.PostItem;

public class StaffFragment extends Fragment {
    public StaffFragment() {}

    private PostsDBHelper dbHelper;
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

    private void loadUserPosts() {
        binding.postsHolder.removeAllViews();
        String username = usernameProvider.getUsername();

        dbHelper = new PostsDBHelper(requireContext());

        Cursor cursor = dbHelper.getUserPosts(username);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Post post = new Post(
                        cursor.getInt(cursor.getColumnIndex(PostsDBHelper.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(PostsDBHelper.COLUMN_USERNAME)),
                        cursor.getString(cursor.getColumnIndex(PostsDBHelper.COLUMN_DATE)),
                        cursor.getString(cursor.getColumnIndex(PostsDBHelper.COLUMN_HEADER)),
                        cursor.getString(cursor.getColumnIndex(PostsDBHelper.COLUMN_INFO)),
                        cursor.getString(cursor.getColumnIndex(PostsDBHelper.COLUMN_CONTACT)),
                        cursor.getString(cursor.getColumnIndex(PostsDBHelper.COLUMN_CATEGORY))
                );

                PostItem postView = new PostItem(getContext());
                postView.setPost(post, getParentFragmentManager());

                binding.postsHolder.addView(postView);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUserPosts();

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