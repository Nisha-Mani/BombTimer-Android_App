package com.nisha.bombtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {
    TextView timeLeftTextBar ;
    MediaPlayer mediaPlayer;
    SeekBar timerSeekBar ;
    Button goButton;
    Boolean isTimerActive = false;
    CountDownTimer countDownTimer;

    public void startTimer(View view){
        goButton = (Button) findViewById(R.id.buttonGo);
        if(isTimerActive){
            resetTimer();
        }
        else {
            isTimerActive=true;
            timerSeekBar.setEnabled(false);
            goButton.setText("Stop!");

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress()*1000+100, 1000) {
                @Override
                public void onTick(long millisUntilFinish) {
                  updateTimer( (int) millisUntilFinish/1000);
                }

                @Override
                public void onFinish() {
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bomb_audio);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();

        }
    }

    public void resetTimer(){
        timeLeftTextBar = (TextView) findViewById(R.id.textViewTimeLeft);
        timeLeftTextBar.setText("00:30");
        timerSeekBar.setProgress(30);
        timerSeekBar.setEnabled(true);
        goButton.setText("go!");
        countDownTimer.cancel();
        isTimerActive=false;
    }
    public void updateTimer(int secsLeft){
        String minsStr="",secsStr="";
        timeLeftTextBar = (TextView) findViewById(R.id.textViewTimeLeft);

        int mins = secsLeft/60;
        int secs = secsLeft - (mins*60);
        if(secs<10){
            secsStr="0"+secs;
        }
        else{
            secsStr=""+secs;
        }
        if(mins<10) {
            minsStr = "0"+mins;
        }
        else{
            minsStr=""+mins;
        }

        timeLeftTextBar.setText(minsStr + ":" + secsStr);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = (SeekBar) findViewById(R.id.seekBarTimer);
        timerSeekBar.setMax(60 * 5);
        timerSeekBar.setProgress(30); // start with 30 secs

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }
}