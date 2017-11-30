package com.visiostudios.tvanywhere;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

        // Setting up the Custom Toolbar for the Main Activity
        Toolbar myToolbar = (Toolbar) findViewById(R.id.grayBar); // Makes the grayBar into a Toolbar Variable
        setSupportActionBar(myToolbar); // Sets Toolbar Variable into the SupportActionBar of the App
        getSupportActionBar().setLogo(R.mipmap.ic_tv_anywhere); // Sets what Image the Logo will be
        getSupportActionBar().setDisplayUseLogoEnabled(true); // Displays The Logo
        getSupportActionBar().setTitle(" TV Anywhere"); // Sets The Title Name on top

        // Using the function to get URL of the LiveTV, and then declare them as a JSON Object
        JSONObject liveTV = getLiveTV();
        // Getting the Channel List, and setting a variable for each list
        channel3 = getChannelList(liveTV , "tv3");
        channel4 = getChannelList(liveTV , "bravo");
        channel11 = getChannelList(liveTV , "theedgetv");

        // Calling all the Now Playing Text Views in the XML to Java
        TextView nowPlayingListC3 = (TextView)findViewById(R.id.nowPlayingListC3);
        TextView nowPlayingListC4 = (TextView)findViewById(R.id.nowPlayingListC4);
        TextView nowPlayingListC11 = (TextView)findViewById(R.id.nowPlayingListC11);

        // Calling all the Next Text Views in the XML to Java
        TextView nextListC3 = (TextView)findViewById(R.id.nextListC3);
        TextView nextListC4 = (TextView)findViewById(R.id.nextListC4);
        TextView nextListC11 = (TextView)findViewById(R.id.nextListC11);

        // Changes the Text inside the Main Activity
        // Putting the first item in the JSON Array of Now Playing to the Main Activity
        nowPlayingListC3.setText(getNowPlaying(liveTV , "tv3" , 0)); // (0 means the first/latest title/name of the series playing)
        nowPlayingListC4.setText(getNowPlaying(liveTV , "bravo" , 0));
        nowPlayingListC11.setText(getNowPlaying(liveTV , "theedgetv" , 0));
        // Putting the second item in the JSON Array of what's next of the current series playing to the Main Activity (this means 1)
        nextListC3.setText(getNowPlaying(liveTV , "tv3" , 1));
        nextListC4.setText(getNowPlaying(liveTV , "bravo" , 1));
        nextListC11.setText(getNowPlaying(liveTV , "theedgetv" , 1));

        // Sending JSON Array List, Video Stream Link, and The Channel Title to the other Activity
        ImageButton buttonC3 = (ImageButton)findViewById(R.id.buttonC3);
        buttonC3.setOnClickListener(new View.OnClickListener() { // Listens when the user clicks on the image button
            @Override
            public void onClick(View view) {
                launchActivity(channel3 , "http://players.brightcove.net/3812193411001/ryftAvCY_default/index.html?videoId=5455129193001&autoplay=true"
                        ,"channel3"); // Sends all the data taken to the next activity
            }
        });

        ImageButton buttonC4 = (ImageButton)findViewById(R.id.buttonC4);
        buttonC4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchActivity(channel4 , "http://players.brightcove.net/3812193411001/ryftAvCY_default/index.html?videoId=5451543064001&autoplay=true"
                        , "channel4");
            }
        });

        ImageButton buttonC11 = (ImageButton)findViewById(R.id.buttonC11);
        buttonC11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchActivity(channel11 , "http://players.brightcove.net/3812193411001/ryftAvCY_default/index.html?videoId=5455128122001&autoplay=true"
                        , "channel11");
        }
        });

    }

    private JSONObject getLiveTV () {
        JSONObject response = null;
        try {
            URL url = new URL("http://now-api.mediaworks.nz/now-api/v3/live-epg"); // Specifying the URL taken
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // Connects to the internet
            conn.setRequestMethod("GET"); // Specifies the Request Method
            InputStream in = new BufferedInputStream(conn.getInputStream()); // Reads the response
            // Converts the stream data into string, which can be used to compare to other objects
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

    // Function to get the Format and the Date to be used in the List Activity
    private ArrayList<String> getChannelList (JSONObject jsonList , String channel) {
        ArrayList<String> response = new ArrayList<String>();
        SimpleDateFormat dF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"); // Date Format of the JSON List
        SimpleDateFormat dF2 = new SimpleDateFormat("hh:mma"); // Date Format of the Start Date
        dF.setTimeZone(TimeZone.getTimeZone("GMT")); // Declaring the Timezone
        dF2.setTimeZone(TimeZone.getTimeZone("Pacific/Auckland")); // Declaring The Specific Tiemzone
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
    // Function to get the Now Playing from the JSON List
    private String getNowPlaying (JSONObject jsonList , String channel , int index) {
        String playing = "";
        // A try catch and an inside for loop to get the JSON List's Title
        try {
            JSONArray jsonArray = jsonList.getJSONArray("channels"); // Specifies the Array used, in this case channels
            for (int x = 0; x < jsonArray.length(); x++) {
                if (jsonArray.getJSONObject(x).getString("title").equals(channel)) {
                    JSONObject channelObject = jsonArray.getJSONObject(x);
                    JSONArray broadcasts = channelObject.getJSONArray("broadcasts");
                    playing = broadcasts.getJSONObject(index).getString("title"); // Take the name of the Channel
                }
            }
        } catch (Exception e) {
            // Return Nothing if there's an error
            e.printStackTrace();
        }
        return playing; // Send the name of the Channel
    }

    // Function that identifies if the ArrayList is either Channel 3, Channel 4, or Channel 11
    private void launchActivity(ArrayList<String> schedule , String playUrl , String channel) {
        Intent intent = new Intent(MainActivity.this, ListActivity.class); // Declaring what Activities the Intent should move
        intent.putStringArrayListExtra("schedule", schedule); // Sends the Schedule to the next activity
        intent.putExtra("playUrl" , playUrl); // Sends the Stream URL to List Activity
        intent.putExtra("channelName" , channel); // Sends the Channel name to List Activity
        startActivity(intent); // Switches to the Next Activity
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left); // Sliding Animation
    }
}