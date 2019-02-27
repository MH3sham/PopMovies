package app.com.example.vip.popmovies;

import android.os.Parcel;
import android.os.Parcelable;


public class Movie implements Parcelable
{
    String title;
    String overview;
    String original_title;
    String original_language;
    String vote_count;
    String release_date;
    String poster_path;
    String id;
    String backdrop_path;
    String popularity;
    String vote_average;

    public Movie(Parcel in){
        String[] data= new String[11];
        in.readStringArray(data);
        this.title= data[0];
        this.overview= data[1];
        this.original_title= data[2];
        this.original_language=data[3];
        this.vote_count=data[4];
        this.release_date=data[5];
        this.poster_path=data[6];
        this.id=data[7];
        this.backdrop_path=data[8];
        this.popularity=data[9];
        this.vote_average=data[10];

    }

    public Movie() {

    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.title,this.overview,this.original_title,
                this.original_language,this.vote_count,this.release_date,this.poster_path,
                this.id,this.backdrop_path,this.popularity,this.vote_average});
    }
    public Movie(String title, String overview, String original_title, String original_language, String vote_count,
                 String release_date, String poster_path, String id, String backdrop_path, String popularity, String vote_average)
    {
        this.backdrop_path=backdrop_path;
        this.id=id;
        this.title=title;
        this.original_title=original_title;
        this.original_language=original_language;
        this.overview=overview;
        this.vote_count=vote_count;
        this.vote_average=vote_average;
        this.release_date=release_date;
        this.poster_path=poster_path;
        this.popularity=popularity;


    }
    @Override
    public int describeContents() {
        return 0;
    }
    public static final Creator<Movie> CREATOR= new Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);  //using parcelable constructor
        }
        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}
