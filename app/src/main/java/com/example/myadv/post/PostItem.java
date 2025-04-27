package com.example.myadv.post;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import androidx.fragment.app.FragmentManager;

import com.example.myadv.R;
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
        LayoutInflater.from(context).inflate(R.layout.item_post, this, true);
        setOnClickListener(v -> showPostDetails());
    }

    public void setPost(Post post, FragmentManager fm) {
        this.post = post;
        this.fragmentManager = fm;
        binding.textItemHeader.setText(post.getHeader());
        binding.textItemDate.setText(post.getDate());
        String category = post.getCategory();
        switch (category) {
            case "Official":
                binding.viewCategory.setBackgroundColor(0x1560BD);
                break;
            case "Events":
                binding.viewCategory.setBackgroundColor(0xF80000);
                break;
            case "Hobbies":
                binding.viewCategory.setBackgroundColor(0xFFC600);
                break;
            default:
                binding.viewCategory.setBackgroundColor(0x228B22);
                break;
        }
    }

    private void showPostDetails() {
        PostItemDialog dialog = PostItemDialog.newInstance(post);
        dialog.show(fragmentManager, "PostDetailsDialog");
    }
}
