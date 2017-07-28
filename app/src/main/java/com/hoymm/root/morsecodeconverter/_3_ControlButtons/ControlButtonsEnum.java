package com.hoymm.root.morsecodeconverter._3_ControlButtons;

/**
 * File created by Damian Muca - Kaizen on 28.07.17.
 */

public enum ControlButtonsEnum {
    PLAY, PAUSE, STOP, NONE;

    static ControlButtonsEnum stringToEnum(String myEnumString) {
        try {
            return valueOf(myEnumString);
        } catch (Exception ex) {
            return ControlButtonsEnum.NONE;
        }
    }
}
