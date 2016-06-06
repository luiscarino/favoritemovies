package com.example.lucarino.whattowatch.common.event;

/**
 * Base event class.
 * Created by luiscarino on 6/3/16.
 */

public class BaseEvent {

    private boolean isSuccess;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
