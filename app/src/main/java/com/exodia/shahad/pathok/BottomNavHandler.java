package com.exodia.shahad.pathok;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BottomNavHandler {
    private static final String TAG = "BottomNavHandler";

    public static void setUpBottomNav(BottomNavigationViewEx bottomNav){
        bottomNav.enableAnimation(false);
        bottomNav.enableShiftingMode(false);
        bottomNav.enableItemShiftingMode(false);
        bottomNav.setTextVisibility(false);
    }
}
