package com.visiostudios.tvanywhere;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    // Channel JSON Array Lists
    private ArrayList<String> channel3 = new ArrayList<String>();
    private ArrayList<String> channel4 = new ArrayList<String>();
    private ArrayList<String> channel11 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);

        JSONObject liveTV = getLiveTV();
        channel3 = getChannelList(liveTV , "tv3");
        channel4 = getChannelList(liveTV , "bravo");
        channel11 = getChannelList(liveTV , "theedgetv");

        TextView nowPlayingListC3 = (TextView)findViewById(R.id.nowPlayingListC3);
        TextView nowPlayingListC4 = (TextView)findViewById(R.id.nowPlayingListC4);
        TextView nowPlayingListC11 = (TextView)findViewById(R.id.nowPlayingListC11);

        TextView nextListC3 = (TextView)findViewById(R.id.nextListC3);
        TextView nextListC4 = (TextView)findViewById(R.id.nextListC4);
        TextView nextListC11 = (TextView)findViewById(R.id.nextListC11);

        nowPlayingListC3.setText(getNowPlaying(liveTV , "tv3" , 0));
        nowPlayingListC4.setText(getNowPlaying(liveTV , "bravo" , 0));
        nowPlayingListC11.setText(getNowPlaying(liveTV , "theedgetv" , 0));

        nextListC3.setText(getNowPlaying(liveTV , "tv3" , 1));
        nextListC4.setText(getNowPlaying(liveTV , "bravo" , 1));
        nextListC11.setText(getNowPlaying(liveTV , "theedgetv" , 1));


        // Sending JSON to the other Activity
        ImageButton buttonC3 = (ImageButton)findViewById(R.id.buttonC3);
        buttonC3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchActivity(channel3 , "channel3");
            }
        });

        ImageButton buttonC4 = (ImageButton)findViewById(R.id.buttonC4);
        buttonC4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchActivity(channel4 , "channel4");
            }
        });

        ImageButton buttonC11 = (ImageButton)findViewById(R.id.buttonC11);
        buttonC11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchActivity(channel11 , "channel11");
        }
        });

    }

    private JSONObject getLiveTV () {
        JSONObject response = null;
        try {
            URL url = new URL("http://now-api.mediaworks.nz/now-api/v3/live-epg");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String jsonStr = convertStreamToString(in);
            if (jsonStr != null) {
                response = new JSONObject(jsonStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    // Format of the Date Shown in the Next Activity
    private ArrayList<String> getChannelList (JSONObject jsonList , String channel) {
        ArrayList<String> response = new ArrayList<String>();
        SimpleDateFormat dF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        SimpleDateFormat dF2 = new SimpleDateFormat("hh:mma");
        dF.setTimeZone(TimeZone.getTimeZone("GMT"));
        dF2.setTimeZone(TimeZone.getTimeZone("Pacific/Auckland"));
        try {
            JSONArray jsonArray = jsonList.getJSONArray("channels");
            for (int x = 0; x < jsonArray.length(); x++) {
                if (jsonArray.getJSONObject(x).getString("title").equals(channel)) {
                    JSONObject channelObject = jsonArray.getJSONObject(x);
                    JSONArray broadcasts = channelObject.getJSONArray("broadcasts");
                    for (int y = 0; y <  broadcasts.length(); y++) {
                        Date start = dF.parse(broadcasts.getJSONObject(y).getString("startDate"));
                        response.add(dF2.format(start) + " - " + broadcasts.getJSONObject(y).getString("title"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private String getNowPlaying (JSONObject jsonList , String channel , int index) {
        String playing = "";
        try {
            JSONArray jsonArray = jsonList.getJSONArray("channels");
            for (int x = 0; x < jsonArray.length(); x++) {
                if (jsonArray.getJSONObject(x).getString("title").equals(channel)) {
                    JSONObject channelObject = jsonArray.getJSONObject(x);
                    JSONArray broadcasts = channelObject.getJSONArray("broadcasts");
                    playing = broadcasts.getJSONObject(index).getString("title");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playing;
    }


    private void launchActivity(ArrayList<String> schedule , String playUrl) {
        Intent intent = new Intent(MainActivity.this, ListActivity.class);
        intent.putStringArrayListExtra("schedule", schedule);
        intent.putExtra("playUrl" , playUrl);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
}