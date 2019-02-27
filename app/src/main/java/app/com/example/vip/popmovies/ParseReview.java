package app.com.example.vip.popmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ParseReview
{
    private final String LOG_TAG = ParseJSONstr.class.getSimpleName();
    private String JSONFile;

    public ArrayList<Review> ParseJSON_To_Review(String JSONFile) {
        Log.v(LOG_TAG, JSONFile);
        this.JSONFile = JSONFile;
        try {
            return getDataFromJson(JSONFile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Review> getDataFromJson(String forecastJsonStr)
            throws JSONException {
        JSONObject forecastJson = new JSONObject(forecastJsonStr);
        JSONArray DataArray = forecastJson.getJSONArray("results");
        ArrayList<Review> userData = new ArrayList<Review>();
        for (int i = 0; i < DataArray.length(); i++) {
            // Get the JSON object representing the day
            JSONObject Forecast = DataArray.getJSONObject(i);
            String id = Forecast.getString("id");
            String author= Forecast.getString("author");
            String content= Forecast.getString("content");
            Review temp = new Review(id,author,content);
            userData.add(temp);
        }
        return userData;
    }
}
