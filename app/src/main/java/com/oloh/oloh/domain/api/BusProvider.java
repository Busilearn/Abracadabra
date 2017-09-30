package com.oloh.oloh.domain.api;

import com.squareup.otto.Bus;

/**
 * Created by micka on 15-Sep-17.
 */

public class BusProvider {

    private static final Bus BUS = new Bus();

    public static Bus getInstance(){
        return BUS;
    }

    public BusProvider(){}
}
