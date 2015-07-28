package app.android.shivanand.moviesp1;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Shivanand on 28/07/2015.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Integer> mData;

    public MoviesAdapter(Context context,List data) {

        this.mContext = context;
        this.mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_movie_item,viewGroup,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.mMovieItemImageView.setImageResource(mData.get(i));

        //Toast.makeText(mContext,"haha the position is "+ i,Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView mMovieItemImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mMovieItemImageView = (ImageView) itemView.findViewById(R.id.movie_list_item);
        }

    }
}
