package me.nkkumawat.picloc_x.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sonu on 14/6/18.
 */

public class PictureRequest {

    @SerializedName("user_id")
    public String user_id;

    @SerializedName("latitude")
    public String latitude;

    @SerializedName("longitude")
    public String  longitude;

    public PictureRequest(String user_id , String latitude , String longitude) {
        this.user_id = user_id;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
