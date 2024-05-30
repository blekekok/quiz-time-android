package net.atlas.projectalpha;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    private EditText edtSignUpId, edtSignUpEmail, edtSignUpPass, edtSignUpConfPass;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtSignUpId = findViewById(R.id.edtSignUpId);
        edtSignUpEmail = findViewById(R.id.edtSignUpEmail);
        edtSignUpPass = findViewById(R.id.edtSignUpPass);
        edtSignUpConfPass = findViewById(R.id.edtSignUpConfPass);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign up logic here

                Intent intent = new Intent();
                intent.setClass(SignUpActivity.this, MainActivity.class);

                startActivity(intent);
            }
        });
    }
}