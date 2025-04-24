package com.example.firstlab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class OutputFragment extends Fragment {
    private static final String ARG_QUESTION = "question";
    private static final String ARG_ANSWER = "answer";

    public OutputFragment() {}

    public static OutputFragment newInstance(String question, String answer) {
        OutputFragment fragment = new OutputFragment();
        Bundle args = new Bundle();
        args.putString(ARG_QUESTION, question);
        args.putString(ARG_ANSWER, answer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_output, container, false);

        TextView resultText = view.findViewById(R.id.resultText);
        Button buttonCancel = view.findViewById(R.id.buttonCancel);

        if (getArguments() != null) {
            String question = getArguments().getString(ARG_QUESTION);
            String answer = getArguments().getString(ARG_ANSWER);
            resultText.setText("Питання: " + question + "\nВідповідь: " + answer);
        }

        buttonCancel.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).hideOutputFragment();
            }
        });

        return view;
    }
}
