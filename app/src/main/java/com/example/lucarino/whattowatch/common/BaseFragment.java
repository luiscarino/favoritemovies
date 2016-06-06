package com.example.lucarino.whattowatch.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.lucarino.whattowatch.application.MoviesApplication;
import com.example.lucarino.whattowatch.common.reactive.MainBus;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by luiscarino on 6/3/16.
 */

public abstract class BaseFragment extends Fragment {

    @Inject
    protected MainBus mBus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MoviesApplication.getApplicationComponent().inject(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        // Subscribe to EventBus
        mBus.getBusObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSubscriber());
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unsubscribe from EventBus
        getSubscriber().unsubscribe();
    }

    protected abstract Subscriber<? super Object> getSubscriber();
}
