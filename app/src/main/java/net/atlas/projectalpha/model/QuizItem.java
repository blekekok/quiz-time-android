package net.atlas.projectalpha.model;

import java.io.Serializable;
import java.util.ArrayList;

public class QuizItem implements Serializable {
    private String title, description, category, image;
    private int plays;
    private ArrayList<Question> questions;

    public QuizItem(String title, String description, String category, String image, int plays, String questionsJSON) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.image = image;
        this.plays = plays;
        this.questions = Question.fromJsonArray(questionsJSON);
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
        return questions.size() + " questions";
    }

    public ArrayList<Question> getQuestions(){
        return questions;
    }

}
