package me.nkkumawat.picloc_x;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreen extends AppCompatActivity {


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SharedPreferences prefs = this.getSharedPreferences("PROLOCX", Context.MODE_PRIVATE);
        String user_id = prefs.getString("User_id", null);
        if(user_id != null) {
            startActivity(new Intent(SplashScreen.this , Home.class));
        }else {
            startActivity(new Intent(SplashScreen.this , Login.class));
        }
        finish();
    }




}
