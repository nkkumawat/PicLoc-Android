package me.nkkumawat.picloc_x.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Picture;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.nkkumawat.picloc_x.Login;
import me.nkkumawat.picloc_x.Models.Pictures;
import me.nkkumawat.picloc_x.R;
import me.nkkumawat.picloc_x.SinglePictureDetails;
import me.nkkumawat.picloc_x.Utils.Constants;

/**
 * Created by sonu on 14/6/18.
 */

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.MyViewHolder> {
    private Context mContext;
    private List<Pictures> picturesList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }
    public PictureAdapter(Context mContext, List<Pictures> picturesList) {
        this.mContext = mContext;
        this.picturesList = picturesList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_list, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Pictures pictures = picturesList.get(position);
        holder.title.setText(pictures.pic_description);
        Log.d("url" ,Constants.BASE_URL1 + pictures.pic_url);
        Picasso.get().load(Constants.BASE_URL1 + pictures.pic_url).into(holder.thumbnail);


        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext , SinglePictureDetails.class);
                intent.putExtra("pic_url" , pictures.pic_url);
                intent.putExtra("pic_latitude" , pictures.latitude);
                intent.putExtra("pic_longitude" , pictures.longitude);
                intent.putExtra("pic_description" , pictures.pic_description);
                intent.putExtra("id" , pictures.id);
                mContext.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return picturesList.size();
    }
}