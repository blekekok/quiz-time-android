package net.atlas.projectalpha.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class QuizItem implements Parcelable {
    private String title, description, category, image;
    private int plays;
    private ArrayList<Question> questions;

    public QuizItem(String title, String description, String category, String image, int plays, JSONArray questionsArr) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.image = image;
        this.plays = plays;
        this.questions = new ArrayList<>();

        for (int i = 0; i < questionsArr.length(); i++) {
            try {
                JSONObject questionObj = questionsArr.getJSONObject(i);
                this.questions.add(new Question(questionObj));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    protected QuizItem(Parcel in) {
        title = in.readString();
        description = in.readString();
        category = in.readString();
        image = in.readString();
        plays = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(category);
        dest.writeString(image);
        dest.writeInt(plays);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QuizItem> CREATOR = new Creator<QuizItem>() {
        @Override
        public QuizItem createFromParcel(Parcel in) {
            return new QuizItem(in);
        }

        @Override
        public QuizItem[] newArray(int size) {
            return new QuizItem[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getImage(){
        return image;
    }

    public String getPlays() {
        String playsStr = plays + " Plays";
        return playsStr;
    }

    public String getNumOfQuestions(){
        if (questions != null){
            return questions.size() + " questions";
        } else {
            return "0 questions";
        }
    }

    public ArrayList<Question> getQuestions(){
        return questions;
    }

}
