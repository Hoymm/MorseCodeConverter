package com.hoymm.root.morsecodeconverter._2_ApplicationBody;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

import com.hoymm.root.morsecodeconverter.R;

/**
 * Created by root on 15.06.17.
 */

class CalculateHeightsForInputOutput {
    private Context myContext;
    int lowerBoxBigHeight, upperBoxSmallHeight, lowerBoxSmallHeight, upperBoxBigHeight;

    CalculateHeightsForInputOutput(Context context) {
        myContext = context;
        calculateAndInitializateBoxHeightsObjects();
    }

    private void calculateAndInitializateBoxHeightsObjects() {
        whenMorseEnabled();
        whenMorseDisabled();
    }

    private void whenMorseEnabled() {
        int freeHeightSpaceWhenMorseEnabled = getFreeSpaceVerticallyForBoxes() - getMorseKeyboardHeight();
        upperBoxBigHeight = freeHeightSpaceWhenMorseEnabled/20 *14;
        lowerBoxSmallHeight = freeHeightSpaceWhenMorseEnabled/20 *5;
    }

    private void whenMorseDisabled() {
        int freeHeightSpaceWhenMorseDisabled = getFreeSpaceVerticallyForBoxes();
        upperBoxSmallHeight = freeHeightSpaceWhenMorseDisabled/20 *5;
        lowerBoxBigHeight = freeHeightSpaceWhenMorseDisabled/20 *14;
    }

    private int getFreeSpaceVerticallyForBoxes() {
        DisplayMetrics displayMetrics = myContext.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels - calculateHeightOfRestAppElements();
    }

    private int calculateHeightOfRestAppElements() {
        int height = 0;
        height += getSpinnerPanelHeight();
        height += getSwappingPanelHeight();
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

    private int getFooterPanelHeight() {
        LinearLayout footerWithButtons = (LinearLayout) getActivity().findViewById(R.id.applicationFooterId);
        return footerWithButtons.getLayoutParams().height;
    }

    private int getMorseKeyboardHeight() {
        LinearLayout morseKeyboard = (LinearLayout) getActivity().findViewById(R.id.morseKeyboardId);
        return morseKeyboard.getLayoutParams().height;
    }

    private Activity getActivity(){
        return ((Activity)myContext);
    }

}
