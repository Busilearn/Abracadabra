package com.apero_area.aperoarea.domain.api;

import android.support.annotation.NonNull;

import com.apero_area.aperoarea.model.entities.Product;

import java.util.ArrayList;

/**
 * Created by stran on 03/09/2017.
 */

public interface CallbackT<T> {
    void next(T result);
}