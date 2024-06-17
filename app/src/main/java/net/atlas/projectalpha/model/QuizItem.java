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
    private String id, title, description, category, image;
    private int plays;
    private ArrayList<Question> questions;

    public QuizItem(String id, String title, String description, String category, String image, int plays, ArrayList<Question> question) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.image = image;
        this.plays = plays;
        this.questions = question;
    }

    protected QuizItem(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.category = in.readString();
        this.image = in.readString();
        this.plays = in.readInt();
        this.questions = in.readArrayList(Question.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(category);
        dest.writeString(image);
        dest.writeInt(plays);
        dest.writeList(questions);
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

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
}
