package com.example.lucarino.whattowatch.domain;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lucarino.whattowatch.application.MoviesApplication;
import com.example.lucarino.whattowatch.R;
import com.example.lucarino.whattowatch.data.Movies;
import com.example.lucarino.whattowatch.data.Result;
import com.example.lucarino.whattowatch.movies.MoviesFragment;
import com.example.lucarino.whattowatch.util.Logger;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter used to populated the grid of movies shown in the UI {@link MoviesFragment}
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder>{

    private Movies mMoviesSet;
    private List<Result> mResultSet;

    private final String API_IMAGE_PATH = "http://image.tmdb.org/t/p/w342/";

    @Inject
    Context context;

    static private OnItemClickListener onItemClickListener;

    public MoviesAdapter(List<Result> movies, OnItemClickListener listener) {
        onItemClickListener = listener;
        mResultSet = movies;
        MoviesApplication.getApplicationComponent().inject(this);

    }

    /**
     * Replaces the set of movies that is displayed in the UI.
     * @param movies - The movies object that holds an array of movie{@link Result}
     */
    public void replaceData(Movies movies){
        if(movies != null && movies.getResults() != null){
            mMoviesSet = movies;
            notifyDataSetChanged();
        }
    }

    public void addData(List<Result> movies) {
        mResultSet.addAll(movies);
        notifyDataSetChanged();
    }

    public void sortBy(int sortType){

        final int POPULARITY = 1;
        final int BEST_RATED = 0;

        switch (sortType){
            case POPULARITY:
                sortByPopularity();
                break;
            case BEST_RATED:
                sortByBestRated();
                break;
        }
    }

    private void sortByBestRated(){
        Collections.sort(mResultSet, Result.comparatorByVoteAverage);
        notifyDataSetChanged();
    }

    private void sortByPopularity(){
        Collections.sort(mResultSet, Result.comparatorByPopularity);
        notifyDataSetChanged();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.imageViewPoster)
        ImageView mImageViewPoster;
        Result movie;
        @Bind(R.id.tv_user_vote)
        TextView tvUserVote;
        @Bind(R.id.iv_favorite)
        ImageView ivFavorite;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (movie != null) {
                onItemClickListener.onClick(movie);
            } else {
                Log.d(Logger.LOG_ERROR, "Movie object is not bind to the adapter");
            }

        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_item, viewGroup, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        Result movie = mResultSet.get(i);
        viewHolder.movie = movie;

        String image_path = API_IMAGE_PATH + movie.getPosterPath();
        Picasso.with(context)
                .load(image_path)
                .fit()
                .into(viewHolder.mImageViewPoster);

        viewHolder.tvUserVote.setText(String.format(context.getString(R.string.vote_placeholder),
                DecimalFormat.getInstance().format(movie.getVoteAverage())));

    }

    @Override
    public int getItemCount() {
        return mResultSet.size();
    }


    // Listener to adapter's movie items
    public interface OnItemClickListener {

        void onClick(Result movie);
    }


    public List<Result> getResultSet() {
        return mResultSet;
    }
}