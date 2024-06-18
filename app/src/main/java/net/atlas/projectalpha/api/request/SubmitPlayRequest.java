package net.atlas.projectalpha.api.request;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SubmitPlayRequest {
    private String quiz_id;
    private String answer;

    public SubmitPlayRequest(String quiz_id, ArrayList<Integer> answer) {
        this.quiz_id = quiz_id;

        Gson gson = new Gson();
        this.answer = gson.toJson(answer);
    }

    public String getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(String quiz_id) {
        this.quiz_id = quiz_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
