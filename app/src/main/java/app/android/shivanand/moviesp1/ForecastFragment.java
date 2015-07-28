package app.android.shivanand.moviesp1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mLayoutManager;

    public ForecastFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_forecast, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.drawer_list);

        MoviesAdapter moviesAdapter = new MoviesAdapter(getActivity(),getData());

        mLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(moviesAdapter);
        return view;
    }

    public static ArrayList<Integer> getData(){
        ArrayList data = new ArrayList();
        data.add(R.drawable.art_clear);
        data.add(R.drawable.ic_clear);
        data.add(R.drawable.ic_cloudy);
        data.add(R.drawable.art_clouds);
        data.add(R.drawable.art_snow);
        data.add(R.drawable.ic_light_rain);
        data.add(R.drawable.art_storm);
        data.add(R.drawable.ic_logo);

        return data;
    }

}
