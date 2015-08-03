package app.android.shivanand.moviesp1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Shivanand on 28/07/2015.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private Context mContext;
    static ArrayList mData = new ArrayList();

    public MoviesAdapter(Context context) {

        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_movie_item,viewGroup,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        //myViewHolder.mMovieItemImageView.setImageResource(mData.get(i));

        HashMap hashMap = (HashMap) mData.get(i);
        String moviePosterPath = (String) hashMap.get(FetchMoviesTask.MS1_POSTER_PATH);

        Picasso.with(mContext).load(moviePosterPath)
                .into(myViewHolder.mMovieItemImageView);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView mMovieItemImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mMovieItemImageView = (ImageView) itemView.findViewById(R.id.movie_list_item);
            mMovieItemImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext,DetailActivity.class);
            intent.putExtra("ITEM_DATA",getPosition());
            mContext.startActivity(intent);
        }
    }
}
