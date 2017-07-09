package id.muhmirza.popularmovies.utils;

import id.muhmirza.popularmovies.model.Movie;

/**
 * Created by Mirza
 */
public interface OnTaskCompleted {
    void onFetchMoviesTaskCompleted(Movie[] movies);
}
