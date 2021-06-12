package com.hansung.android.myandroidapp;

import com.squareup.otto.Bus;

public final class BusProvider extends Bus{
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
    }
}

