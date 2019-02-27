package app.com.example.vip.popmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class ReviewAdapter extends BaseAdapter
{
    Context context;
    ArrayList<Review> mydata ;
    LayoutInflater inflater ;
    public ReviewAdapter(Context context, ArrayList<Review> mydata)
    {
        this.context=context;
        this.mydata=mydata;
        inflater= LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        if(mydata == null)
            return 0;
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
        reviewholder holder=new reviewholder();
        View vi = convertView;
        if(vi==null)
            vi=inflater.inflate(R.layout.review_item,null);
        holder.textView1=(TextView)vi.findViewById(R.id.name);
        holder.textView1.setText(mydata.get(position).author);
        holder.textView2=(TextView)vi.findViewById(R.id.content);
        holder.textView2.setText(mydata.get(position).content);
        return vi;
    }
}

class reviewholder
{
    TextView textView1;
    TextView textView2;
}