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
import androidx.lifecycle.GenericLifecycleObserver;

import com.bumptech.glide.Glide;

import net.atlas.projectalpha.databinding.ActivityMainBinding;
import net.atlas.projectalpha.model.QuizItem;

public class QuizDescActivity extends AppCompatActivity {
    private ImageView ivDescImage;
    private TextView tvDescTitle, tvDescDesc, tvDescCategory, tvDescQuestions;
    private Button btnDescPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_desc);

        ivDescImage = findViewById(R.id.ivDescImage);
        tvDescTitle = findViewById(R.id.tvDescTitle);
        tvDescDesc = findViewById(R.id.tvDescDesc);
        tvDescCategory = findViewById(R.id.tvDescCategory);
        tvDescQuestions = findViewById(R.id.tvDescQuestions);
        btnDescPlay = findViewById(R.id.btnDescPlay);

        // Get Quiz Item
        Intent intent = getIntent();
        QuizItem quizItem = (QuizItem) intent.getParcelableExtra("quizItem");

        tvDescTitle.setText(quizItem.getTitle());
        tvDescDesc.setText(quizItem.getDescription());
        tvDescCategory.setText(quizItem.getCategory());
//        tvDescQuestions.setText(quizItem.getQuestions().size() + " questions");
        Glide.with(this)
                .load(quizItem.getImage())
                .placeholder(R.drawable.quiz_time_logo)
                .error(R.drawable.quiz_time_logo)
                .into(ivDescImage);

        // Play button
        btnDescPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(QuizDescActivity.this, PlayActivity.class);

                startActivity(intent);
            }
        });
    }

}