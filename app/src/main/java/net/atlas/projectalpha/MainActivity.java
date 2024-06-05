package net.atlas.projectalpha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

        // Add Quiz Items to List View
        ArrayList<QuizItem> quizList = new ArrayList<>();
        try {
            String jsonString = readRawResource(R.raw.questions);
            JSONArray questionsArr = new JSONArray(jsonString);

            for (int i = 0 ; i < questionsArr.length() ; i++){
                JSONObject questionObj = questionsArr.getJSONObject(i);
                quizList.add(new QuizItem(
                        questionObj.getString("title"),
                        questionObj.getString("description"),
                        questionObj.getString("category"),
                        questionObj.getString("image"),
                        questionObj.getInt("plays"),
                        questionObj.getJSONArray("questions").toString()));
            }

        } catch (Exception e) {}

        QuizListAdapterActivity quizAdapter = new QuizListAdapterActivity(this, R.layout.activity_quiz_list_adapter, quizList);
        lvQuizList.setAdapter(quizAdapter);

        // When item is clicked, show QuizDescActivity
        lvQuizList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, QuizDescActivity.class);
                intent.putExtra("quizItem", quizList.get(position));

                startActivity(intent);
            }
        });
    }

    private String readRawResource(int resourceId) {
        Resources resources = getResources();
        InputStream inputStream = resources.openRawResource(resourceId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int i;
        try {
            i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {}

        return byteArrayOutputStream.toString();
    }

    /**
     * A native method that is implemented by the 'projectalpha' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}