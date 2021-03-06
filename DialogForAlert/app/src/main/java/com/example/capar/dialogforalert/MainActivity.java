package com.example.capar.dialogforalert;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickShowAlert(View view) {
        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(MainActivity.this);
        //Set the dialog title and message
        myAlertBuilder.setTitle(R.string.alertbuilder_title);
        myAlertBuilder.setMessage(R.string.alertbuilder_message);
        //Add the dialog buttons
        myAlertBuilder.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //User clicked OK Button
                Toast.makeText(getApplicationContext(), "Pressed OK", Toast.LENGTH_SHORT).show();
            }
        });
        myAlertBuilder.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //User canceled the dialog
                Toast.makeText(getApplicationContext(), "Pressed Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        //Create and show the AlertDialog
        myAlertBuilder.show();
    }
}
