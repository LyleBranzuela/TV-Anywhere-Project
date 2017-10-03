package com.visiostudios.tvanywhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intent = getIntent();
//        finishIntent();

        ListView scheduleListView = (ListView) findViewById(R.id.scheduleListView);

        ArrayList<String> schedule = intent.getStringArrayListExtra("schedule");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, schedule);

        scheduleListView.setAdapter(arrayAdapter);

        String playUrl = intent.getStringExtra("playUrl");

    }
    private void finishIntent() {
//        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
