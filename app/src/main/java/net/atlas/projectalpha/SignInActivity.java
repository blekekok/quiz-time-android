package net.atlas.projectalpha;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import net.atlas.projectalpha.api.ApiResponse;
import net.atlas.projectalpha.api.model.LoginViewModel;
import net.atlas.projectalpha.api.model.UserInfoViewModel;

public class SignInActivity extends AppCompatActivity {
    private EditText edtSignInId, edtSignInPass;
    private Button btnSignIn;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtSignInId = findViewById(R.id.edtSignInId);
        edtSignInPass = findViewById(R.id.edtSignInPass);
        btnSignIn = findViewById(R.id.btnSignIn);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.login(edtSignInId.getText().toString(), edtSignInPass.getText().toString(), SignInActivity.this);
            }
        });

        loginViewModel.getLoginResponse().observe(this, response -> {
            if (response == null) {
                Toast.makeText(SignInActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(SignInActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent();
            intent.setClass(SignInActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}