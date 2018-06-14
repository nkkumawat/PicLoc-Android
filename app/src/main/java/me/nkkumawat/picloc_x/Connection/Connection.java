package me.nkkumawat.picloc_x.Connection;

import org.json.JSONObject;

import me.nkkumawat.picloc_x.Models.PictureRequest;
import me.nkkumawat.picloc_x.Models.ResponseFromServer;
import me.nkkumawat.picloc_x.Models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by sonu on 14/6/18.
 */

public interface Connection {
    @POST("/login")
    Call<ResponseFromServer>  Login(@Body User user);
    @POST("/getnearestpictures")
    Call<ResponseFromServer>  GetPictures(@Body PictureRequest pictureRequest);

//    @GET("movie/{id}")
//    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
}
