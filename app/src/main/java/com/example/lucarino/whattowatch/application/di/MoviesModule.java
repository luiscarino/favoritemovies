package com.example.lucarino.whattowatch.application.di;

import com.example.lucarino.whattowatch.common.reactive.MainBus;
import com.example.lucarino.whattowatch.movies.MoviesContract;
import com.example.lucarino.whattowatch.movies.MoviesInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lucarino on 10/16/15.
 */

@Module
public class MoviesModule {

    public MoviesModule(){

    }

    @Singleton
    @Provides
    public MoviesInteractor provideMoviesInteractor(){
        return new MoviesInteractor();
    }

    @Singleton
    @Provides
    public MoviesContract.Interactor providesMoviesContractInteractor(){
        return new MoviesInteractor();
    }


    @Singleton
    @Provides
    public MainBus provideBus() {
        return new MainBus();
    }

}
