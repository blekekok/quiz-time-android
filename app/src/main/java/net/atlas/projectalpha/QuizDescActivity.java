package net.atlas.projectalpha;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import net.atlas.projectalpha.databinding.ActivityMainBinding;

public class QuizDescActivity extends AppCompatActivity {
    private TextView tvDescTitle, tvDescCategory, tvDescDesc, tvDescQuestions;
    private ImageView ivDescImage;
    private Button btnDescPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_desc);

        tvDescTitle = findViewById(R.id.tvDescTitle);
        tvDescCategory = findViewById(R.id.tvDescCategory);
        tvDescDesc = findViewById(R.id.tvDescDesc);
        tvDescQuestions = findViewById(R.id.tvDescQuestions);
        ivDescImage = findViewById(R.id.ivDescImage);

        // Get Quiz Item
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String category = intent.getStringExtra("category");
        String description = intent.getStringExtra("description");
        String questions = intent.getStringExtra("questions");
        int image = intent.getIntExtra("image", R.drawable.ic_launcher_foreground);

        tvDescTitle.setText(title);
        tvDescCategory.setText(category);
        tvDescDesc.setText(description);
        tvDescQuestions.setText(questions);
        ivDescImage.setImageResource(image);
    }

}