package net.atlas.projectalpha;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OptionsFragment extends Fragment {

    private LinearLayout layout;
    private Map<String, TextView> textViewMap = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_options, container, false);
        layout = view.findViewById(R.id.optionsLayout);

        // TEMP Create TextView options
        Bundle args = getArguments();
        if (args != null) {
            ArrayList<String> options = args.getStringArrayList("options");
            if (options != null) {
                for (String option : options) {
                    TextView textView = new TextView(getContext());
                    textView.setText(option);
                    textView.setTextSize(20f);
                    textView.setTextColor(getResources().getColor(R.color.black));
                    textView.setBackgroundColor(getResources().getColor(R.color.white));

                    textViewMap.put(option, textView);

                    layout.addView(textView);
                }
            }
        }

        return view;
    }

    // TEMP Method to update the background color
    public void updateTextViewBackgroundColor(String optionText, int color) {
        TextView textView = textViewMap.get(optionText);
        if (textView != null) {
            textView.setBackgroundColor(color);
        }
    }
}
