package app.com.example.vip.popmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParseTrailer
{
    private static final String LOG_TAG = ParseJSONstr.class.getSimpleName();
    private String JSONFile;
   public String id;
    public ArrayList<Trailer> ParseJSON_To_Tailer(String JSONFile) {
        Log.v(LOG_TAG, JSONFile);
        this.JSONFile = JSONFile;
        try {
            return getDataFromJson(JSONFile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Trailer> getDataFromJson(String forecastJsonStr)
            throws JSONException {
        JSONObject forecastJson = new JSONObject(forecastJsonStr);
        JSONArray DataArray = forecastJson.getJSONArray("results");
        ArrayList<Trailer> userData = new ArrayList<Trailer>();
        for (int i = 0; i < DataArray.length(); i++) {
            // Get the JSON object representing the day
            JSONObject Forecast = DataArray.getJSONObject(i);
            id = Forecast.getString("id");
            String name= Forecast.getString("name");
            String site= Forecast.getString("site");
            String size = Forecast.getString("size");
            String key=Forecast.getString("key");




            Trailer temp = new Trailer(id, name,site, size,key);
            userData.add(temp);
        }
        return userData;
    }
}
