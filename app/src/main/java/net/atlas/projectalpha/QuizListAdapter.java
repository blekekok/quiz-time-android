package net.atlas.projectalpha;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class QuizListAdapter extends ArrayAdapter<String> {
    // Java Class for Quiz here
    public QuizListAdapter(Context context, ArrayList<String> items) {
        super(context, R.layout.quiz_item_layout, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate and customize list item view
        return convertView;
    }
}