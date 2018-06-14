package me.nkkumawat.picloc_x.Models;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

/**
 * Created by sonu on 14/6/18.
 */

public class ResponseFromServer {
    @SerializedName("response")
    public JsonObject response;
}
