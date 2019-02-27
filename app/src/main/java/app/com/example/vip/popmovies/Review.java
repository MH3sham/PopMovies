package app.com.example.vip.popmovies;

import android.os.Parcel;
import android.os.Parcelable;


public class Review implements Parcelable
{
    String id;
    String author;
    String content;

    public Review(Parcel in)
    {
        String review_data[]=new String[3];
        in.readStringArray(review_data);
        this.id=review_data[0];
        this.author=review_data[1];
        this.content=review_data[2];
    }

    public  void writetoparcel(Parcel dest,int flags)
    {
        dest.writeStringArray(new String[]{this.id,this.author,this.content,
                });
    }

    public Review(String id, String name, String site)
    {

        this.id=id;
        this.author=name;
        this.content=site;


    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Creator<Review> CREATOR= new Creator<Review>() {

        @Override
        public Review createFromParcel(Parcel source) {
            return new Review(source);  //using parcelable constructor
        }
        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}
