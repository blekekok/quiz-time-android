package net.atlas.projectalpha.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Question {
    private String text, image;
    private boolean use_image;
    private ArrayList<Option> options;

    public Question(JSONObject questionObj) {
        try {
            this.text = questionObj.getString("text");
            this.image = questionObj.getString("image");
            this.use_image = questionObj.getBoolean("use_image");
            this.options = new ArrayList<>();

            JSONArray optionsArr = questionObj.getJSONArray("options");
            for (int i = 0; i < optionsArr.length(); i++) {
                JSONObject optionObj = optionsArr.getJSONObject(i);
                this.options.add(new Option(optionObj));
            }
        } catch (JSONException e) {
            Log.d("Question.java", "JSONException: " + e.getMessage());
        }
    }

    // Get a question from JSONObject
//    public static Question fromJson(JSONObject question){
//        try {
//            String text = question.getString("text");
//            String image = question.getString("image");
//            boolean use_image = question.getBoolean("use_image");
//
//            JSONArray optionsArr = question.getJSONArray("options");
//            ArrayList<Option> options = new ArrayList<>();
//            for (int i = 0 ; i < optionsArr.length() ; i++){
//                JSONObject optionObj = optionsArr.getJSONObject(i);
//                String optionText = optionObj.getString("text");
//                boolean isCorrect = optionObj.getBoolean("isCorrect");
//
//                options.add(new Option(optionText, isCorrect));
//            }
//
//            return new Question(text, image, use_image, options);
//        } catch (Exception e) {
//            return null;
//        }
//    }

    // Get array of questions from JSON String
//    public static ArrayList<Question> fromJsonArray(String jsonString){
//        ArrayList<Question> questions = new ArrayList<>();
//        try {
//            JSONObject jsonObject = new JSONObject(jsonString);
//            JSONArray questionsArr = jsonObject.getJSONArray("questions");
//
//            for (int i = 0 ; i < questionsArr.length(); i++){
//                JSONObject questionObj = questionsArr.getJSONObject(i);
//                Question question = fromJson(questionObj);
//                if (question != null){
//                    questions.add(question);
//                }
//            }
//        } catch (Exception e) {}
//
//        return questions;
//    }

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }

    public boolean isUse_image() {
        return use_image;
    }

    public ArrayList<Option> getOptions() {
        return options;
    }
}
