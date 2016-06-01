package com.example.lucarino.whattowatch.daggermodule;

import javax.inject.Singleton;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.lucarino.whattowatch.MoviesApplication;

import dagger.Module;
import dagger.Provides;

/**
 * Main application module for providing application context, shared preferences and other
 * utilities.
 * 
 * @author lucarino
 */


@Module
public class ApplicationModule {

    private final MoviesApplication application;


    public ApplicationModule(MoviesApplication application) {
        this.application = application;
    }


    @Singleton
    @Provides
    public Context provideApplicationContext() {
        return this.application;
    }

    @Singleton
    @Provides
    public SharedPreferences provideSharedPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(this.application);
    }

}
