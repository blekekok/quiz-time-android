package net.atlas.projectalpha.model;

public class QuizItem {
    private String title;
    private String description;
    private String category;
    private int plays;

    public QuizItem(String title, String description, String category, int plays) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.plays = plays;
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
}
