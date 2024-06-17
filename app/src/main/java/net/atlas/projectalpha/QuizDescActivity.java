package net.atlas.projectalpha;

import android.app.ProgressDialog;
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
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import net.atlas.projectalpha.api.model.QuizDetailViewModel;
import net.atlas.projectalpha.api.response.QuizQuestionsResponse;
import net.atlas.projectalpha.databinding.ActivityMainBinding;
import net.atlas.projectalpha.model.Question;
import net.atlas.projectalpha.model.QuizItem;

import java.util.ArrayList;

public class QuizDescActivity extends AppCompatActivity {
    private ImageView ivDescImage;
    private TextView tvDescTitle, tvDescDesc, tvDescCategory, tvDescQuestions;
    private Button btnDescPlay;

    private QuizItem quizItem;

    private QuizDetailViewModel quizDetailViewModel;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_desc);

        dialog = new ProgressDialog(this);

        ivDescImage = findViewById(R.id.ivDescImage);
        tvDescTitle = findViewById(R.id.tvDescTitle);
        tvDescDesc = findViewById(R.id.tvDescDesc);
        tvDescCategory = findViewById(R.id.tvDescCategory);
        tvDescQuestions = findViewById(R.id.tvDescQuestions);
        btnDescPlay = findViewById(R.id.btnDescPlay);

        quizDetailViewModel = new ViewModelProvider(this).get(QuizDetailViewModel.class);

        // Get Quiz Item
        Intent intent = getIntent();
        quizItem = intent.getParcelableExtra("quizItem");

        fetchQuizDetail(quizItem.getId());

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
                Intent intent = new Intent(QuizDescActivity.this, PlayActivity.class);

                intent.putExtra("quizItem", quizItem);

                startActivity(intent);
            }
        });
    }

    private void fetchQuizDetail(String id) {
        showLoadingDialog();

        quizDetailViewModel.fetch(id, this);

        quizDetailViewModel.getResponse().observe(this, response -> {
            ArrayList<Question> questions = new ArrayList<>();
            for (QuizQuestionsResponse question : response.getData().getContent()) {
                String questionText = question.getQuestion();
                ArrayList<String> options = question.getOptions();
                int correctAnswer = question.getCorrectAnswer();
                questions.add(new Question(questionText, options, correctAnswer));
            }

            quizItem.setQuestions(questions);

            closeLoadingDialog();
        });
    }

    private void showLoadingDialog() {
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
    }

    private void closeLoadingDialog() {
        dialog.dismiss();
    }

}