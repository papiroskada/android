package com.example.firstlab;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private InputFragment inputFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputFragment = new InputFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, inputFragment)
                .commit();
    }

    public void showOutputFragment(String question, String answer) {
        String result = "Питання: " + question + "\nВідповідь: " + answer;

        boolean saved = saveToFile(result);
        Toast.makeText(this, saved ? "Результат збережено" : "Помилка збереження", Toast.LENGTH_SHORT).show();

        OutputFragment outputFragment = OutputFragment.newInstance(question, answer);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, outputFragment)
                .addToBackStack(null)
                .commit();
    }

    public void hideOutputFragment() {
        getSupportFragmentManager().popBackStack();
    }

    private boolean saveToFile(String data) {
        try {
            FileOutputStream fos = openFileOutput("results.txt", MODE_APPEND);
            fos.write((data + "\n\n").getBytes());
            fos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

