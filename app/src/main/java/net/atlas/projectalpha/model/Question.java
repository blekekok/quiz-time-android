package net.atlas.projectalpha.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Question {
    private String text, image;
    private boolean use_image;
    private ArrayList<Option> options = new ArrayList<>();

    public Question(String text, String image, boolean use_image, ArrayList<Option> options) {
        this.text = text;
        this.image = image;
        this.use_image = use_image;
        this.options = options;
    }

    // Get a question from JSONObject
    public static Question fromJson(JSONObject question){
        try {
            String text = question.getString("text");
            String image = question.getString("image");
            boolean use_image = question.getBoolean("use_image");

            JSONArray optionsArr = question.getJSONArray("options");
            ArrayList<Option> options = new ArrayList<>();
            for (int i = 0 ; i < optionsArr.length() ; i++){
                JSONObject optionObj = optionsArr.getJSONObject(i);
                String optionText = optionObj.getString("text");
                boolean isCorrect = optionObj.getBoolean("isCorrect");

                options.add(new Option(optionText, isCorrect));
            }

            return new Question(text, image, use_image, options);
        } catch (Exception e) {
            return null;
        }
    }

    // Get array of questions from JSON String
    public static ArrayList<Question> fromJsonArray(String jsonString){
        ArrayList<Question> questions = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray questionsArr = jsonObject.getJSONArray("questions");

            for (int i = 0 ; i < questionsArr.length(); i++){
                JSONObject questionObj = questionsArr.getJSONObject(i);
                Question question = fromJson(questionObj);
                if (question != null){
                    questions.add(question);
                }
            }
        } catch (Exception e) {}

        return questions;
    }

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
