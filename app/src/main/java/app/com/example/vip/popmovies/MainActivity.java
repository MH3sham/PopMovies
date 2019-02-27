package app.com.example.vip.popmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PopmoviesFragment.Callback, DetailFragment.OnFavoriteClick {

    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private boolean mTwoPane;

    public static final String mypreference = "mypref";
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.movie_detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.

        }
        else
        {
            mTwoPane = false;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.movie_container, new PopmoviesFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);


    }



    @Override
    public void onItemSelected (Movie movie) {
        if (mTwoPane) {
            Bundle args = new Bundle();
            args.putParcelable(DetailFragment.DETAIL_MOVIE, movie);
            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        }
        else
        {
            Intent Detail_Activity=new Intent(this,DetailActivity.class).putExtra("Selected", movie);
            Log.d(DetailActivity.TAG, "onItemSelected PUT extra done :)");
            startActivity(Detail_Activity);
        }

    }

    ////////////////////////////////////////////////Fav\\\\\\\\\\\

    public  String [] splitStringToIdStrings()
    {
        SharedPreferences sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        // Writing data to SharedPreferences
        String idStringArrayfromSP=sharedpreferences.getString("idArray", "");
        String[] idArrayFromString = idStringArrayfromSP.split(",", -1);
        return idArrayFromString;

    }
    public ArrayList<Movie> favoriteMovies(String []favIds,ArrayList<Movie> allMovies)
    {
        ArrayList<Movie> favMovies=new ArrayList<>();
        for (Movie m:allMovies)
        {
            for (String  id:favIds)
            {
                if (m.id.equals(id))
                {
                    favMovies.add(m);
                    break;

                }
            }
        }
        return favMovies;
    }


    @Override
    public void makeFavorite(String movieId)
    {
        Toast.makeText(MainActivity.this, "Added To Favorites", Toast.LENGTH_SHORT).show();
        sharedpreferences = this.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        // Writing data to SharedPreferences
        String idArray = sharedpreferences.getString("idArray", "");
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("idArray", idArray + "," + movieId);
        editor.commit();
    }

}
