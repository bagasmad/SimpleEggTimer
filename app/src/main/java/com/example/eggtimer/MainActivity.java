package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public MediaPlayer media;
    public TextView timerText;
    public SeekBar seekBar;
    public Button button;
    public Context context;
    int millFuture = 30000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerText = findViewById(R.id.timerText);
        seekBar = findViewById(R.id.seekBar);
        button = findViewById(R.id.button);
        context = this;
        seekBar.setMax(600);
        seekBar.setProgress(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                int minutes = progress/60;
                int seconds = progress - minutes*60;
                setTimerText(minutes,seconds);
                millFuture = progress * 1000;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void setTimerText(int minutes, int seconds)
    {
        if (minutes<10)
        {
            if (seconds<10)
            {
                timerText.setText("0"+minutes+":"+"0"+seconds);
            }
            else
            {
                timerText.setText("0"+minutes+":"+seconds);
            }
        }
        else
        {
            if (seconds<10)
            {
                timerText.setText(minutes+":"+"0"+seconds);
            }
            else
            {
                timerText.setText(minutes+":"+seconds);
            }

        }
    }
    public void Timer(View view)
    {
        seekBar.setEnabled(false);
        button.setEnabled(false);
        new CountDownTimer(millFuture + 1000,1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {
                seekBar.setProgress((int) millisUntilFinished/1000);
                Log.i("Seconds Left",Long.toString(millisUntilFinished/1000));
                int minutes = (int) millisUntilFinished/60000;
                int seconds = (int) (millisUntilFinished - minutes*60000)/1000;
                setTimerText(minutes,seconds);
            }
            public void onFinish() {
                seekBar.setEnabled(true);
                button.setEnabled(true);
                media = MediaPlayer.create(context, R.raw.alarm);
                media.start();

            }
        }.start();
    }
}
