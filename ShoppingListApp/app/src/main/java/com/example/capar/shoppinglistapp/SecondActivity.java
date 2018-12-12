package com.example.capar.shoppinglistapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    private static final String LOG_TAG = SecondActivity.class.getSimpleName();

    public static final String EXTRA_REPLY =
            "com.example.android.shoppinglistapp.extra.REPLY";

    private Button apples, apricots, bananas, blueberries, cantaloupe, cherries,
            grapefruit, honeydew, lemons, mangos, oranges, peaches, pears,
            pineapples, plum, pomegranate, raspberries, strawberries, watermelon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        apples = findViewById(R.id.apples_item);
        apricots = findViewById(R.id.apricots_item);
        bananas = findViewById(R.id.bananas_item);
        blueberries = findViewById(R.id.blueberries_item);
        cantaloupe = findViewById(R.id.cantaloupe_item);
        cherries = findViewById(R.id.cherries_item);
        grapefruit = findViewById(R.id.grapefruits_item);
        honeydew = findViewById(R.id.honeydew_item);
        lemons = findViewById(R.id.lemons_item);
        mangos = findViewById(R.id.mangos_item);
        oranges = findViewById(R.id.oranges_item);
        peaches = findViewById(R.id.peaches_item);
        pears = findViewById(R.id.pears_item);
        pineapples = findViewById(R.id.pineapples_item);
        plum = findViewById(R.id.plums_item);
        pomegranate = findViewById(R.id.pomegranate_item);
        raspberries = findViewById(R.id.raspberries_item);
        strawberries = findViewById(R.id.strawberries_item);
        watermelon = findViewById(R.id.watermelon_item);
    }

    public void sendItem(View view) {
        String reply = ((Button)view).getText().toString();
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY,reply);
        setResult(RESULT_OK, replyIntent);
        Log.d(LOG_TAG, "onActivityResult: " + reply);
        finish();
    }
}
