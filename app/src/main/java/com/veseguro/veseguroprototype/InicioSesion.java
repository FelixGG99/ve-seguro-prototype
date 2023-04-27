package com.veseguro.veseguroprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


public class InicioSesion extends AppCompatActivity {

    Button iraMapa;
    final static String packagename = "com.veseguro.veseguroprototype";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.activity_inicio_sesion);

        iraMapa = (Button)findViewById(R.id.IniciarSesion);

        iraMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent();
                i.setClassName(packagename,"com.veseguro.veseguroprototype.Mapa");
                startActivity(i);
            }
        });
    }
}