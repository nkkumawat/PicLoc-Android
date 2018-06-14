package me.nkkumawat.picloc_x.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sonu on 14/6/18.
 */

public class Pictures {
    @SerializedName("id")
    public String  id;
    @SerializedName("user_id")
    public String user_id;
    @SerializedName("pic_url")
    public String  pic_url;
    @SerializedName("pic_latitude")
    public String latitude;
    @SerializedName("pic_longitude")
    public String  longitude;
    @SerializedName("pic_description")
    public String  pic_description;
    @SerializedName("date_and_time")
    public String date_and_time;
    public Pictures(String id , String uid , String url , String lati , String longi , String desc , String datime) {
        this.id = id;
        this.user_id = uid;
        this.latitude = lati;
        this.longitude = longi;
        this.pic_url = url;
        this.pic_description = desc;
        this.date_and_time = datime;
    }


}
