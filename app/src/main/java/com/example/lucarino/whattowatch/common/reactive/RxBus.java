package com.example.lucarino.whattowatch.common.reactive;

import android.util.Log;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * RxJava Bus.
 */

public class RxBus {

    public static final String TAG = RxBus.class.getSimpleName();

    private final Subject<Object, Object> bus = new SerializedSubject<>(PublishSubject.create());

    public void post(Object o) {
        Log.i(TAG, "Object posted to bus: " + o.toString());
        bus.onNext(o);
    }

    public Observable<Object> getBusObservable() {
        return bus;
    }

}
