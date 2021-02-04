package com.cst2335.niki0007;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private boolean Switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);

        //onClick for TOAST button
        button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toastMessage = MainActivity.this.getResources().getString(R.string.toast_message);
                Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_LONG).show();
            }
        });

        //onChecked for SNACKBAR switch
        Switch s = (Switch)findViewById(R.id.switch1);
        s.setOnCheckedChangeListener((notChecked, isChecked) -> {
            if(isChecked){
                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.switch1), getString(R.string.snackbarTextOn), Snackbar.LENGTH_LONG);
                mySnackbar.setAction("Undo", click -> s.setChecked(Switch));
                mySnackbar.show();
                }else if(!isChecked){
                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.switch1), getString(R.string.snackbarTextOff), Snackbar.LENGTH_LONG);
                mySnackbar.setAction("Undo", click -> s.setChecked(!Switch));
                mySnackbar.show();
            }
        });
    }
}

