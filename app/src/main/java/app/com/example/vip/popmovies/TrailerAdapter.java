package app.com.example.vip.popmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrailerAdapter extends BaseAdapter
{
    Context context;
    ArrayList<Trailer> mydata ;
    LayoutInflater inflater ;
    public TrailerAdapter(Context context, ArrayList<Trailer> mydata)
    {
        this.context=context;
        this.mydata=mydata;
        inflater= LayoutInflater.from(context);
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
    public View getView(int position, View convertView, ViewGroup parent)
    {
        trailerholder holder=new trailerholder();
        View vi = convertView;
        if(vi==null)
               vi=inflater.inflate(R.layout.trailer_item,null);


        holder.textView = (TextView) vi.findViewById(R.id.Trailername);
        holder.textView.setText(mydata.get(position).name);

        holder.imageView = (ImageView) vi.findViewById(R.id.TrailerImage);
        Picasso.with(context).load("http://img.youtube.com/vi/" + mydata.get(position).key +"/0.jpg")
                .into(holder.imageView);


        return vi;
    }
}

class trailerholder
{
   public ImageView imageView;
   public TextView textView;


}