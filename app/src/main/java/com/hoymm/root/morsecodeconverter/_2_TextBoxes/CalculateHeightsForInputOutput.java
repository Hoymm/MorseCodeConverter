package com.hoymm.root.morsecodeconverter._2_TextBoxes;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

import com.hoymm.root.morsecodeconverter.R;

/**
 * Created by root on 15.06.17.
 */

class CalculateHeightsForInputOutput {
    private final static int boxesSizedivider = 20
            , upperBoxSizeMultipler = 6
            , bottomBoxSizeMultipler = 13;
    private Context myContext;
    int lowerBoxBigHeight, upperBoxSmallHeight, boxSmallHeight, boxBigHeight;

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
        boxBigHeight = freeHeightSpaceWhenMorseEnabled/boxesSizedivider *bottomBoxSizeMultipler;
        boxSmallHeight = freeHeightSpaceWhenMorseEnabled/boxesSizedivider *upperBoxSizeMultipler;
    }

    private void whenMorseDisabled() {
        int freeHeightSpaceWhenMorseDisabled = getFreeSpaceVerticallyForBoxes();
        upperBoxSmallHeight = freeHeightSpaceWhenMorseDisabled/boxesSizedivider *upperBoxSizeMultipler;
        lowerBoxBigHeight = freeHeightSpaceWhenMorseDisabled/boxesSizedivider *bottomBoxSizeMultipler;
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
        if (swappingTranslatingModePanel == null)
            return 0;
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
