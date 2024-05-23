package net.atlas.projectalpha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import net.atlas.projectalpha.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'projectalpha' library on application startup.
    static {
        System.loadLibrary("projectalpha");
    }

    private ActivityMainBinding binding;
    private ImageView ivLogo;
    private Button btnSignIn, btnSignUp;
    private EditText edtSearch;
//    private ListView lvQuizList = findViewById(R.id.lvQuizList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Logo
        ivLogo = findViewById(R.id.ivLogo);
        ivLogo.setImageResource(R.drawable.ic_launcher_background);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        edtSearch = findViewById(R.id.edtSearch);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SignInActivity.class);

                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SignUpActivity.class);

                startActivity(intent);
            }
        });

//        // Example of a call to a native method
//        TextView tv = binding.sampleText;
//        tv.setText(stringFromJNI());

        /*for (int i = 0; i < 5; i++) {
            View itemView = getLayoutInflater().inflate(R.layout.quiz_item_layout, null);

            TextView quizTitle = itemView.findViewById(R.id.titleText);
            quizTitle.setText("TEST" + i);

            quizList.addView(itemView);
        }*/
    }

    /**
     * A native method that is implemented by the 'projectalpha' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}

//public class QuizItem {
//    private String title;
//    private String description;
//    private int category;
//}