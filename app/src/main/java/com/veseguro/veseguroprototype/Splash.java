package com.veseguro.veseguroprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TimerTask tare = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, SplashVacia.class);
                startActivity(intent);
                finish();
            }
        };

        Timer tempo = new Timer();
        tempo.schedule(tare, 10000);
    }
}