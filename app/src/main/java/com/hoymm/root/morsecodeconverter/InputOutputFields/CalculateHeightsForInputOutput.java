package com.hoymm.root.morsecodeconverter.InputOutputFields;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

import com.hoymm.root.morsecodeconverter.R;

/**
 * Created by root on 15.06.17.
 */

public class CalculateHeightsForInputOutput {
    private Context myContext;
    public int lowerBoxBigHeight, upperBoxSmallHeight, lowerBoxSmallHeight, upperBoxBigHeight;

    public CalculateHeightsForInputOutput(Context context) {
        myContext = context;
        calculateAndInitializateBoxHeightsObjects();
    }

    private int getFreeSpaceVerticallyForBoxes() {
        DisplayMetrics displayMetrics = myContext.getResources().getDisplayMetrics();
        int deviceHeight = displayMetrics.heightPixels;
        return deviceHeight - calculateDPHeightOfRestAppElements();
    }

    private int calculateDPHeightOfRestAppElements() {
        int height = 0;
        height += getSpinnerPanelHeight();
        height += getSwappingPanelHeight();
        height += getMorseKeyboardHeight();
        height += getFooterPanelHeight();
        return height;
    }

    private int getSpinnerPanelHeight() {
        LinearLayout topSpinnerBar = (LinearLayout) getActivity().findViewById(R.id.headerId);
        return topSpinnerBar.getLayoutParams().height;
    }

    private int getSwappingPanelHeight() {
        LinearLayout swappingTranslatingModePanel = (LinearLayout) getActivity().findViewById(R.id.swappingPanelId);
        return swappingTranslatingModePanel.getLayoutParams().height;
    }

    private int getMorseKeyboardHeight() {
        LinearLayout morseKeyboard = (LinearLayout) getActivity().findViewById(R.id.morseKeyboardId);
        return morseKeyboard.getLayoutParams().height;
    }

    private int getFooterPanelHeight() {
        LinearLayout footerWithButtons = (LinearLayout) getActivity().findViewById(R.id.applicationFooterId);
        return footerWithButtons.getLayoutParams().height;
    }

    private void calculateAndInitializateBoxHeightsObjects() {
        whenMorseEnabled();
        whenMorseDisabled();
    }

    private void whenMorseEnabled() {
        int freeHeightSpaceWhenMorseEnabled = getFreeSpaceVerticallyForBoxes();
        upperBoxBigHeight = freeHeightSpaceWhenMorseEnabled/8 *5;
        lowerBoxSmallHeight = freeHeightSpaceWhenMorseEnabled/8 *2;
    }

    private void whenMorseDisabled() {
        int freeHeightSpaceWhenMorseDisabled = getFreeSpaceVerticallyForBoxes() - getMorseKeyboardHeight();
        upperBoxSmallHeight = freeHeightSpaceWhenMorseDisabled/8 *2;
        lowerBoxBigHeight = freeHeightSpaceWhenMorseDisabled/8 *5;
    }

    private Activity getActivity(){
        return ((Activity)myContext);
    }

}
