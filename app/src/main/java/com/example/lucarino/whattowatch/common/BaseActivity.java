package com.example.lucarino.whattowatch.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.lucarino.whattowatch.R;
import com.example.lucarino.whattowatch.application.MoviesApplication;
import com.example.lucarino.whattowatch.common.reactive.MainBus;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by luiscarino on 6/3/16.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    protected MainBus mBus;

    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_base_activity);

        MoviesApplication.getApplicationComponent().inject(this);

        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
    }



    protected void onResume() {
        super.onResume();
        // Subscribe to EventBus
        mBus.getBusObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSubscriber());
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unsubscribe from EventBus
        getSubscriber().unsubscribe();
    }

    protected abstract Subscriber<? super Object>  getSubscriber();

}
