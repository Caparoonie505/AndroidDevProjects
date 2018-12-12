package com.example.capar.twoactivitieschallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE =
            "com.example.android.twoactiviiteschallenge.extra.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonOneClicked(View view) {

        Intent intent = new Intent(this,activity_second.class);
        String message = getString(R.string.first_message);
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);

    }

    public void buttonTwoClicked(View view) {

        Intent intent = new Intent(this,activity_second.class);
        String message = getString(R.string.second_message);
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);

    }

    public void buttonThreeClicked(View view) {

        Intent intent = new Intent(this,activity_second.class);
        String message = getString(R.string.third_message);
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);

    }
}
