package com.visiostudios.tvanywhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Calling the Intent from MainActivity
        Intent intent = getIntent();
        final String channelName = intent.getStringExtra("channelName");

        // Toolbar Customization
        Toolbar myToolbar = (Toolbar) findViewById(R.id.grayBar2);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_tv_anywhere);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Displays The Back Button
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_button); // Uses my custom Back button instead of the Default

        // Declaring the XML/Design variables
        ImageButton liveStreamButton = (ImageButton) findViewById(R.id.liveStreamButton);
        ListView scheduleListView = (ListView) findViewById(R.id.scheduleListView);

        // Checks what Channel No. the Main Activity sent the List Activity
        // After checking, it then changes the title and the color of the image depending on that Channel No.
        if (channelName.equals("channel3")) {
            getSupportActionBar().setTitle(" Three TV Guide");
            liveStreamButton.setImageResource(R.drawable.television_lb);
            liveStreamButton.setBackgroundResource(R.color.secondaryColorPrimaryLight); // Changes the Color of the Button
        } else if (channelName.equals("channel4")) {
            getSupportActionBar().setTitle(" Bravo TV Guide");
            liveStreamButton.setImageResource(R.drawable.television_b);
            liveStreamButton.setBackgroundResource(R.color.secondaryColorPrimary);
        } else {
            getSupportActionBar().setTitle(" Edge TV Guide");
            liveStreamButton.setImageResource(R.drawable.television_db);
            liveStreamButton.setBackgroundResource(R.color.secondaryColorPrimaryDark);
        }
        // Gets the Schedule from the Main Activity's Intent
        ArrayList<String> schedule = intent.getStringArrayListExtra("schedule");
        // This changes my custom List on the XML into the JSON Array list
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.custom_list_view_1, schedule);
        scheduleListView.setAdapter(arrayAdapter);

        final String playUrl = getIntent().getStringExtra("playUrl");

        liveStreamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, StreamActivity.class);
                intent.putExtra("playUrl", playUrl);
                intent.putExtra("channelName" , channelName);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
