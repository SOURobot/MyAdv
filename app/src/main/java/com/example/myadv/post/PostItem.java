package com.example.myadv.post;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import androidx.fragment.app.FragmentManager;

import com.example.myadv.databinding.ItemPostBinding;

public class PostItem extends RelativeLayout {

    private ItemPostBinding binding;
    private Post post;
    private FragmentManager fragmentManager;

    public PostItem(Context context) {
        super(context);
        init(context);
    }

    public PostItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        binding = ItemPostBinding.inflate(LayoutInflater.from(context), this, true);
        setOnClickListener(v -> showPostDetails());
    }

    public void setPost(Post post, FragmentManager fm) {
        this.post = post;
        this.fragmentManager = fm;
        binding.textItemHeader.setText(post.getHeader());
        binding.textItemDate.setText(post.getDate());
        String category = post.getCategory();
        switch (category) {
            case "Официальное":
                binding.viewCategory.setBackgroundColor(0xFF1560BD);
                binding.viewCategory.invalidate();
                break;
            case "Событие":
                binding.viewCategory.setBackgroundColor(0xFFF80000);
                binding.viewCategory.invalidate();
                break;
            case "Хобби":
                binding.viewCategory.setBackgroundColor(0xFFFFC600);
                binding.viewCategory.invalidate();
                break;
            default:
                binding.viewCategory.setBackgroundColor(0xFF228B22);
                binding.viewCategory.invalidate();
                break;
        }
    }

    private void showPostDetails() {
        PostItemDialog dialog = PostItemDialog.newInstance(post);
        dialog.show(fragmentManager, "PostDetailsDialog");
    }
}
