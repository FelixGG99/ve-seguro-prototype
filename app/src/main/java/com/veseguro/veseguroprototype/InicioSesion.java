package com.veseguro.veseguroprototype;

import static com.veseguro.veseguroprototype.ProviderType.FACEBOOK;
import static com.veseguro.veseguroprototype.ProviderType.GOOGLE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class InicioSesion extends AppCompatActivity {

    Button btnToMap;
    Button btnLoginGoogle;
    Button btnLoginFacebook;

    TextView tvRegistro;

    // ID to pick up results from starting google login intent
    private final int GOOGLE_LOGIN_INTENT_ID = 100;

    private CallbackManager callbackManager = CallbackManager.Factory.create();
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
        btnToMap            = (Button)findViewById(R.id.IniciarSesion);
        btnLoginGoogle      = (Button)findViewById(R.id.btnLoginGoogle);
        btnLoginFacebook    = (Button)findViewById(R.id.btnLoginFacebook);
        tvRegistro          = (TextView) findViewById(R.id.RegisterLink);

        // Button Listeners
        btnToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClassName(packagename,"com.veseguro.veseguroprototype.Mapa");
                startActivity(i);
            }
        });
        tvRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClassName(packagename,"com.veseguro.veseguroprototype.Registro");
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

        // Login con Facebook
        btnLoginFacebook.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Configuración
                //<String> perms = Arrays.asList("email");
                //LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        if(loginResult != null){
                            AccessToken token = loginResult.getAccessToken();
                            AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
                            firebaseLoginWithCredential(credential, GOOGLE);
                        }
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {
                        showLoginErrorAlert(FACEBOOK);
                    }
                });
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        // Desencadenar llamada a callbacks de Facebook
        callbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);

        // Get results for Google Login
        if(requestCode == GOOGLE_LOGIN_INTENT_ID){
            Task task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = (GoogleSignInAccount) task.getResult(ApiException.class);
                if(account != null) {
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    firebaseLoginWithCredential(credential, GOOGLE);
                }
            } catch (Throwable e) {
                showLoginErrorAlert(GOOGLE);
                throw new RuntimeException(e);
            }
        }

        // Get results for Google Login
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

    private String firebaseLoginWithCredential(AuthCredential credential, ProviderType provider) {
        if (credential == null)
            return null;

        final Boolean[] loginSucessful = {false};
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                loginSucessful[0] = true;
                if (task.isSuccessful()) {
                    Intent i = new Intent();
                    i.setClassName(packagename, "com.veseguro.veseguroprototype.Mapa");
                    startActivity(i);
                    return;
                }
                showLoginErrorAlert(provider);
            }
        });
        return loginSucessful[0] ? "EXITO" : null;
    }
}