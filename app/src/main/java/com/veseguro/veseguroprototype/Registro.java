package com.veseguro.veseguroprototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
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
                    firebaseRegisterWithEmailAndPassword(edtCorreo.getText().toString().trim(), edtContraseña.getText().toString().trim());
                }
            }
        });
    }

    private Boolean validateFields(){

        if(edtNombre.getText().toString().trim().length() == 0){
            String msg = "El campo \"Nombre\" está vacío. todos los datos deben estar completos para registrarse.";
            showMissingFieldAlert(msg);
            return false;
        }

        if(edtApellidos.getText().toString().trim().length() == 0){
            String msg = "El campo \"Apellidos\" está vacío. todos los datos deben estar completos para registrarse.";
            showMissingFieldAlert(msg);
            return false;
        }

        if(edtFechaNacimiento.getText().toString().trim().length() == 0){
            String msg = "El campo \"Fecha de Nacimiento\" está vacío. todos los datos deben estar completos para registrarse.";
            showMissingFieldAlert(msg);
            return false;
        }

        if(edtCorreo.getText().toString().trim().length() == 0){
            String msg = "El campo \"Correo electrónico\" está vacío. todos los datos deben estar completos para registrarse.";
            showMissingFieldAlert(msg);
            return false;
        }

        if(edtContraseña.getText().toString().trim().length() < 6){
            String msg = "El campo \"Contraseña\" está vacío. La contraseña debe ser al menos de 6 caracteres de largo para registrarse.";
            showMissingFieldAlert(msg);
            return false;
        }
        return true;
    }

    private void showMissingFieldAlert(String error){
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    private void firebaseRegisterWithEmailAndPassword(String email, String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getBaseContext(), "Cuenta creada exitosamente. Inicia sesión con tus nuevas credenciales.", Toast.LENGTH_LONG).show();
                    Intent i = new Intent();
                    i.setClassName(getString(R.string.package_name), "com.veseguro.veseguroprototype.InicioSesion");
                    startActivity(i);
                    return;
                }
                Toast.makeText(getBaseContext(), task.getResult().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}