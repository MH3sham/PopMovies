package app.com.example.vip.popmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class Trailer implements Parcelable
{
    String id;
    String name;
    String site;
    String size;
    String key;
    public Trailer(Parcel in)
    {
        String trailer_data[]=new String[5];
        in.readStringArray(trailer_data);
        this.id=trailer_data[0];
        this.name=trailer_data[1];
        this.site=trailer_data[2];
        this.size=trailer_data[3];
        this.key=trailer_data[4];
    }

    public Trailer() {

    }

    public  void writetoparcel(Parcel dest,int flags)
    {
        dest.writeStringArray(new String[]{this.id,this.name,this.site,
                this.size,this.key});
    }

    public Trailer(String id, String name, String site, String size, String key)
    {

        this.id=id;
        this.name=name;
        this.site=site;
        this.size=size;
        this.key=key;

    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Creator<Trailer> CREATOR= new Creator<Trailer>() {

        @Override
        public Trailer createFromParcel(Parcel source) {
            return new Trailer(source);  //using parcelable constructor
        }
        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };
}
