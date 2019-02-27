package app.com.example.vip.popmovies;

import java.util.Comparator;


public class PopularComparator implements Comparator<Movie>
{
    @Override
    public int compare(Movie o1, Movie o2)
    {
        return o2.popularity.compareTo(o1.popularity);
    }
}