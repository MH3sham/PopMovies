package app.com.example.vip.popmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ParseJSONstr {
    private final String LOG_TAG = ParseJSONstr.class.getSimpleName();
   // private String JSONFile;

    public ArrayList<Movie> ParseJSON_To_TweetObject(String JSONFile) {
        //Log.v(LOG_TAG,JSONFile);
        //this.JSONFile = JSONFile;
        try {
            return getDataFromJson(JSONFile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Movie> getDataFromJson(String forecastJsonStr)
            throws JSONException {
        JSONObject forecastJson = new JSONObject(forecastJsonStr);
        JSONArray DataArray = forecastJson.getJSONArray("results");
        ArrayList<Movie> userData = new ArrayList<Movie>();
        for (int i = 0; i < DataArray.length(); i++) {
            // Get the JSON object representing the movie
            JSONObject Forecast = DataArray.getJSONObject(i);
            String title = Forecast.getString("title");
            String overview = Forecast.getString("overview");
            String original_title = Forecast.getString("original_title");
            String original_language = Forecast.getString("original_language");
            String vote_count = Forecast.getString("vote_count");
            String release_date = Forecast.getString("release_date");
            String poster_path = Forecast.getString("poster_path");
            String id = Forecast.getString("id");
            String backdrop_path = Forecast.getString("backdrop_path");
            String popularity = Forecast.getString("popularity");
            String vote_average = Forecast.getString("vote_average");



            Movie temp = new Movie(title, overview, original_title, original_language,
                    vote_count, release_date, poster_path, id, backdrop_path, popularity, vote_average);
            userData.add(temp);
        }
        return userData;
    }
}
