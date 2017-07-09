package id.muhmirza.popularmovies.ui.home;

import android.os.Bundle;

import id.muhmirza.popularmovies.model.Movie;

/**
 * Created by mirza on 09/07/17.
 */

public interface HomeView {
    void updateGrid(Movie[] movies);
    void initData(Bundle savedInstanceState);
}
