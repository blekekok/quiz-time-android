package net.atlas.projectalpha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.atlas.projectalpha.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'projectalpha' library on application startup.
    static {
        System.loadLibrary("projectalpha");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        // Example of a call to a native method
//        TextView tv = binding.sampleText;
//        tv.setText(stringFromJNI());

        LinearLayout quizList = findViewById(R.id.quizList);

        for (int i = 0; i < 5; i++) {
            View itemView = getLayoutInflater().inflate(R.layout.quiz_item_layout, null);

            TextView quizTitle = itemView.findViewById(R.id.titleText);
            quizTitle.setText("TEST" + i);

            quizList.addView(itemView);
        }
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