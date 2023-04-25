package com.kevsray.veseguro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button irInicioSesion;
    final static String packagename = "com.kevsray.veseguro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_main);
        irInicioSesion = (Button)findViewById(R.id.BotonInicio);

        irInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent();
                i.setClassName(packagename,"com.kevsray.veseguro.InicioSesion");
                startActivity(i);
            }
        });

    }
}
