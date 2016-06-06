package com.example.lucarino.whattowatch.application.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.lucarino.whattowatch.common.BaseActivity;
import com.example.lucarino.whattowatch.common.BaseFragment;
import com.example.lucarino.whattowatch.domain.MoviesAdapter;
import com.example.lucarino.whattowatch.movies.MoviesPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Main component of this application, it works as a bridge between the modules ( where how
 * dependencies are created) and @Inject annotations (where the app request Dagger to inject the
 * specified object).
 *
 * This is a class that provides the interface to get the classes that are injectable
 *
 * A Component must:
 *   Define the modules it is composed of as an argument in the @Component annotation
 *   Define any dependencies on other Components it has.
 *   Define functions to inject explicit types with dependencies.
 *   Expose any internal dependencies to be accessed externally or by other Components.
 *
 *
 *   public void inject(SomeActivity someActivity);
 *   public void inject(SomeFragment someFragment);
 *   public void inject(SomeOtherFragment someOtherFragment);
 */

@Singleton
@Component(
        modules = {ApplicationModule.class,
                MoviesModule.class}
        //this component can include -  dependencies = {OtherComponent.class}
)
public interface ApplicationComponent {

    // Define functions to inject explicit types with dependencies.
    void inject(BaseActivity baseActivity);
    void inject(BaseFragment baseFragment);
    void inject(MoviesAdapter moviesAdapter);
    void inject(MoviesPresenter moviesPresenter);

    // Expose injectable objects to sub-graphs.
    Context context();
    SharedPreferences sharedPreferences();

}
