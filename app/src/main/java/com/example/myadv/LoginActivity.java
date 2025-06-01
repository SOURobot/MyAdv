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

        if (!checkFields(login, password)){
            return;
        }

        if (helper.checkUser(login, password)) {
            startApp(login);
        } else {
            showToast(R.string.bad_input);
        }
    }

    protected void register() {
        String login = binding.textLogin.getText().toString().trim();
        String password = binding.textPassword.getText().toString().trim();

        if (!checkFields(login, password)){
            return;
        }
        if (!checkLogin(login)){
            return;
        }
        if (!checkPassword(password)){
            return;
        }

        if (helper.ifUserExists(login)) {
            showToast(R.string.user_exists);
            return;
        }

        long result = helper.addUser(login, password);
        if (result != -1) {
            showToast(R.string.registration_success);
            clearFields();
        } else {
            showToast(R.string.save_error);
        }
    }

    private boolean checkFields(String login, String password) {
        if (login.isEmpty() || password.isEmpty()) {
            showToast(R.string.empty_fields);
            return false;
        }
        return true;
    }

    private boolean checkLogin(String login) {
        return login.matches("[a-zA-Z0-9_]+");
    }

    private boolean checkPassword(String password) {
        return password.length() >= 6;
    }

    private void startApp(String login) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("Login", login);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void showToast(int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
