package net.atlas.projectalpha;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import net.atlas.projectalpha.databinding.ActivityMainBinding;
import net.atlas.projectalpha.model.Option;
import net.atlas.projectalpha.model.Question;
import net.atlas.projectalpha.model.QuizItem;

import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity {
    private TextView tvPlayQuestion;
    private ImageView ivPlayImage;
    private Button btnPlayA, btnPlayB, btnPlayC, btnPlayD;
    private Button btnPlayNext;

    private ArrayList<Question> questions;
    private int currentQuestion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        tvPlayQuestion = findViewById(R.id.tvPlayQuestion);
        ivPlayImage = findViewById(R.id.ivPlayImage);
        btnPlayA = findViewById(R.id.btnPlayA);
        btnPlayB = findViewById(R.id.btnPlayB);
        btnPlayC = findViewById(R.id.btnPlayC);
        btnPlayD = findViewById(R.id.btnPlayD);
        btnPlayNext = findViewById(R.id.btnPlayNext);

        // Get Quiz Item
        Intent intent = getIntent();
        QuizItem quizItem = (QuizItem) intent.getParcelableExtra("quizItem");
        if (quizItem != null){
            questions = quizItem.getQuestions();
            if (questions != null && !questions.isEmpty()){
                showQuestion(currentQuestion);
            }
        }

        View.OnClickListener answerButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(v.getId());
                btnPlayNext.setVisibility(View.VISIBLE);
            }
        };

        btnPlayA.setOnClickListener(answerButtonListener);
        btnPlayB.setOnClickListener(answerButtonListener);
        btnPlayC.setOnClickListener(answerButtonListener);
        btnPlayD.setOnClickListener(answerButtonListener);

        btnPlayNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestion < questions.size() - 1){
                    currentQuestion++;
                    showQuestion(currentQuestion);
                } else {
                    btnPlayNext.setText("RETURN");
                    btnPlayNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(PlayActivity.this, MainActivity.class);

                            startActivity(intent);
                        }
                    });
                }
                btnPlayNext.setVisibility(View.GONE);
            }
        });
    }

    private void showQuestion(int index){
        Question question = questions.get(index);
        tvPlayQuestion.setText(question.getText());

        if (question.isUse_image()){
            Glide.with(this)
                    .load(question.getImage())
                    .placeholder(R.drawable.quiz_time_logo)
                    .error(R.drawable.quiz_time_logo)
                    .into(ivPlayImage);
        }

        ArrayList<String> options = new ArrayList<>();
        for (Option option : question.getOptions()){
            options.add(option.getOptionText());
        }

        OptionsFragment optionsFragment = new OptionsFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("options", options);
        optionsFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameOptions, optionsFragment)
                .commit();
    }

    private void checkAnswer(int buttonId){
        Question currQuestion = questions.get(currentQuestion);
        Option selectedOption = null;

        if (buttonId == R.id.btnPlayA){
            selectedOption = currQuestion.getOptions().get(0);
        } else if (buttonId == R.id.btnPlayB){
            if (currQuestion.getOptions().size() > 1) {
                selectedOption = currQuestion.getOptions().get(1);
            }
        } else if (buttonId == R.id.btnPlayC){
            if (currQuestion.getOptions().size() > 2) {
                selectedOption = currQuestion.getOptions().get(2);
            }
        } else if (buttonId == R.id.btnPlayD){
            if (currQuestion.getOptions().size() > 3) {
                selectedOption = currQuestion.getOptions().get(3);
            }
        }

        if (selectedOption != null && selectedOption.isCorrect()){
            updateOptionsFragmentTextViewBackground(selectedOption.getOptionText(), getResources().getColor(R.color.green));
        } else {
            updateOptionsFragmentTextViewBackground(selectedOption.getOptionText(), getResources().getColor(R.color.red));
        }
    }

    // Method to update the fragment's content dynamically
    public void updateOptionsFragmentTextViewBackground(String optionText, int color) {
        OptionsFragment optionsFragment = (OptionsFragment) getSupportFragmentManager().findFragmentById(R.id.frameOptions);
        if (optionsFragment != null) {
            optionsFragment.updateTextViewBackgroundColor(optionText, color);
        }
    }
}
