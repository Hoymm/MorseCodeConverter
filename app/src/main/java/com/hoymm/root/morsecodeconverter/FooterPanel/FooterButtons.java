package com.hoymm.root.morsecodeconverter.FooterPanel;

import android.app.Activity;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public interface FooterButtons {
    void start(int time);
    boolean isPermissionGranted();
    void setButtonBehavior();
    void initObjects();
    Activity getActivity();
}
