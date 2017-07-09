package id.muhmirza.popularmovies.utils;

import id.muhmirza.popularmovies.model.Movie;

/**
 * Created by mirza on 09/07/17.
 */
public interface OnTaskCompleted {
    void onFetchDataCompleted(Movie[] movies);
}
