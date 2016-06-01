package com.example.lucarino.whattowatch.daggercomponent;

import com.example.lucarino.whattowatch.daggermodule.MoviesModule;
import com.example.lucarino.whattowatch.movies.MoviesContract;
import com.example.lucarino.whattowatch.movies.MoviesInteractor;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Dagger component for the {@link MoviesModule} that provides the classes that are injectable.
 *
 */

@Singleton
@Component (modules = {MoviesModule.class})
public interface MoviesComponent {

    MoviesInteractor moviesInteractor();
    MoviesContract.Interactor moviesContractInteractor();
}
