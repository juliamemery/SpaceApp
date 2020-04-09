package com.cs580.android.spaceapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SpaceAppMain extends AppCompatActivity {

    private Button mAPODbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.ship_sound);

        mAPODbutton = findViewById(R.id.APOD);
        mAPODbutton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SpaceAppMain.this, APOD.class );
                startActivityForResult(intent, 0);
                mp.start();
            }
        });
    }


}
