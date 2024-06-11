package net.atlas.projectalpha.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Option {
    private String text;
    private boolean isCorrect;

    public Option(JSONObject optionObj) {
        try {
            this.text = optionObj.getString("text");
            this.isCorrect = optionObj.getBoolean("isCorrect");
        } catch (JSONException e) {
            Log.d("Option.java", "JSONException: " + e.getMessage());
        }
    }

    public String getOptionText() {
        return text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

}
