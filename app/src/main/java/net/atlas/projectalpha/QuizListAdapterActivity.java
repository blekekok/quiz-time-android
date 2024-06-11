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
    private ArrayList<QuizItem> quizItems;

    public QuizListAdapterActivity(@NonNull Context context, int resource, @NonNull ArrayList<QuizItem> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.quizItems = objects;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.tvQuizTitle = convertView.findViewById(R.id.tvQuizTitle);
            holder.tvQuizDesc = convertView.findViewById(R.id.tvQuizDesc);
            holder.tvQuizPlays = convertView.findViewById(R.id.tvQuizPlays);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        QuizItem quizItem = quizItems.get(position);
        holder.tvQuizTitle.setText(quizItem.getTitle());
        holder.tvQuizDesc.setText(quizItem.getDescription());
        holder.tvQuizPlays.setText(quizItem.getPlays());

        return convertView;
    }

    static class ViewHolder{
        TextView tvQuizTitle;
        TextView tvQuizDesc;
        TextView tvQuizPlays;
    }
}