package com.fabrizziochavez.apropo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fabrizziochavez.apropo.model.MensajeModel;
import com.fabrizziochavez.apropo.model.RespuestaLogin;
import com.fabrizziochavez.apropo.model.UsuarioModel;
import com.fabrizziochavez.apropo.services.RestClient;
import com.fabrizziochavez.apropo.services.RestInterface;
import com.fabrizziochavez.apropo.services.SessionManager;
import com.fabrizziochavez.apropo.services.SharedTools;
import com.fabrizziochavez.apropo.services.SyncService;
import com.fabrizziochavez.apropo.utils.AnimationUtils;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button login;
    TextInputEditText username;
    TextInputLayout usernamelayout;
    TextInputEditText password;
    TextInputLayout passwordlayout;
    TextView forgot;
    TextView copyright;
    ImageView logo;
    private MaterialDialog progress;
    private SessionManager session;
    private ProgressReceiver progressReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getSupportActionBar().hide();
        session = new SessionManager(getApplicationContext());
        if(session.isLoggedIn()){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(intent);
            finish();
        }else {
            login = (Button) findViewById(R.id.btn_login);
            login.setOnClickListener(this);
            forgot = (TextView) findViewById(R.id.forgot_password);
            forgot.setOnClickListener(this);
            username = (TextInputEditText) findViewById(R.id.username);
            usernamelayout = (TextInputLayout) findViewById(R.id.text_username_layout);
            password = (TextInputEditText) findViewById(R.id.password);
            passwordlayout = (TextInputLayout) findViewById(R.id.text_password_layout);
            copyright = (TextView) findViewById(R.id.copyright);
            logo = (ImageView) findViewById(R.id.logo);
            AnimationUtils.makeRoundCorner(login, Color.parseColor("#0696DC"), 15, 500);
            AnimationUtils.showMe(login, 600);
            AnimationUtils.enterTop(logo, 300);
            AnimationUtils.showMe(username, 500);
            AnimationUtils.showMe(password, 500);
            AnimationUtils.enterLeft(forgot, 500);
            AnimationUtils.enterBottom(copyright, 400);
        }
    }
    @Override
    public void onResume() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(SyncService.actionFinish);
        filter.addAction(SyncService.actionError);
        progressReceiver = new ProgressReceiver();
        getApplicationContext().registerReceiver(progressReceiver, filter);
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        getApplicationContext().unregisterReceiver(progressReceiver);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_login:
                IniciarSesion();
                break;
            case R.id.forgot_password:
                Toast.makeText(this, "Olvidar Password", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    private void IniciarSesion(){
        progress = new MaterialDialog.Builder(LoginActivity.this)
                .title("Iniciando sesión")
                .content("Espere por favor")
                .progress(true, 0)
                .show();
        RestInterface api = SharedTools.call();
        Call<RespuestaLogin> call = api.Login(username.getText().toString(), password.getText().toString());
        call.enqueue(new Callback<RespuestaLogin>() {
            @Override
            public void onResponse(Response<RespuestaLogin> response, Retrofit retrofit) {
                progress.dismiss();
                if (response.isSuccess()){
                    RespuestaLogin msj = response.body();
                    if(msj.getCoderror() == 1){
                        session.createLoginSession(
                                msj.getUsuario().getCodigo(),
                                msj.getUsuario().getNombre(),
                                msj.getUsuario().getZonaid(),
                                msj.getUsuario().getZonanombre(),
                                msj.getUsuario().getEmail());
                        SyncService.activity = LoginActivity.this;
                        SyncService.newLogin = true;
                        syncData("Sincronizando", 1);
                         //syncData("Sincronizando", 1);
                    }else{
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("Error")
                                .setMessage(msj.getMensaje())
                                .setPositiveButton("Aceptar", null)
                                .show();
                    }

                }else{
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Error")
                            .setMessage("error desconocido")
                            .setPositiveButton("Aceptar", null)
                            .show();
                }
            }
            @Override
            public void onFailure(Throwable t) {
                progress.dismiss();
                SharedTools.showError(LoginActivity.this, t);
            }
        });
    }
    private void syncData(String message, int actionType){
        progress = new MaterialDialog.Builder(this)
                .title("Sincronizando")
                .content(message)
                .cancelable(false)
                .progress(true, 0)
                .show();

        Intent msgIntent = new Intent(this, SyncService.class);
        msgIntent.putExtra("actionType", actionType);
        this.startService(msgIntent);
    }
    public class ProgressReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            progress.dismiss();
            if(intent.getAction().equals(SyncService.actionFinish)) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                switch (intent.getIntExtra("actionType",0)){
                    case 1://Fin Sync
                        SyncService.newLogin=false;
                        Intent mintent = new Intent(LoginActivity.this, MainActivity.class);
                        LoginActivity.this.startActivity(mintent);
                        finish();
                        break;
                }
            }else if (intent.getAction().equals(SyncService.actionError)){
                SharedTools.errorMessage(LoginActivity.this, message);
            }

        }
    }
}
