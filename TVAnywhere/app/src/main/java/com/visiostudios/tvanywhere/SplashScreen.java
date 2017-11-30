package com.visiostudios.tvanywhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SplashScreen extends AppCompatActivity {
    // Creates the Variables for each View
    private TextView splashText;
    private ImageView splashImage;
    private static final String TAG = "SplashScreen";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        // Identifies the Views in the XML
        splashText = (TextView) findViewById(R.id.splashText);
        splashImage = (ImageView) findViewById(R.id.splashImage);
        // Identifiees the Animation in the XML anim Folder
        Animation mySplash = AnimationUtils.loadAnimation(this,R.anim.transition);
        // Starts The Animation
        splashText.startAnimation(mySplash);
        splashImage.startAnimation(mySplash);

        // Transitions from the Splash Screen to the Main Activity
        final Intent mainIntent = new Intent(SplashScreen.this,MainActivity.class);
        Thread timer  = new Thread() {
            public void run () {
                try {
                    sleep(5000); // Sleeps the thread
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(mainIntent); // Fade into The Main Activity
                    finish();
                }
            }
        };
                timer.start(); // Starts the Thread
    }

}
