package net.atlas.projectalpha.api.response;

import java.util.ArrayList;

public class QuizDetailResponse {
    private String id;
    private String title;
    private String description;
    private String category;
    private String thumbnail;
    private String authorName;
    private String questionCount;
    private ArrayList<QuizQuestionsResponse> content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(String questionCount) {
        this.questionCount = questionCount;
    }

    public ArrayList<QuizQuestionsResponse> getContent() {
        return content;
    }

    public void setContent(ArrayList<QuizQuestionsResponse> content) {
        this.content = content;
    }
}
