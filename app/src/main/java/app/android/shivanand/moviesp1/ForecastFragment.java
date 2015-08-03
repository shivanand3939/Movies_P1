package app.android.shivanand.moviesp1;


import android.app.LoaderManager;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private RecyclerView mRecyclerView;
    static MoviesAdapter moviesAdapter;
    private static final String SORT_ORDER = "SORT_ORDER";
    private static final String SORT_ORDER_DEFAULT_VALUE = "\"popularity.desc\"";
    public ForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecast_fragment_menu, menu);

        MenuItem item = menu.findItem(R.id.action_settings);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.sortBy_Entries, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String[] sortByEntriesList = getResources().getStringArray(R.array.sortBy_Entries);
                String[] sortByValuesList = getResources().getStringArray(R.array.sortBy_Values);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String mSortOrder = sharedPreferences.getString(SORT_ORDER, SORT_ORDER_DEFAULT_VALUE);

                CheckedTextView textView = (CheckedTextView) view;
                String sortBy = (String) textView.getText();
                if (sortBy.equals(sortByEntriesList[0])) {
                    mSortOrder = sortByValuesList[0];
                } else if (sortBy.equals(sortByEntriesList[1])) {
                    mSortOrder = sortByValuesList[1];
                } else if (sortBy.equals(sortByEntriesList[2])) {
                    mSortOrder = sortByValuesList[2];
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(SORT_ORDER,mSortOrder);
                editor.commit();

                updateMovies();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.refresh) {
            updateMovies();
            return true;
        }
        else if(id == R.id.action_settings)
        {
            Toast.makeText(getActivity(),"haha Mahendra",Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_forecast, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.drawer_list);

        moviesAdapter = new MoviesAdapter(getActivity());

        // using vertical grid layout this works
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL,
                false));
        mRecyclerView.setAdapter(moviesAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    private void updateMovies() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String mSortOrder = sharedPreferences.getString(SORT_ORDER, SORT_ORDER_DEFAULT_VALUE);

        FetchMoviesTask fetchMoviesTask = new FetchMoviesTask();
        fetchMoviesTask.execute(mSortOrder);

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
