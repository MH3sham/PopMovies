package app.com.example.vip.popmovies;

import java.util.Comparator;


public class HighestComparator implements Comparator<Movie> {
    @Override
    public int compare(Movie o1, Movie o2)
    {
        return o2.vote_average.compareTo(o1.vote_average);
    }
}