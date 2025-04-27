package com.example.myadv;

import static android.text.TextUtils.isEmpty;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.myadv.databinding.ActivityMainBinding;
import com.example.myadv.fragments.StaffFragment;
import com.example.myadv.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements StaffFragment.UsernameProvider {

    private ActivityMainBinding binding;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        Intent intent = getIntent();
        username = intent.getStringExtra("Login");

        if (!isEmpty(username) && username.length() >= 20) {
            username = username.substring(0, 8) + "...";
        }
        binding.textUsername.setText(username);

        binding.fabLogout.setOnClickListener(v -> {
            Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent1);
        });
    }

    @Override
    public String getUsername() {
        if (username != null) {
            return username;
        } else {
            return "Ошибка";
        }
    }
}