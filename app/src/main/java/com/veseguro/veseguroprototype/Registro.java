package com.veseguro.veseguroprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class Registro extends AppCompatActivity {

    Button btnBack;
    Button btnRegister;

    // EditText Views
    EditText edtNombre;
    EditText edtApellidos;
    EditText edtFechaNacimiento;
    EditText edtCorreo;
    EditText edtContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Declare buttons
        btnBack = (Button) findViewById(R.id.back_btn);
        btnRegister = (Button) findViewById(R.id.register_btn);

        // Declare EditText
        edtNombre           = (EditText)findViewById(R.id.editTextNombre);
        edtApellidos        = (EditText)findViewById(R.id.editTextApellidos);
        edtFechaNacimiento  = (EditText)findViewById(R.id.editTextDate);
        edtCorreo           = (EditText)findViewById(R.id.editTextTextEmailAddress);
        edtContraseña       = (EditText)findViewById(R.id.editTextTextPassword);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClassName(getString(R.string.package_name),"com.veseguro.veseguroprototype.InicioSesion");
                startActivity(i);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateFields()) {

                }
            }
        });
    }

    private Boolean validateFields(){

        if(edtNombre.getText().toString().trim().length() == 0){
            showMissingFieldAlert("Nombre");
            return false;
        }

        if(edtApellidos.getText().toString().trim().length() == 0){
            showMissingFieldAlert("Apellidos");
            return false;
        }

        if(edtFechaNacimiento.getText().toString().trim().length() == 0){
            showMissingFieldAlert("Fecha de Nacimiento");
            return false;
        }

        if(edtCorreo.getText().toString().trim().length() == 0){
            showMissingFieldAlert("Correo electrónico");
            return false;
        }

        if(edtContraseña.getText().toString().trim().length() == 0){
            showMissingFieldAlert("Contraseña");
            return false;
        }
        return true;
    }

    private void showMissingFieldAlert(String fieldName){
        Toast.makeText(this, "El campo \""+fieldName+"\" está vacío. Es necesario llenar todos los datos antes de registrarse.", Toast.LENGTH_LONG).show();
    }
}