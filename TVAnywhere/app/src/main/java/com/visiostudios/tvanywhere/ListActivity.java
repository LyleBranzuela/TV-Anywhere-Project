package com.visiostudios.tvanywhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intent = getIntent();
        ArrayList<String> schedule = intent.getStringArrayListExtra("schedule");
        String playUrl = intent.getStringExtra("playUrl");

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(schedule.toString());
    }
    private void finishIntent() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
