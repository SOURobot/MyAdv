package com.example.myadv;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myadv.databases.UsersDBHelper;
import com.example.myadv.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private UsersDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        helper = new UsersDBHelper(this);

        binding.buttonSign.setOnClickListener(v -> signIn());
        binding.buttonRegister.setOnClickListener(v -> register());
    }

    protected void signIn() {
        String login = binding.textLogin.getText().toString().trim();
        String password = binding.textPassword.getText().toString().trim();

        if (login.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean pairValid = helper.checkUser(login, password);

        if (pairValid) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("Login", login);
            intent.putExtra("Password", password);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Неверные данные", Toast.LENGTH_SHORT).show();
        }
    }

    protected void register() {
        String login = binding.textLogin.getText().toString().trim();
        String password = binding.textPassword.getText().toString().trim();

        if (login.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        if (helper.ifUserExists(login)) {
            Toast.makeText(this, "Данный пользователь уже существует", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = helper.addUser(login, password);

        if (result != -1) {
            Toast.makeText(this, "Данные успешно сохранены", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(this, "Ошибка при сохранении данных", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        binding.textLogin.setText("");
        binding.textPassword.setText("");
    }

    @Override
    protected void onDestroy() {
        helper.close();
        super.onDestroy();
    }
}
