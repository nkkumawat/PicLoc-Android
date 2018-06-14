package me.nkkumawat.picloc_x;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import me.nkkumawat.picloc_x.Utils.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SinglePictureDetails extends AppCompatActivity implements OnMapReadyCallback {
    private ImageView picture_iv;
    SupportMapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_picture_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle b = getIntent().getExtras();
        String pic_url = "";
        if(b != null) {
            pic_url = b.getString("pic_url");
        }
        picture_iv = (ImageView) findViewById(R.id.picture_iv);

        Picasso.get().load(Constants.BASE_URL1 + pic_url).into(picture_iv);


        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        picture_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

                        googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(37.4233438, -122.0728817))
                                .title("LinkedIn")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                        googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(37.4629101,-122.2449094))
                                .title("Facebook")
                                .snippet("Facebook HQ: Menlo Park"));

                        googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(37.3092293, -122.1136845))
                                .title("Apple"));

                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.4233438, -122.0728817), 10));
                    }
                });
            }

        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.4233438, -122.0728817))
                .title("LinkedIn")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.4629101,-122.2449094))
                .title("Facebook")
                .snippet("Facebook HQ: Menlo Park"));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.3092293, -122.1136845))
                .title("Apple"));

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.4233438, -122.0728817), 10));
    }
}
