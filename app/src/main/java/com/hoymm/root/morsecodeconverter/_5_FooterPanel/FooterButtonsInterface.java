package com.hoymm.root.morsecodeconverter._5_FooterPanel;

/**
 * File created by Damian Muca - Kaizen on 11.07.17.
 */

interface FooterButtonsInterface {
    boolean isPermissionGranted();
    void startIfActiveAndPermissionsGranted(int time);
    boolean isButtonActive();
}
