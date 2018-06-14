package me.nkkumawat.picloc_x;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import me.nkkumawat.picloc_x.Adapters.PictureAdapter;
import me.nkkumawat.picloc_x.Connection.Connection;
import me.nkkumawat.picloc_x.Connection.RetrofitClient;
import me.nkkumawat.picloc_x.Models.Pictures;
import me.nkkumawat.picloc_x.Models.ResponseFromServer;
import me.nkkumawat.picloc_x.Models.PictureRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private PictureAdapter pictureAdapter;
    private List<Pictures> picturesList;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pd = new ProgressDialog(Home.this);
        pd.setMessage("loading");
        pd.setCancelable(false);
        pd.show();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        picturesList = new ArrayList<>();
        pictureAdapter = new PictureAdapter(this, picturesList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pictureAdapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        TextView username_et = (TextView) header.findViewById(R.id.username);
        TextView email_et = (TextView) header.findViewById(R.id.email);
        SharedPreferences prefs = this.getSharedPreferences("PROLOCX", Context.MODE_PRIVATE);
        String username = prefs.getString("Username", null);
        String email = prefs.getString("Email", null);
        username_et.setText(username);
        email_et.setText(email);
        getAllImages();
    }


    public void getAllImages() {
        SharedPreferences prefs = this.getSharedPreferences("PROLOCX", Context.MODE_PRIVATE);
        String user_id = prefs.getString("User_id", null);
        if(user_id != null) {
            Connection connection = RetrofitClient.getClient().create(Connection.class);
            Call<ResponseFromServer> call = connection.GetPictures(new PictureRequest(user_id , "22.15" , "45.25"));
            call.enqueue(new Callback<ResponseFromServer>() {
                @Override
                public void onResponse(Call<ResponseFromServer> call, Response<ResponseFromServer> response) {
                    JsonObject jsonObject = response.body().response;
                    try {
                        JsonObject head = jsonObject.get("head").getAsJsonObject();
                        JsonObject body = jsonObject.get("body").getAsJsonObject();
                        Log.d("Body", body.toString());
                        int status = head.get("status").getAsInt();
                        if (status == 200) {
                            JsonArray res = body.getAsJsonArray("res");
                            for(int i =0 ; i < res.size(); i ++) {
                                JsonObject pic = res.get(i).getAsJsonObject();
                                String id = pic.get("id").getAsString();
                                String user_id= pic.get("user_id").getAsString();
                                String pic_url= pic.get("pic_url").getAsString();
                                String latitude= pic.get("pic_latitude").getAsString();
                                String longitude= pic.get("pic_longitude").getAsString();
                                String description= pic.get("pic_description").getAsString();
                                String date= pic.get("date_and_time").getAsString();
                                Log.d("Pic   " , pic.toString() + "      " +id);
                                Pictures p =   new Pictures(id ,user_id,pic_url,longitude, latitude ,description , date);
                                picturesList.add(p);
                            }
                            pictureAdapter.notifyDataSetChanged();
                        } else {
                            String error = body.get("res").getAsString();
                            Snackbar snackbar = Snackbar.make(new LinearLayout(Home.this), error, Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                        pd.dismiss();
                    }catch (Exception e) {
                        pd.dismiss();
                    }
                }
                @Override
                public void onFailure(Call<ResponseFromServer> call, Throwable t) {
                }
            });
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column
            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
