package movies;

import com.example.lucarino.whattowatch.data.Movies;
import com.example.lucarino.whattowatch.movies.MoviesContract;
import com.example.lucarino.whattowatch.movies.MoviesInteractor;
import com.example.lucarino.whattowatch.movies.MoviesPresenter;

import org.mockito.Mock;

import java.util.List;

/**
 * Unit test for the implementation of {@link MoviesPresenter}
 */
public class MoviesPresenterTest {



    @Mock
    private MoviesContract.View mMoviesView;

    @Mock
    private MoviesInteractor moviesInteractor;
}
