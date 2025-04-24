package com.example.firstlab;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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
        OutputFragment outputFragment = OutputFragment.newInstance(question, answer);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, outputFragment)
                .addToBackStack(null)
                .commit();
    }

    public void hideOutputFragment() {
        getSupportFragmentManager().popBackStack();
    }
}
