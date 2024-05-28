package net.atlas.projectalpha;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import net.atlas.projectalpha.databinding.ActivityMainBinding;

public class SignInActivity extends AppCompatActivity {
    private EditText edtSignInId, edtSignInPass;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtSignInId = findViewById(R.id.edtSignInId);
        edtSignInPass = findViewById(R.id.edtSignInPass);
        btnSignIn = findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SignInActivity.this, MainActivity.class);

                startActivity(intent);
            }
        });
    }
}