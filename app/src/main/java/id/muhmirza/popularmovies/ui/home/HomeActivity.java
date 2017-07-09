package id.muhmirza.popularmovies.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import id.muhmirza.popularmovies.R;
import id.muhmirza.popularmovies.model.Movie;
import id.muhmirza.popularmovies.ui.detail.DetailsActivity;

/**
 * Created by mirza on 09/07/17.
 */
public class HomeActivity extends AppCompatActivity implements HomeView{

    private final String LOG_TAG = HomeActivity.class.getSimpleName();
    private GridView mGridView;
    private Menu mMenu;

    HomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        presenter = new HomePresenter(this,this);

        mGridView = (GridView) findViewById(R.id.gridview);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = (Movie) parent.getItemAtPosition(position);

                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra(getResources().getString(R.string.parcel_movie), movie);

                startActivity(intent);
            }
        });

        initData(savedInstanceState);

    }

    @Override
    public void initData(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            // Get data from the Internet
            presenter.getMoviesFromTMDb(presenter.getSortMethod());
        } else {
            // Get data from local and get movie object
            Parcelable[] parcelable = savedInstanceState.
                    getParcelableArray(getString(R.string.parcel_movie));

            if (parcelable != null) {
                int numMovieObjects = parcelable.length;
                Movie[] movies = new Movie[numMovieObjects];
                for (int i = 0; i < numMovieObjects; i++) {
                    movies[i] = (Movie) parcelable[i];
                }

                updateGrid(movies);

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, mMenu);

        mMenu = menu;

        mMenu.add(Menu.NONE, // No group
                R.string.pref_sort_pop_desc_key, // ID
                Menu.NONE, // Sort order: not relevant
                null) // No text to display
                .setVisible(false)
                .setIcon(R.drawable.ic_hot)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        mMenu.add(Menu.NONE, R.string.pref_sort_vote_avg_desc_key, Menu.NONE, null)
                .setVisible(false)
                .setIcon(R.drawable.ic_favorite)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        updateMenu();

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        int numMovieObjects = mGridView.getCount();
        if (numMovieObjects > 0) {
            // Get Movie objects from gridview
            Movie[] movies = new Movie[numMovieObjects];
            for (int i = 0; i < numMovieObjects; i++) {
                movies[i] = (Movie) mGridView.getItemAtPosition(i);
            }

            // Save Movie objects to bundle
            outState.putParcelableArray(getString(R.string.parcel_movie), movies);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.string.pref_sort_pop_desc_key:
                presenter.updateSharedPrefs(getString(R.string.tmdb_sort_pop_desc));
                updateMenu();
                presenter.getMoviesFromTMDb(presenter.getSortMethod());
                return true;
            case R.string.pref_sort_vote_avg_desc_key:
                presenter.updateSharedPrefs(getString(R.string.tmdb_sort_vote_avg_desc));
                updateMenu();
                presenter.getMoviesFromTMDb(presenter.getSortMethod());
                return true;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateMenu() {
        String sortMethod = presenter.getSortMethod();

        if (sortMethod.equals(getString(R.string.tmdb_sort_pop_desc))) {
            mMenu.findItem(R.string.pref_sort_pop_desc_key).setVisible(false);
            mMenu.findItem(R.string.pref_sort_vote_avg_desc_key).setVisible(true);
        } else {
            mMenu.findItem(R.string.pref_sort_vote_avg_desc_key).setVisible(false);
            mMenu.findItem(R.string.pref_sort_pop_desc_key).setVisible(true);
        }
    }

    @Override
    public void updateGrid(Movie[] movies) {
        mGridView.setAdapter(new ImageAdapter(this, movies));
    }


}

