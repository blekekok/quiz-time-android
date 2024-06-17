package net.atlas.projectalpha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.atlas.projectalpha.api.ApiResponse;
import net.atlas.projectalpha.api.SessionManager;
import net.atlas.projectalpha.api.model.QuizListAllViewModel;
import net.atlas.projectalpha.api.model.UserInfoViewModel;
import net.atlas.projectalpha.api.response.QuizResponse;
import net.atlas.projectalpha.databinding.ActivityMainBinding;
import net.atlas.projectalpha.model.QuizItem;

import org.json.JSONArray;
import org.json.JSONException;
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

    private Button btnMainSignIn;
    private Button btnMainSignOut;
    private TextView usernameText;
    private EditText edtSearch;
    private ListView lvQuizList;

    private SessionManager sessionManager;
    private UserInfoViewModel userInfoViewModel;
    private QuizListAllViewModel quizListAllViewModel;

    private ProgressDialog dialog;
    private int dialogDismiss = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new ProgressDialog(this);

        btnMainSignIn = findViewById(R.id.btnMainSignIn);
        btnMainSignOut = findViewById(R.id.btnMainSignOut);
        usernameText = findViewById(R.id.usernameText);
        edtSearch = findViewById(R.id.edtSearch);
        lvQuizList = findViewById(R.id.lvQuizList);

        sessionManager = new SessionManager(this);
        userInfoViewModel = new ViewModelProvider(this).get(UserInfoViewModel.class);
        quizListAllViewModel = new ViewModelProvider(this).get(QuizListAllViewModel.class);

        Log.d("START", "creating...");

        fetchUserInfo();
        fetchQuizList();

        // Sign In Button
        btnMainSignIn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, SignInActivity.class);

            startActivity(intent);
        });

        btnMainSignOut.setOnClickListener(v -> {
            sessionManager.logout();

            Intent intent = new Intent();
            intent.setClass(MainActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void fetchUserInfo() {
        showLoadingDialog();

        userInfoViewModel.fetchUserInfo(MainActivity.this);

        userInfoViewModel.getResponse().observe(this, response -> {
            closeLoadingDialog();

            if (response == null) {
                showLogin();
                return;
            }

            String name = response.getData().getName();
            usernameText.setText(name);

            showLogout();
        });
    }

    private void showLogin() {
        btnMainSignIn.setVisibility(View.VISIBLE);
        btnMainSignOut.setVisibility(View.GONE);
        usernameText.setVisibility(View.GONE);
    }

    private void showLogout() {
        btnMainSignIn.setVisibility(View.GONE);
        btnMainSignOut.setVisibility(View.VISIBLE);
        usernameText.setVisibility(View.VISIBLE);
    }

    private void fetchQuizList() {
        showLoadingDialog();

        quizListAllViewModel.fetch(MainActivity.this);

        quizListAllViewModel.getResponse().observe(this, response -> {
            if (response == null) {
                return;
            }

            loadQuizzes(response.getData());

            closeLoadingDialog();
        });
    }

    private void loadQuizzes(QuizResponse[] quizzes) {
        // Add Quiz Items to List View
        ArrayList<QuizItem> quizList = new ArrayList<>();

        for (QuizResponse quiz : quizzes) {
            String title = quiz.getTitle();
            String desc = quiz.getDescription();
            String category = quiz.getCategory();
            String image = quiz.getThumbnail();
            int plays = 0; // Change this later
            quizList.add(new QuizItem(title, desc, category, image, plays));
        }

        QuizListAdapterActivity quizAdapter = new QuizListAdapterActivity(this, R.layout.activity_quiz_list_adapter, quizList);
        lvQuizList.setAdapter(quizAdapter);

        // When item is clicked, show QuizDescActivity
        lvQuizList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, QuizDescActivity.class);

                QuizItem quiz = quizList.get(position);
                intent.putExtra("quizItem", quiz);

                startActivity(intent);
            }
        });
    }

    private void showLoadingDialog() {
        dialogDismiss++;

        if (dialogDismiss > 0) {
            return;
        }

        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
    }

    private void closeLoadingDialog() {
        dialogDismiss = Math.max(--dialogDismiss, 0);
        if (dialogDismiss == 0) {
            dialog.dismiss();
        }
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