package com.example.lucarino.whattowatch;

import android.app.Application;

import com.example.lucarino.whattowatch.daggercomponent.ApplicationComponent;
import com.example.lucarino.whattowatch.daggercomponent.DaggerApplicationComponent;
import com.example.lucarino.whattowatch.daggermodule.ApplicationModule;
import com.example.lucarino.whattowatch.daggermodule.MoviesModule;
import com.facebook.stetho.Stetho;

/**
 * @author lucarino
 */
public class MoviesApplication extends Application {

    private static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // initialize Stetho
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());

        buildDaggerGraph();
    }

    public void buildDaggerGraph() {

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .moviesModule(new MoviesModule())
                .build();
    }

    public static ApplicationComponent getApplicationComponent(){
        return  applicationComponent;
    }
}
