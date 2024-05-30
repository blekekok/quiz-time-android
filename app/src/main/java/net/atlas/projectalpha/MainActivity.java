package net.atlas.projectalpha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
        quizList.add(new QuizItem("Zoology", "Do you know your animals?", "animal", 20, 5, R.drawable.quiz_time_logo));
        quizList.add(new QuizItem("Trees", "We don't make mistakes, we have happy accidents", "plants", 12, 7, R.drawable.quiz_time_logo));
        quizList.add(new QuizItem("Floriography", "The language of flowers, a cryptological communication through the use or arrangement of flowers", "plants", 8, 1, R.drawable.quiz_time_logo));
        quizList.add(new QuizItem("Ducks", "Free birds", "animal", 101, 3, R.drawable.quiz_time_logo));
        // Add quiz items here

        QuizListAdapterActivity quizAdapter = new QuizListAdapterActivity(this, R.layout.activity_quiz_list_adapter, quizList);
        lvQuizList.setAdapter(quizAdapter);

        // When item is clicked, show QuizDescActivity
        lvQuizList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, QuizDescActivity.class);
                intent.putExtra("title", quizList.get(position).getTitle());
                intent.putExtra("category", quizList.get(position).getCategory());
                intent.putExtra("description", quizList.get(position).getDescription());
                intent.putExtra("questions", quizList.get(position).getQuestions());
                intent.putExtra("image", quizList.get(position).getImage());

                startActivity(intent);
            }
        });
    }

    /**
     * A native method that is implemented by the 'projectalpha' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}