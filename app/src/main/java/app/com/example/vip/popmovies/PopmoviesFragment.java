package app.com.example.vip.popmovies;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

/* A placeholder fragment containing a simple view.
*/
public class PopmoviesFragment extends Fragment {
    ImageAdapter mimageAdapter;


    public interface Callback {
        public void onItemSelected(Movie movie);
    }

    private ArrayList<Movie> MovieList;
    private GridView gridView;

    public PopmoviesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_main,container,false);

        mimageAdapter = new ImageAdapter(getActivity(),new ArrayList<Movie>());

        gridView = (GridView) rootview.findViewById(R.id.griditem_movies);
        gridView.setAdapter(mimageAdapter);
        MovieList = new ArrayList<Movie>();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie SelectedItem= (Movie)mimageAdapter.getItem(position);
                ((Callback) getActivity()).onItemSelected(SelectedItem);
                //to add Toast when click on item
                // Toast.makeText(getActivity(),posterclick,Toast.LENGTH_LONG).show();
            }
        });

        return rootview;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    public void updateMovies() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sort = prefs.getString(getString(R.string.pref_selection_key),
                getString(R.string.pref_selection_default));
        String StrURL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=04bfe32d5e287713638a5fe586a9a477";
        FetchMovieTask movieTask = new FetchMovieTask();

        movieTask.execute(StrURL,sort);
    }

    public class FetchMovieTask extends AsyncTask<String, Void, String> {



        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();
        String Sort_Method;
        private String MovieJsonStr = null;

        public String GetStr() {
            return MovieJsonStr;
        }
        @Override
        protected String doInBackground(String ... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            // Will contain the raw JSON response as a string.
            try {
                URL url = new URL(params[0]);
                Sort_Method = params[1];
                // Create the request to TMDB, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    MovieJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    MovieJsonStr = null;
                }
                MovieJsonStr = buffer.toString();
                Log.v(LOG_TAG, "JSON string" + MovieJsonStr);
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                MovieJsonStr = null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
            return MovieJsonStr;
        }
        @Override
        protected void onPostExecute(String result)
        {
            ParseJSONstr parser = new ParseJSONstr();
            MovieList = new ArrayList<Movie>();
            MovieList = parser.ParseJSON_To_TweetObject(MovieJsonStr);

            if (Sort_Method.equals(getString(R.string.pref_selection_favorite)))
            {//favorite selection
                String [] idsList=((MainActivity)getActivity()).splitStringToIdStrings();
                ArrayList<Movie> favMovies=((MainActivity)getActivity()).favoriteMovies(idsList,MovieList);
                mimageAdapter = new ImageAdapter(getActivity(), favMovies);
            }
            else
            {//top or highest selection
                SortMovies(Sort_Method, MovieList);
                mimageAdapter = new ImageAdapter(getActivity(), MovieList);
                //gridView.setAdapter(mimageAdapter);
            }

            gridView.setAdapter(mimageAdapter);
        }

        //sort movies
        private ArrayList<Movie> SortMovies(String Sort, ArrayList<Movie> Arr) {
            if (Sort.equals(getString(R.string.pref_selection_highest))) {
                Collections.sort(Arr, new HighestComparator());
            }
            else if (Sort.equals(getString(R.string.pref_selection_popular))) {
                Collections.sort(Arr, new PopularComparator());
            }
            else {
                Log.d(LOG_TAG, "Sort type not found: " + Sort);
            }
            return Arr;
        }


    }
}



