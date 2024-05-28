package net.atlas.projectalpha;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.atlas.projectalpha.model.QuizItem;

import java.util.ArrayList;

public class QuizListAdapterActivity extends ArrayAdapter<QuizItem> {

    private Context context;
    private int resource;

    public QuizListAdapterActivity(@NonNull Context context, int resource, @NonNull ArrayList<QuizItem> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String title = getItem(position).getTitle();
        String description = getItem(position).getDescription();
        String plays = getItem(position).getPlays();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView tvQuizTitle = (TextView) convertView.findViewById(R.id.tvQuizTitle);
        TextView tvQuizDesc = (TextView) convertView.findViewById(R.id.tvQuizDesc);
        TextView tvQuizPlays = (TextView) convertView.findViewById(R.id.tvQuizPlays);

        tvQuizTitle.setText(title);
        tvQuizDesc.setText(description);
        tvQuizPlays.setText(plays);

        return convertView;
    }
}