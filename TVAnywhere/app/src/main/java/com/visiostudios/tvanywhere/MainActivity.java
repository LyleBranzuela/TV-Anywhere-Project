package com.visiostudios.tvanywhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ImageButton buttonC3;
    private ImageButton buttonC4;
    private ImageButton buttonC11;

    private ArrayList<String> channel3 = new ArrayList<String>();
    private ArrayList<String> channel4 = new ArrayList<String>();
    private ArrayList<String> channel11 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        buttonC3 = (ImageButton)findViewById(R.id.buttonC3);
        buttonC3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchActivity(channel3 , "channel3");
            }
        });

        buttonC4 = (ImageButton)findViewById(R.id.buttonC4);
        buttonC4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchActivity(channel4 , "channel4");
            }
        });

        buttonC11 = (ImageButton)findViewById(R.id.buttonC11);
        buttonC11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchActivity(channel11 , "channel11");
            }
        });
    }
    private void launchActivity(ArrayList<String> schedule , String playUrl) {
        Intent intent = new Intent(MainActivity.this, ListActivity.class);
        intent.putStringArrayListExtra("schedule", schedule);
        intent.putExtra("playUrl" , playUrl);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
}