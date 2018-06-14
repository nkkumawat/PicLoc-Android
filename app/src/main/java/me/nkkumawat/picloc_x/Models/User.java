package me.nkkumawat.picloc_x.Models;

import android.graphics.Movie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sonu on 14/6/18.
 */

public class User {
    @SerializedName("username")
    public String  username;
    @SerializedName("password")
    public String password;

    public User(String username , String password) {
        this.username = username;
        this.password = password;
    }
}
