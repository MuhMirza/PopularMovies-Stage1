package id.muhmirza.popularmovies.ui.home;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import id.muhmirza.popularmovies.R;
import id.muhmirza.popularmovies.model.Movie;

/**
 * Created by mirza on 09/07/17.
 */
public class ImageAdapter extends BaseAdapter {
    private final Context mContext;
    private final Movie[] mMovies;

    public ImageAdapter(Context context, Movie[] movies) {
        mContext = context;
        mMovies = movies;
    }

    @Override
    public int getCount() {
        if (mMovies == null || mMovies.length == 0) {
            return -1;
        }

        return mMovies.length;
    }

    @Override
    public Movie getItem(int pos) {
        if (mMovies == null || mMovies.length == 0) {
            return null;
        }

        return mMovies[pos];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        // Will be null if it's not recycled. Will initialize ImageView if new.
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setAdjustViewBounds(true);
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext)
                .load(mMovies[position].getPosterPath())
                .resize(mContext.getResources().getInteger(R.integer.tmdb_poster_w185_width),
                        mContext.getResources().getInteger(R.integer.tmdb_poster_w185_height))
                .error(R.drawable.ic_error)
                .placeholder(R.drawable.ic_loading)
                .into(imageView);

        return imageView;
    }
}
