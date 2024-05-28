package net.atlas.projectalpha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import net.atlas.projectalpha.databinding.ActivityMainBinding;
import net.atlas.projectalpha.model.QuizItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'projectalpha' library on application startup.
    static {
        System.loadLibrary("projectalpha");
    }

    private ActivityMainBinding binding;
    private Button btnMainSignIn, btnMainSignUp;
    private EditText edtSearch;
    private ListView lvQuizList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        btnMainSignIn = findViewById(R.id.btnMainSignIn);
        btnMainSignUp = findViewById(R.id.btnMainSignUp);
        edtSearch = findViewById(R.id.edtSearch);
        lvQuizList = findViewById(R.id.lvQuizList);

        // Sign In Button
        btnMainSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SignInActivity.class);

                startActivity(intent);
            }
        });

        // Sign Up Button
        btnMainSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SignUpActivity.class);

                startActivity(intent);
            }
        });

        // Quiz Items List View
        ArrayList<QuizItem> quizList = new ArrayList<>();
        quizList.add(new QuizItem("Zoology", "Do you know your animals?", "animal", 20));
        // Add quiz items here

        QuizListAdapterActivity quizAdapter = new QuizListAdapterActivity(this, R.layout.activity_quiz_list_adapter, quizList);
        lvQuizList.setAdapter(quizAdapter);
    }

    /**
     * A native method that is implemented by the 'projectalpha' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}