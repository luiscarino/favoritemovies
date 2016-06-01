package com.example.lucarino.whattowatch.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by lucarino on 10/17/15.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerApp {
}
