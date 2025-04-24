package com.example.firstlab;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileViewActivity extends AppCompatActivity {

    private TextView fileContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_view);

        fileContent = findViewById(R.id.textFileContent);
        Button buttonDelete = findViewById(R.id.buttonDelete);
        Button buttonBack = findViewById(R.id.buttonBack);

        loadFileContent();

        buttonDelete.setOnClickListener(v -> {
            deleteFile("results.txt");
            Toast.makeText(this, "Файл очищено", Toast.LENGTH_SHORT).show();
            fileContent.setText("Дані відсутні.");
        });

        buttonBack.setOnClickListener(v -> finish());
    }

    private void loadFileContent() {
        try {
            FileInputStream fis = openFileInput("results.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();

            String content = sb.toString().trim();
            fileContent.setText(content.isEmpty() ? "Дані відсутні." : content);

        } catch (IOException e) {
            fileContent.setText("Файл не знайдено або пустий.");
        }
    }
}
