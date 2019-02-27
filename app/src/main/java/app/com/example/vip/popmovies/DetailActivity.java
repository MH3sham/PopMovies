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

public class DetailActivity extends AppCompatActivity implements DetailFragment.OnFavoriteClick{

    public static final String TAG="POP";
    Movie selectedMovie;
    private SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            selectedMovie=intent.getParcelableExtra("Selected");
            Log.d(TAG, "onCreate  get selected in Activity");
            Bundle arguments = new Bundle();
            arguments.putParcelable(DetailFragment.DETAIL_MOVIE, selectedMovie);
            Log.d(TAG, "onCreate put parcelable to argument");
            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(arguments);
            Log.d(TAG, "onCreate setArguments to fragment");

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, fragment)
                    .commit();
            //Log.d(TAG, "onCreate begin Transaction movie detail container");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void makeFavorite(String movieId) {
        Toast.makeText(DetailActivity.this, "Added To Favorites", Toast.LENGTH_SHORT).show();
        sharedpreferences = this.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        // Writing data to SharedPreferences
        String idArray = sharedpreferences.getString("idArray", "");
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("idArray", idArray + "," + movieId);
        editor.commit();

    }
}
