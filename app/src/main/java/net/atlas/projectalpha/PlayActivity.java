package net.atlas.projectalpha;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import net.atlas.projectalpha.api.SessionManager;
import net.atlas.projectalpha.api.model.SubmitPlayViewModel;
import net.atlas.projectalpha.model.Question;
import net.atlas.projectalpha.model.QuizItem;

import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity {
    private TextView tvPlayQuestion;
    private Button btnPlayA, btnPlayB, btnPlayC, btnPlayD;

    private ProgressDialog dialog;
    private CountDownTimer countdown;
    private final String DEFAULT_COLOR = "#6b82e2";
    private boolean isShowingAnswers = false;
    private boolean isLEDOn = false;

    private QuizItem quizItem;
    private ArrayList<Question> questions;
    private int currentQuestion = 0;
    private ArrayList<Integer> answers;

    private SessionManager sessionManager;
    private SubmitPlayViewModel submitPlayViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        tvPlayQuestion = findViewById(R.id.tvPlayQuestion);
        btnPlayA = findViewById(R.id.btnPlayA);
        btnPlayB = findViewById(R.id.btnPlayB);
        btnPlayC = findViewById(R.id.btnPlayC);
        btnPlayD = findViewById(R.id.btnPlayD);

        setLCD("", "");

        setButtonBackgroundColor(DEFAULT_COLOR);

        dialog = new ProgressDialog(this);

        sessionManager = new SessionManager(this);
        submitPlayViewModel = new ViewModelProvider(this).get(SubmitPlayViewModel.class);

        // Get Quiz Item
        quizItem = getIntent().getParcelableExtra("quizItem");
        questions = quizItem.getQuestions();

        answers = new ArrayList<>();

        currentQuestion = 0;

        introCountdown();

        View.OnClickListener answerButtonListener = v -> showCorrectAnswers(v.getId());

        btnPlayA.setOnClickListener(answerButtonListener);
        btnPlayB.setOnClickListener(answerButtonListener);
        btnPlayC.setOnClickListener(answerButtonListener);
        btnPlayD.setOnClickListener(answerButtonListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (this.countdown != null) {
            countdown.cancel();
        }
    }

    private void showQuestion() {
        Question question = questions.get(currentQuestion);

        String questionText = question.getQuestion();
        tvPlayQuestion.setText(questionText);

        ArrayList<String> options = question.getOptions();

        btnPlayA.setVisibility(View.INVISIBLE);
        btnPlayB.setVisibility(View.INVISIBLE);
        btnPlayC.setVisibility(View.INVISIBLE);
        btnPlayD.setVisibility(View.INVISIBLE);

        int totalAnswers = answers.size();
        int totalQuestions = questions.size();
        int correctAnswers = 0;
        for (int i = 0; i < totalAnswers; i++) {
            if (answers.get(i) == questions.get(i).getCorrectAnswer()) {
                correctAnswers++;
            }
        }

        setLCD(String.format("Question %d", currentQuestion + 1),
                String.format("Correct %d / %d", correctAnswers, totalQuestions));

        int count = options.size();
        if (count >= 1) {
            btnPlayA.setVisibility(View.VISIBLE);
            btnPlayA.setText(options.get(0));
        }

        if (count >= 2) {
            btnPlayB.setVisibility(View.VISIBLE);
            btnPlayB.setText(options.get(1));
        }

        if (count >= 3) {
            btnPlayC.setVisibility(View.VISIBLE);
            btnPlayC.setText(options.get(2));
        }

        if (count >= 4) {
            btnPlayD.setVisibility(View.VISIBLE);
            btnPlayD.setText(options.get(3));
        }

        questionCountdown();
    }

    private void questionCountdown() {
        int countdownSeconds = 8;

        this.countdown = new CountDownTimer(countdownSeconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);

                setLED(seconds);
            }

            @Override
            public void onFinish() {
                showCorrectAnswers(-1);
            }
        };
        this.countdown.start();
    }

    private void checkAnswer(int buttonId) {
        int selected = -1;

        if (buttonId == R.id.btnPlayA) {
            selected = 0;
        }

        if (buttonId == R.id.btnPlayB) {
            selected = 1;
        }

        if (buttonId == R.id.btnPlayC) {
            selected = 2;
        }

        if (buttonId == R.id.btnPlayD) {
            selected = 3;
        }

        answers.add(selected);

        currentQuestion++;

        if (currentQuestion >= questions.size()) {
            submitPlay();
            return;
        }

        showQuestion();
    }

    private void submitPlay() {
        showLoadingDialog();

        boolean isLoggedIn = sessionManager.isLoggedIn();
        if (!isLoggedIn) {
            quizFinished(0);
            return;
        }

        String quizId = this.quizItem.getId();
        submitPlayViewModel.send(quizId, answers, PlayActivity.this);

        submitPlayViewModel.getResponse().observe(PlayActivity.this, response -> {
            if (response == null) {
                quizFinished(2);
                return;
            }

            closeLoadingDialog();

            quizFinished(1);
        });
    }

    private void quizFinished(int submitStatus) {
        int correctAnswers = 0;
        int totalAnswers = questions.size();

        for (int i = 0; i < totalAnswers; i++) {
            if (answers.get(i) == questions.get(i).getCorrectAnswer()) {
                correctAnswers++;
            }
        }

        String[] finishText = new String[] {
                "Progress is not saved",
                "Quiz saved",
                "Unable to save quiz"
        };

        setLED(0);

        setLCD(
                "Congratulations",
                String.format("Guessed %d / %d", correctAnswers, totalAnswers));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Congratulations")
                .setMessage(String.format("You've guessed %d / %d correct\n%s", correctAnswers, totalAnswers, finishText[submitStatus]))
                .setPositiveButton("Finish", (dialog, which) -> {
                    dialog.dismiss();
                    Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    setLCD("", "");
                    finish();
                });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        dialog.show();
    }

    private void showCorrectAnswers(int buttonId) {
        if (isShowingAnswers) {
            return;
        }

        if (this.countdown != null) {
            this.countdown.cancel();
        }

        isShowingAnswers = true;

        Question question = questions.get(currentQuestion);
        int correctAnswer = question.getCorrectAnswer();

        setButtonBackgroundColor("#f08080");

        int colorGreen = Color.parseColor("#6fcc9f");
        switch (correctAnswer) {
            case 0:
                btnPlayA.setBackgroundColor(colorGreen);
                break;

            case 1:
                btnPlayB.setBackgroundColor(colorGreen);
                break;

            case 2:
                btnPlayC.setBackgroundColor(colorGreen);
                break;

            case 3:
                btnPlayD.setBackgroundColor(colorGreen);
                break;
        }

        this.countdown = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                flipLED();
            }

            @Override
            public void onFinish() {
                setLED(0);
                setButtonBackgroundColor(DEFAULT_COLOR);
                isShowingAnswers = false;
                checkAnswer(buttonId);
            }
        };

        this.countdown.start();
    }

    private void setButtonBackgroundColor(String hex) {
        int color = Color.parseColor(hex);
        btnPlayA.setBackgroundColor(color);
        btnPlayB.setBackgroundColor(color);
        btnPlayC.setBackgroundColor(color);
        btnPlayD.setBackgroundColor(color);
    }

    private void introCountdown() {
        this.countdown = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long second = millisUntilFinished / 1000;

                tvPlayQuestion.setText(Long.toString(second));

                setLCD("Get ready... " + second, "");

                flipLED();
            }

            @Override
            public void onFinish() {
                setLED(0);
                showQuestion();
            }
        };

        this.countdown.start();
    }

    private void showLoadingDialog() {
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
    }

    private void closeLoadingDialog() {
        dialog.dismiss();
    }

    private void flipLED() {
        setLED(isLEDOn ? 0 : 8);
        isLEDOn = !isLEDOn;
    }

    private native void setLED(int length);
    private native void setLCD(String line1, String line2);
}
