package com.jorgesys.playmedia;

import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    private final static String TAG = "MainActivity";
    private MediaPlayer mediaPlayer;
    private int[] sounds = {R.raw.one,R.raw.two, R.raw.tree, R.raw.four};
    private int sound;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        mediaPlayer = MediaPlayer.create(this, sounds[0]);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.start();
        showText("Playing: " + sounds[sound]);

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        play();
    }

    private void play() {
        sound++;
        if (sounds.length <= sound){
            showText("End...");
            return;
        }

        AssetFileDescriptor afd = this.getResources().openRawResourceFd(sounds[sound]);
        showText("Playing: " + sounds[sound]);

        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
            afd.close();
        }
        catch (IllegalArgumentException e) {
            Log.e(TAG, "IllegalArgumentException Unable to play audio : " + e.getMessage());
        }
        catch (IllegalStateException e) {
            Log.e(TAG, "IllegalStateException Unable to play audio : " + e.getMessage());
        }
        catch (IOException e) {
            Log.e(TAG, "IOException Unable to play audio : " + e.getMessage());
        }
    }


    private void showText(String s){
        textView.setTextColor(getRandomColor());
        s += "\n" + textView.getText();
        textView.setText(s);
    }

    private int getRandomColor(){
        int r = (int) (0xff * Math.random());
        int g = (int) (0xff * Math.random());
        int b = (int) (0xff * Math.random());
        return Color.rgb(r, g, b);
    }
}