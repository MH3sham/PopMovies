package app.com.example.vip.popmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    OnFavoriteClick mCallback;
    public interface OnFavoriteClick{
        void makeFavorite(String movieId);
    }
    Button favourite;
    private boolean clicked = false;

    String TAG = "DONE";
    private static final String LOG_TAG = DetailFragment.class.getSimpleName();
    ArrayList<Trailer> trailerlist = new ArrayList<Trailer>();
    ArrayList<Review> reviewlist = new ArrayList<Review>();
    TrailerAdapter traileradapter;
    ReviewAdapter reviewadapter;
    boolean temp = true;
    ListView l1, l2;
    private static final String MOVIE_SHARE_HASHTAG = " #MovieApp";
    private String mMovieStr;
    static final String DETAIL_MOVIE = "MOVIE";


    public DetailFragment() {
        setHasOptionsMenu(true);
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach started");
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnFavoriteClick) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView started");
        final View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        //Intent Detail_Activity = getActivity().getIntent();
        Bundle arguments = getArguments();
        final Movie ItemSelected = arguments.getParcelable(DetailFragment.DETAIL_MOVIE);
        Log.d(LOG_TAG, "onCreateView get Item Selected");

        // names are diff. from the orignal names from Json .. so i can easily recognize them
        // ex. rate > my name .... vote_average > Json name

        TextView t1 = (TextView) rootView.findViewById(R.id.Movie_name);
        Log.d(LOG_TAG, "onCreateView textView t1");
        t1.setText((CharSequence) ItemSelected.title);

        ImageView img = (ImageView) rootView.findViewById(R.id.grid_item_movies_imageview);
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185//" + ItemSelected.poster_path).into(img);
        //img.setImageResource(R.id.im); //for test design

        Log.d(LOG_TAG, "onCreateView picasso");

        TextView t2 = (TextView) rootView.findViewById(R.id.Year); //gets year only from release date
        String Time[] = ItemSelected.release_date.split("-");
        t2.setText((CharSequence) Time[0]);

        TextView t3 = (TextView) rootView.findViewById(R.id.Lenth);
        t3.setText("120 min"); //Fake


        TextView t4 = (TextView) rootView.findViewById(R.id.rate);
        t4.setText(ItemSelected.vote_average + "/10");

        TextView t6 = (TextView) rootView.findViewById(R.id.release); // gets full date
        t6.setText( "Release Date: " + ItemSelected.release_date);

        TextView t10 = (TextView) rootView.findViewById(R.id.overviewtext);
        t10.setText("Overview: \n");
        TextView t5 = (TextView) rootView.findViewById(R.id.overview);
        t5.setText(ItemSelected.overview);

///////////////////////////////////////////////////////////////////////////
        TextView t8 = (TextView) rootView.findViewById(R.id.trailertext);
        t8.setText("Trailer: \n");

        TextView t9 = (TextView) rootView.findViewById(R.id.reviewtext);
        t9.setText("Review: \n");

        l1 = (ListView) rootView.findViewById(R.id.trailer);
        l2 = (ListView) rootView.findViewById(R.id.review);


        FetchMovieTask2 fetch_trailer = new FetchMovieTask2(getActivity());
        String url_tailer = "http://api.themoviedb.org/3/movie/" + ItemSelected.id + "/videos?api_key=04bfe32d5e287713638a5fe586a9a477";
        fetch_trailer.execute(url_tailer);
        Log.d(TAG, "onCreateView Trailer execute");


        FetchMovieTask2 fetch_review = new FetchMovieTask2(getActivity());
        String url_review = "http://api.themoviedb.org/3/movie/" + ItemSelected.id + "/reviews?api_key=04bfe32d5e287713638a5fe586a9a477";
        fetch_review.execute(url_review);
        Log.d(TAG, "onCreateView Review execute");


        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Trailer temp = (Trailer) parent.getItemAtPosition(position);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + temp.key));
                startActivity(intent);
            }
        });


        favourite = (Button) rootView.findViewById(R.id.favorite_button);
        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.makeFavorite(ItemSelected.id);
                Log.d("PoP", "onClick make favorite");

            }
        });

        return rootView;
    }


    public class FetchMovieTask2 extends AsyncTask<String, Void, String> {
        Context mcontext;

        public FetchMovieTask2(Context mcontext) {
            this.mcontext = mcontext;
        }

        private final String LOG_TAG = FetchMovieTask2.class.getSimpleName();
        private String forecastJsonStr = null;

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            // Will contain the raw JSON response as a string.
            try {
                URL url = new URL(params[0]);
                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    forecastJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    forecastJsonStr = null;
                }
                forecastJsonStr = buffer.toString();
                Log.v(LOG_TAG, "JSON string" + forecastJsonStr);
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                forecastJsonStr = null;
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
            return forecastJsonStr;
        }

        @Override
        protected void onPostExecute(String result) {
            if (temp) {
                ParseTrailer parser = new ParseTrailer();
                trailerlist = new ArrayList<Trailer>();
                trailerlist = parser.ParseJSON_To_Tailer(forecastJsonStr);
                traileradapter = new TrailerAdapter(mcontext, trailerlist);
                l1.setAdapter(traileradapter);

                temp = false;
            } else {
                ParseReview parser1 = new ParseReview();
                reviewlist = new ArrayList<Review>();
                reviewlist = parser1.ParseJSON_To_Review(forecastJsonStr);
                reviewadapter = new ReviewAdapter(mcontext, reviewlist);
                l2.setAdapter(reviewadapter);
                temp = true;
            }
        }
    }




//share
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.detailfragment, menu);

        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);

        // Get the provider and hold onto it to set/change the share intent.
        ShareActionProvider mShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        // Attach an intent to this ShareActionProvider.  You can update this at any time,
        // like when the user selects a new piece of data they might like to share.
        if (mShareActionProvider != null )
        {
            mShareActionProvider.setShareIntent(createShareForecastIntent());
        }
        else
        {
            Log.d(LOG_TAG, "Share Action Provider is null?");
        }
    }

    private Intent createShareForecastIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                mMovieStr + MOVIE_SHARE_HASHTAG);
        return shareIntent;
    }
}

