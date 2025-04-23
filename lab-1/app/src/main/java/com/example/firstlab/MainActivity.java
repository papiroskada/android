package com.example.firstlab;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText editQuestion;
    RadioGroup radioGroup;
    RadioButton radioYes, radioNo;
    Button buttonOk;
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editQuestion = findViewById(R.id.editQuestion);
        radioGroup = findViewById(R.id.radioGroup);
        radioYes = findViewById(R.id.radioYes);
        radioNo = findViewById(R.id.radioNo);
        buttonOk = findViewById(R.id.buttonOk);
        resultText = findViewById(R.id.resultText);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = editQuestion.getText().toString().trim();
                int selectedId = radioGroup.getCheckedRadioButtonId();

                if (question.isEmpty() || selectedId == -1) {
                    showAlert("Будь ласка, заповніть всі поля.");
                    return;
                }

                RadioButton selectedRadio = findViewById(selectedId);
                String answer = selectedRadio.getText().toString();

                resultText.setText("Питання: " + question + "\nВідповідь: " + answer);
            }
        });
    }
    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Увага");
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}
