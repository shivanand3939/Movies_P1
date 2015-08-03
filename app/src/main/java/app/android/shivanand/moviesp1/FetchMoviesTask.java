package app.android.shivanand.moviesp1;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shivanand on 28/07/2015.
 */
public class FetchMoviesTask extends AsyncTask<String,Void,ArrayList> {

    private static final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

    final static String MS1_POSTER_PATH = "poster_path";
    final static String MS1_ORIGINAL_TITLE = "original_title";
    final static String MS1_POSTER_BACKGROUND_PATH = "backdrop_path";
    final static String MS1_OVERVIEW = "overview";
    final static String MS1_VOTE_AVERAGE = "vote_average";
    final static String MS1_RELEASE_DATE = "release_date";


    private ArrayList processMovieJsonData(String movieJsonString) {

        ArrayList mPosters = new ArrayList();
        String posterBasePath = "http://image.tmdb.org/t/p/w342/";
        final String MS1_RESULTS = "results";

        try
        {
            JSONObject jsonObject = new JSONObject(movieJsonString);
            JSONArray results = jsonObject.getJSONArray(MS1_RESULTS);
            for(int i =0;i<results.length();i++){

                JSONObject movie = results.getJSONObject(i);

                HashMap hashMap = new HashMap(6);
                //Movie Poster path used in MainActivity's RecyclerView Item
                String moviePosterPath = posterBasePath + movie.getString(MS1_POSTER_PATH)+"\n";
                hashMap.put(MS1_POSTER_PATH,moviePosterPath);
                //original Title
                String originalTitle = movie.getString(MS1_ORIGINAL_TITLE);
                hashMap.put(MS1_ORIGINAL_TITLE,originalTitle);
                //Poster Background Image Path, used in DetailActivity
                String backgroundImagePath = posterBasePath + movie.getString(MS1_POSTER_BACKGROUND_PATH)+"\n";
                hashMap.put(MS1_POSTER_BACKGROUND_PATH,backgroundImagePath);
                //Plot Synopsis
                String overview = movie.getString(MS1_OVERVIEW);
                hashMap.put(MS1_OVERVIEW,overview);
                //User Rating
                int userRating = movie.getInt(MS1_VOTE_AVERAGE);
                hashMap.put(MS1_VOTE_AVERAGE,userRating);
                //Release Date
                String releaseDate = movie.getString(MS1_RELEASE_DATE);
                hashMap.put(MS1_RELEASE_DATE, releaseDate);
                mPosters.add(i,hashMap);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return mPosters;
    }

    @Override
    protected ArrayList doInBackground(String... params) {
        //mPosters contains the results from processMovieJsonData(String movieJsonString)
        ArrayList mPosters = new ArrayList();
        if(params == null)
            return null;

        String sortByOrder = params[0];

        //if sortByOrder = popularity.desc add an additional query that vote_count.gte = 0
        // for other sortByOrder add an additional query that vote_count.gte = 1000
        int voteCount = 1000;
        if("popularity.desc".equals(sortByOrder))
        {
            voteCount = 0;
        }

        //url= http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=a9959e5a2ee93c6a3d1d7d36a70be814
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String apiKey = "a9959e5a2ee93c6a3d1d7d36a70be814";

        try
        {
            final String MOVIE_BASE_URL =
                    "http://api.themoviedb.org/3/discover/movie?";
            final String SORT_BY = "sort_by";
            final String API_KEY = "api_key";
            final String VOTE_COUNT = "vote_count.gte";

            Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendQueryParameter(SORT_BY, sortByOrder)
                    .appendQueryParameter(API_KEY, apiKey)
                    .appendQueryParameter(VOTE_COUNT, String.valueOf(voteCount))
                    .build();
            URL url = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
            if(inputStreamReader == null)
                return null;
            reader = new BufferedReader(inputStreamReader);

            String readLine;
            StringBuffer buffer = new StringBuffer();

            while ((readLine=reader.readLine())!= null){

                buffer.append(readLine+"\n");
            }
            String movieJsonString = buffer.toString();
            mPosters = processMovieJsonData(movieJsonString);
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            if(urlConnection != null)
            {
                urlConnection.disconnect();
            }
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG,"Error Closing BufferReader Stream "+e);
                }
            }
        }

        return mPosters;
    }

    @Override
    protected void onPostExecute(ArrayList data) {
        super.onPostExecute(data);
        MoviesAdapter.mData = data;
        ForecastFragment.moviesAdapter.notifyDataSetChanged();
    }
}
