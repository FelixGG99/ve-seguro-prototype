package com.veseguro.veseguroprototype;

import static com.veseguro.veseguroprototype.ProviderType.GOOGLE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

public class InicioSesion extends AppCompatActivity {

    Button btnToMap;
    Button btnLoginGoogle;

    // ID to pick up results from starting google login intent
    private final int GOOGLE_LOGIN_INTENT_ID = 100;
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

        // Declare buttons
        btnToMap        = (Button)findViewById(R.id.IniciarSesion);
        btnLoginGoogle  = (Button)findViewById(R.id.btnLoginGoogle);

        // Button Listeners
        btnToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClassName(packagename,"com.veseguro.veseguroprototype.Mapa");
                startActivity(i);
            }
        });

        btnLoginGoogle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Configuración
                GoogleSignInOptions googleConf = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                        requestIdToken(getString(R.string.default_web_client_id)).
                        requestEmail().
                        build();
                GoogleSignInClient googleClient = GoogleSignIn.getClient(getBaseContext(), googleConf);
                // Salir de cualquier cuenta que pueda estar loggeada
                googleClient.signOut();
                startActivityForResult(googleClient.getSignInIntent(), GOOGLE_LOGIN_INTENT_ID);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Get results for Google Login
        if(requestCode == GOOGLE_LOGIN_INTENT_ID){
            Task task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = (GoogleSignInAccount) task.getResult(ApiException.class);
                if(account != null) {
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()) {
                                Intent i = new Intent();
                                i.setClassName(packagename, "com.veseguro.veseguroprototype.Mapa");
                                startActivity(i);
                                return;
                            }
                            showLoginErrorAlert(GOOGLE);
                        }
                    });
                }
            } catch (Throwable e) {
                showLoginErrorAlert(GOOGLE);
                throw new RuntimeException(e);
            }
        }
    }

    private void showLoginErrorAlert(ProviderType provider){

        String errorMessage = new String();
        switch (provider) {
            case GOOGLE:
                errorMessage = getString(R.string.GoogleLoginErrorMsg);
                break;
            case FACEBOOK:
                errorMessage = getString(R.string.FacebookLoginErrorMsg);
                break;
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }
}