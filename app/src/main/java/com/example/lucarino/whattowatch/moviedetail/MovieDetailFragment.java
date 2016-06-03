package com.example.lucarino.whattowatch.moviedetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lucarino.whattowatch.R;
import com.example.lucarino.whattowatch.data.Result;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Display detail information for the selected movie {@link Result} item from the
 * grid shown in the {@link com.example.lucarino.whattowatch.movies.MoviesFragment}
 */
public class MovieDetailFragment extends Fragment implements MovieDetailContract.View {

    @Bind(R.id.imageViewPoster)
    ImageView imageViewPoster;

    @Bind(R.id.textViewTitle)
    TextView textViewOriginalTitle;

    @Bind(R.id.textViewRelaseDate)
    TextView textViewReleaseDate;

    @Bind(R.id.textViewSynopsis)
    TextView textViewSynopsis;

    @Bind(R.id.duration_textview)
    TextView durationTextView;

    @Bind(R.id.rating_textview)
    TextView ratingTextView;

    private Result mMovieResult;

    private final String API_IMAGE_PATH = "http://image.tmdb.org/t/p/w342/";

    private final static String ARG_MOVIE_KEY = "movie.result.key";

    public MovieDetailFragment() {
        // Necessary default constructor
    }

    /**
     * This is used to create a new instance of this fragment that will hold a movie {@link Result}
     * as parameter
     *
     * @param movie - The Movie object that holds the content to display in the UI.
     * @return
     */
    public static Fragment newInstance(Result movie) {
        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE_KEY, movie);

        movieDetailFragment.setArguments(args);
        return movieDetailFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ARG_MOVIE_KEY)) {
            mMovieResult = (Result) getArguments().getSerializable(ARG_MOVIE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inject views using Butterknife
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadMovieDetail(mMovieResult);
    }

    @Override
    public void loadMovieDetail(Result movie) {

        textViewOriginalTitle.setText(movie.getTitle());
        textViewReleaseDate.setText(movie.getReleaseDate());
        textViewSynopsis.setText(movie.getOverview());

        String image_path = API_IMAGE_PATH + movie.getPosterPath();


        Picasso.with(getContext())
                .load(image_path)
                .into(imageViewPoster);

        ratingTextView.setText(movie.getVoteAverage() + "/10");
    }

    // El fragment recibe el id de la pelicula
    // El fragment le pasa el url de la prelicula al presenter
    // El presenter utiliza el ID y le dice al interactor que vaya al servidory traiga la informacion de la pelicula
    // El presenter actualiza el UI mostrando la barra de progreso
    // El interactor consulta el servidor con el ID en el background
    // El interactor lanza un callback informando el estado de la respuesta
    // El preseter maneja la respuesta entregada por el callback
    // El presenter actualiza la vista basado en la respuesta

}
