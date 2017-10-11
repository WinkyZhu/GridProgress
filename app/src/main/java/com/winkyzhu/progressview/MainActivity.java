package com.winkyzhu.progressview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.widget.SeekBar;

import com.winkyzhu.gridprogress.GridProgressBar;

public class MainActivity extends AppCompatActivity {


    private GridProgressBar progressBar1;
    private GridProgressBar progressBar2;
    private GridProgressBar progressBar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar1 = (GridProgressBar) findViewById(R.id.progress1);
        progressBar2 = (GridProgressBar) findViewById(R.id.progress2);
        progressBar3 = (GridProgressBar) findViewById(R.id.progress3);
        AppCompatSeekBar seekBar = (AppCompatSeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               setProgress(progress,progressBar1,progressBar2,progressBar3);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    private void setProgress(int progress,GridProgressBar... bars){
        if (null == bars){
            return;
        }
        for (GridProgressBar bar:
             bars) {
            bar.setProgress(progress);
        }
    }
}
