package com.example.firstlab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;

public class InputFragment extends Fragment {
    private EditText editQuestion;
    private RadioGroup radioGroup;

    public InputFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input, container, false);

        editQuestion = view.findViewById(R.id.editQuestion);
        radioGroup = view.findViewById(R.id.radioGroup);
        Button buttonOk = view.findViewById(R.id.buttonOk);
        Button buttonOpen = view.findViewById(R.id.buttonOpen);

        buttonOk.setOnClickListener(v -> {
            String question = editQuestion.getText().toString().trim();
            int selectedId = radioGroup.getCheckedRadioButtonId();

            if (question.isEmpty() || selectedId == -1) {
                Toast.makeText(getActivity(), "Будь ласка, заповніть всі поля", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedRadio = view.findViewById(selectedId);
            String answer = selectedRadio.getText().toString();

            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).showOutputFragment(question, answer);
            }
        });

        buttonOpen.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), FileViewActivity.class);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        clearForm();
    }

    public void clearForm() {
        if (editQuestion != null) {
            editQuestion.setText("");
        }
        if (radioGroup != null) {
            radioGroup.clearCheck();
        }
    }
}

