package net.atlas.projectalpha;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import net.atlas.projectalpha.databinding.ActivityMainBinding;
import net.atlas.projectalpha.model.QuizItem;

import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity {
    private Button btnPlayA, btnPlayB, btnPlayC, btnPlayD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        btnPlayA = findViewById(R.id.btnPlayA);
        btnPlayB = findViewById(R.id.btnPlayB);
        btnPlayC = findViewById(R.id.btnPlayC);
        btnPlayD = findViewById(R.id.btnPlayD);

        // Add the OptionsFragment initially if the activity is being created for the first time
        if (savedInstanceState == null) {
            OptionsFragment optionsFragment = new OptionsFragment();
            Bundle args = new Bundle();
            ArrayList<String> options = new ArrayList<>();
            options.add("Option A");
            options.add("Option B");
            options.add("Option C");
            options.add("Option D");
            args.putStringArrayList("options", options);
            optionsFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameOptions, optionsFragment)
                    .commit();
        }

        btnPlayA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOptionsFragmentTextViewBackground("Option A", getResources().getColor(R.color.green));
                updateOptionsFragmentTextViewBackground("Option B", getResources().getColor(R.color.white));
                updateOptionsFragmentTextViewBackground("Option C", getResources().getColor(R.color.white));
                updateOptionsFragmentTextViewBackground("Option D", getResources().getColor(R.color.white));
            }
        });
        btnPlayB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOptionsFragmentTextViewBackground("Option A", getResources().getColor(R.color.white));
                updateOptionsFragmentTextViewBackground("Option B", getResources().getColor(R.color.green));
                updateOptionsFragmentTextViewBackground("Option C", getResources().getColor(R.color.white));
                updateOptionsFragmentTextViewBackground("Option D", getResources().getColor(R.color.white));
            }
        });
        btnPlayC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOptionsFragmentTextViewBackground("Option A", getResources().getColor(R.color.white));
                updateOptionsFragmentTextViewBackground("Option B", getResources().getColor(R.color.white));
                updateOptionsFragmentTextViewBackground("Option C", getResources().getColor(R.color.green));
                updateOptionsFragmentTextViewBackground("Option D", getResources().getColor(R.color.white));
            }
        });
        btnPlayD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOptionsFragmentTextViewBackground("Option A", getResources().getColor(R.color.white));
                updateOptionsFragmentTextViewBackground("Option B", getResources().getColor(R.color.white));
                updateOptionsFragmentTextViewBackground("Option C", getResources().getColor(R.color.white));
                updateOptionsFragmentTextViewBackground("Option D", getResources().getColor(R.color.green));
            }
        });
    }

    // Method to update the fragment's content dynamically
    public void updateOptionsFragmentTextViewBackground(String optionText, int color) {
        OptionsFragment optionsFragment = (OptionsFragment) getSupportFragmentManager().findFragmentById(R.id.frameOptions);
        if (optionsFragment != null) {
            optionsFragment.updateTextViewBackgroundColor(optionText, color);
        }
    }
}
