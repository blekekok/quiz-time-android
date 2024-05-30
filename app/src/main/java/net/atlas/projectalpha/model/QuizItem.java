package net.atlas.projectalpha.model;

public class QuizItem {
    private String title;
    private String description;
    private String category;
    private int plays;
    private int questions;
    private int image;

    public QuizItem(String title, String description, String category, int plays, int questions, int image) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.plays = plays;
        this.questions = questions;
        this.image = image;
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

    public String getPlays() {
        String playsStr = plays + " Plays";
        return playsStr;
    }

    public String getQuestions(){
        String questionsStr = questions + " questions";
        return questionsStr;
    }

    public int getImage(){
        return image;
    }
}
