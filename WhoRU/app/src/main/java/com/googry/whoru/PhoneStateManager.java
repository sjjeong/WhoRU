package com.googry.whoru;

/**
 * Created by SeokJun on 2016-06-30.
 */
public class PhoneStateManager {
    private static PhoneStateManager instance;

    private PhoneStateManager() {
    }

    public static PhoneStateManager getInstance() {
        if (instance == null) {
            instance = new PhoneStateManager();
        }
        return instance;
    }
}
