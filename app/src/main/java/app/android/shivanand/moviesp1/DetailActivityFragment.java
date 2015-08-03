package app.android.shivanand.moviesp1;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_detail, container, false);
        TextView original_title = (TextView) view.findViewById(R.id.original_title);
        ImageView background_image = (ImageView) view.findViewById(R.id.background_image);
        TextView release_date = (TextView) view.findViewById(R.id.release_date);
        TextView vote_average = (TextView) view.findViewById(R.id.vote_average);
        TextView over_view = (TextView) view.findViewById(R.id.overview);

        Bundle extra = getActivity().getIntent().getExtras();
        int position=0;
        if(extra != null)
        {
            position = extra.getInt("ITEM_DATA");
        }
        HashMap hashMap = (HashMap) MoviesAdapter.mData.get(position);
        String originalTitle = (String) hashMap.get(FetchMoviesTask.MS1_ORIGINAL_TITLE);
        original_title.setText(originalTitle);

        String backgroundImagePath = (String) hashMap.get(FetchMoviesTask.MS1_POSTER_BACKGROUND_PATH);
        Picasso.with(getActivity()).load(backgroundImagePath)
                .into(background_image);

        String overview = (String) hashMap.get(FetchMoviesTask.MS1_OVERVIEW);
        over_view.setText(overview);

        int userRating = (int) hashMap.get(FetchMoviesTask.MS1_VOTE_AVERAGE);
        vote_average.setText(userRating+"");

        String releaseDate = (String) hashMap.get(FetchMoviesTask.MS1_RELEASE_DATE);
        release_date.setText(releaseDate);

        return view;
    }
}
