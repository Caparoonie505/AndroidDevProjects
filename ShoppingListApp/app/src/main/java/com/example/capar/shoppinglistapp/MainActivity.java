package com.example.capar.shoppinglistapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    public static final int TEXT_REQUEST = 1;

    private TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10;
    private ArrayList<TextView> tList = new ArrayList<>();
    private EditText e1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = findViewById(R.id.list_item_1);
        t2 = findViewById(R.id.list_item_2);
        t3 = findViewById(R.id.list_item_3);
        t4 = findViewById(R.id.list_item_4);
        t5 = findViewById(R.id.list_item_5);
        t6 = findViewById(R.id.list_item_6);
        t7 = findViewById(R.id.list_item_7);
        t8 = findViewById(R.id.list_item_8);
        t9 = findViewById(R.id.list_item_9);
        t10 = findViewById(R.id.list_item_10);

        e1 = findViewById(R.id.store_location_edittext);

        tList.add(t1);
        tList.add(t2);
        tList.add(t3);
        tList.add(t4);
        tList.add(t5);
        tList.add(t6);
        tList.add(t7);
        tList.add(t8);
        tList.add(t9);
        tList.add(t10);

        if(savedInstanceState != null){
            boolean item1 = savedInstanceState.getBoolean("item_first_visible");
            boolean item2 = savedInstanceState.getBoolean("item_second_visible");
            boolean item3 = savedInstanceState.getBoolean("item_third_visible");
            boolean item4 = savedInstanceState.getBoolean("item_fourth_visible");
            boolean item5 = savedInstanceState.getBoolean("item_fifth_visible");
            boolean item6 = savedInstanceState.getBoolean("item_sixth_visible");
            boolean item7 = savedInstanceState.getBoolean("item_seventh_visible");
            boolean item8 = savedInstanceState.getBoolean("item_eighth_visible");
            boolean item9 = savedInstanceState.getBoolean("item_ninth_visible");
            boolean item10 = savedInstanceState.getBoolean("item_tenth_visible");
            if(item1){
                t1.setText(savedInstanceState.getString("item_first_text"));
            }
            if(item2){
                t2.setText(savedInstanceState.getString("item_second_text"));
            }
            if(item3){
                t3.setText(savedInstanceState.getString("item_third_text"));
            }
            if(item4){
                t4.setText(savedInstanceState.getString("item_fourth_text"));
            }
            if(item5){
                t5.setText(savedInstanceState.getString("item_fifth_text"));
            }
            if(item6){
                t6.setText(savedInstanceState.getString("item_sixth_text"));
            }
            if(item7){
                t7.setText(savedInstanceState.getString("item_seventh_text"));
            }
            if(item8){
                t8.setText(savedInstanceState.getString("item_eighth_text"));
            }
            if(item9){
                t9.setText(savedInstanceState.getString("item_ninth_text"));
            }
            if(item10){
                t10.setText(savedInstanceState.getString("item_tenth_text"));
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(t1.getText() != ""){
            outState.putBoolean("item_first_visible",true);
            outState.putString("item_first_text",t1.getText().toString());
        }if(t2.getText() != ""){
            outState.putBoolean("item_second_visible",true);
            outState.putString("item_second_text",t2.getText().toString());
        }if(t3.getText() != ""){
            outState.putBoolean("item_third_visible",true);
            outState.putString("item_third_text",t3.getText().toString());

        }if(t4.getText() != ""){
            outState.putBoolean("item_fourth_visible",true);
            outState.putString("item_fourth_text",t4.getText().toString());

        }if(t5.getText() != ""){
            outState.putBoolean("item_fifth_visible",true);
            outState.putString("item_fifth_text",t5.getText().toString());

        }if(t6.getText() != ""){
            outState.putBoolean("item_sixth_visible",true);
            outState.putString("item_sixth_text",t6.getText().toString());

        }if(t7.getText() != ""){
            outState.putBoolean("item_seventh_visible",true);
            outState.putString("item_seventh_text",t7.getText().toString());

        }if(t8.getText() != ""){
            outState.putBoolean("item_eighth_visible",true);
            outState.putString("item_eighth_text",t8.getText().toString());

        }if(t9.getText() != ""){
            outState.putBoolean("item_ninth_visible",true);
            outState.putString("item_ninth_text",t9.getText().toString());

        }if(t10.getText() != ""){
            outState.putBoolean("item_tenth_visible",true);
            outState.putString("item_tenth_text",t10.getText().toString());

        }
    }

    public void callItemList(View view) {
        Log.d(LOG_TAG, "callItemList: ");
        Intent intent = new Intent(this, SecondActivity.class);
        startActivityForResult(intent, TEXT_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == TEXT_REQUEST){
            if(resultCode == RESULT_OK){
                String reply = data.getStringExtra(SecondActivity.EXTRA_REPLY);
                for(TextView t: tList){
                    if(t.getText().toString().equals("")){
                        t.setText(reply);
                        break;
                    }
                }
            }
        }
    }

    public void searchLocation(View view) {
        String loc = e1.getText().toString();
        Uri addressUri = Uri.parse("geo:0,0?q=" + loc);
        Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("StoreLocatorIntent","Can't handle this intent!");
        }
    }
}
