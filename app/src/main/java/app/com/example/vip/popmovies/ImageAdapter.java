package app.com.example.vip.popmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


class ImageAdapter extends BaseAdapter {
    Context context;
    ArrayList<Movie> mydata ;
    LayoutInflater inflater ;
    public ImageAdapter (Context context, ArrayList<Movie> mydata )
    {
        this.context=context;
        this.mydata=mydata;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mydata.size();
    }

    @Override
    public Object getItem(int position) {
        return mydata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String LOG_TAG = ParseJSONstr.class.getSimpleName();
       // View vi = convertView;
        if(convertView==null)
            convertView = inflater.inflate(R.layout.list_item_movies,null);
        ImageView img=(ImageView)convertView.findViewById(R.id.grid_item_movies_imageview);
        img.setAdjustViewBounds(true);

        Picasso.with(context).load("http://image.tmdb.org/t/p/w185//"+mydata.get(position).poster_path)
                .resize(context.getResources().getInteger(R.integer.tmdb_poster_w185_width),
                        context.getResources().getInteger(R.integer.tmdb_poster_w185_height))
                .into(img);
        Log.v(LOG_TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>pop>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + mydata.get(position).popularity);
        Log.v(LOG_TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>vote>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + mydata.get(position).vote_average);
        return convertView;
    }


}