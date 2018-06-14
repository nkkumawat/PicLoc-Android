package me.nkkumawat.picloc_x;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;



import me.nkkumawat.picloc_x.Connection.Connection;
import me.nkkumawat.picloc_x.Connection.RetrofitClient;
import me.nkkumawat.picloc_x.Models.ResponseFromServer;
import me.nkkumawat.picloc_x.Models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private Button login;
    private EditText username_et , password_et;
    ProgressDialog pd ;
    SharedPreferences sharedPreferences;
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pd = new ProgressDialog(Login.this);
        pd.setMessage("loading");
        pd.setCancelable(false);
        sharedPreferences = getSharedPreferences("PROLOCX", Context.MODE_PRIVATE);
        login = (Button) findViewById(R.id.login_btn);
        username_et = (EditText)findViewById(R.id.username_et);
        password_et = (EditText)findViewById(R.id.password_et);
        linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = username_et.getText().toString();
                String password = password_et.getText().toString();
                if(username.equals("") || password.equals("")) {
                    Snackbar snackbar = Snackbar.make(linearLayout, "Fill All Feilds", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else {
                    pd.show();
                    Connection connection = RetrofitClient.getClient().create(Connection.class);
                    Call<ResponseFromServer> call = connection.Login(new User(username , password));
                    call.enqueue(new Callback<ResponseFromServer>() {
                        @Override
                        public void onResponse(Call<ResponseFromServer> call, Response<ResponseFromServer> response) {
                            JsonObject jsonObject = response.body().response;
                            try {
                                JsonObject head = jsonObject.get("head").getAsJsonObject();
                                JsonObject body = jsonObject.get("body").getAsJsonObject();
                                Log.d("Body" , body.toString());
                                int status = head.get("status").getAsInt();
                                if(status == 200) {
                                    JsonArray res = body.getAsJsonArray("res");
                                    JsonObject user = res.get(0).getAsJsonObject();
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Username", user.get("username").getAsString());
                                    editor.putString("User_id", user.get("id").getAsString());
                                    editor.putString("Email", user.get("email").getAsString());
                                    editor.commit();
                                    startActivity(new Intent(Login.this , Home.class));
                                    finish();
                                }else {
                                    String error = body.get("res").getAsString();
                                    Snackbar snackbar = Snackbar.make(linearLayout, error, Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                                pd.dismiss();
                            }catch (Exception e ) {
                                Snackbar snackbar = Snackbar.make(linearLayout, e.toString() + "Try Again", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseFromServer> call, Throwable t) {
                            Log.d("fail" , t.toString());
                        }
                    });

                }
            }
        });
    }
}
