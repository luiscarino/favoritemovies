package com.example.lucarino.whattowatch.domain;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lucarino.whattowatch.R;
import com.example.lucarino.whattowatch.data.Result;
import com.example.lucarino.whattowatch.util.Logger;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by skyfishjy on 10/31/14.
 */
public class MyListCursorAdapter extends CursorRecyclerViewAdapter<MyListCursorAdapter.ViewHolder> {

    static private MyListCursorAdapter.OnItemClickListener onItemClickListener;
    Context mContext;
    private final String API_IMAGE_PATH = "http://image.tmdb.org/t/p/w342/";


    public MyListCursorAdapter(Context context, Cursor cursor, MyListCursorAdapter.OnItemClickListener listener) {
        super(context, cursor);
        mContext = context;
        onItemClickListener = listener;
    }

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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);

        MyListCursorAdapter.ViewHolder vh = new MyListCursorAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {

        Result movie = new Result(cursor.getInt(1), cursor.getString(2),  cursor.getString(3), cursor.getDouble(5), cursor.getString(7), cursor.getDouble(6), cursor.getString(4));
        viewHolder.movie = movie;

        String image_path = API_IMAGE_PATH + movie.getPosterPath();
        Picasso.with(mContext)
                .load(image_path)
                .fit()
                .into(viewHolder.mImageViewPoster);

        viewHolder.tvUserVote.setText(String.format(mContext.getString(R.string.vote_placeholder),
                DecimalFormat.getInstance().format(movie.getVoteAverage())));

    }


    // Listener to adapter's movie items
    public interface OnItemClickListener {

        void onClick(Result movie);
    }
}