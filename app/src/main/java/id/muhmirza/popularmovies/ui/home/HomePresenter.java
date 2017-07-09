package id.muhmirza.popularmovies.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.widget.Toast;

import id.muhmirza.popularmovies.R;
import id.muhmirza.popularmovies.model.Movie;
import id.muhmirza.popularmovies.utils.FetchMovieAsyncTask;
import id.muhmirza.popularmovies.utils.OnTaskCompleted;

/**
 * Created by mirza on 09/07/17.
 */

public class HomePresenter {

    HomeView view;
    Context context;

    public HomePresenter(HomeView view,Context context){
        this.view = view;
        this.context = context;
    }


    public void getMoviesFromTMDb(String sortMethod) {
        if (isNetworkAvailable()) {

            String apiKey = context.getString(R.string.key_themoviedb);

            // Listener for when AsyncTask is ready to update UI
            OnTaskCompleted taskCompleted = new OnTaskCompleted() {
                @Override
                public void onFetchDataCompleted(Movie[] movies) {
                    view.updateGrid(movies);
                }
            };

            // Execute task
            FetchMovieAsyncTask movieTask = new FetchMovieAsyncTask(taskCompleted, apiKey);
            movieTask.execute(sortMethod);
        } else {
            Toast.makeText(context, context.getString(R.string.error_need_internet), Toast.LENGTH_LONG).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public String getSortMethod() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        return prefs.getString(context.getString(R.string.pref_sort_method_key),
                context.getString(R.string.tmdb_sort_pop_desc));
    }


    public void updateSharedPrefs(String sortMethod) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.pref_sort_method_key), sortMethod);
        editor.apply();
    }


}
