package com.visiostudios.tvanywhere;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intent = getIntent();

        // Declaring the XML/Design variables
        TextView tvGuideName = (TextView) findViewById(R.id.tvGuideName);
        ImageButton liveStreamButton = (ImageButton) findViewById(R.id.liveStreamButton);
        ListView scheduleListView = (ListView) findViewById(R.id.scheduleListView);

        // Converting XML Color Styles to an Integer
        ColorDrawable secondaryColorPrimaryLight = new ColorDrawable(ContextCompat.getColor(this, R.color.secondaryColorPrimaryLight));
        ColorDrawable secondaryColorPrimary = new ColorDrawable(ContextCompat.getColor(this, R.color.secondaryColorPrimary));
        ColorDrawable secondaryColorPrimaryDark = new ColorDrawable(ContextCompat.getColor(this, R.color.secondaryColorPrimaryDark));

        String channelName = intent.getStringExtra("channelName");

        if (channelName.equals("channel3")) {
            tvGuideName.setText("Three TV Guide");
            liveStreamButton.setImageResource(R.drawable.television_lb);
            liveStreamButton.setBackgroundResource(R.color.secondaryColorPrimaryLight);
//            scheduleListView.setDivider(secondaryColorPrimaryLight);
        } else if (channelName.equals("channel4")) {
            tvGuideName.setText("Bravo TV Guide");
            liveStreamButton.setImageResource(R.drawable.television_b);
            liveStreamButton.setBackgroundResource(R.color.secondaryColorPrimary);
//            scheduleListView.setDivider(secondaryColorPrimary);
        } else {
            tvGuideName.setText("The Edge TV Guide");
            liveStreamButton.setImageResource(R.drawable.television_db);
            liveStreamButton.setBackgroundResource(R.color.secondaryColorPrimaryDark);
//            scheduleListView.setDivider(secondaryColorPrimaryDark);
        }

        ArrayList<String> schedule = intent.getStringArrayListExtra("schedule");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.custom_list_view_1, schedule);
        scheduleListView.setAdapter(arrayAdapter);

        final String playUrl = getIntent().getStringExtra("playUrl");

        liveStreamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, StreamActivity.class);
                intent.putExtra("playUrl", playUrl);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
