package net.atlas.projectalpha.api.response;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class QuizQuestionsResponse {
    private String question;
    private ArrayList<String> options;
    private int correctAnswer;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
