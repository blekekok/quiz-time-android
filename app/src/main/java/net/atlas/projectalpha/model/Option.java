package net.atlas.projectalpha.model;

public class Option {
    private String text;
    private boolean isCorrect;

    public Option(String text, boolean isCorrect) {
        this.text = text;
        this.isCorrect = isCorrect;
    }

    public String getOptionText() {
        return text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

}
