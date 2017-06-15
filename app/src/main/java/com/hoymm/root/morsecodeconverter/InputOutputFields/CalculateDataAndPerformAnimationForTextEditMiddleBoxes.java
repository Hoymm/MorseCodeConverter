package com.hoymm.root.morsecodeconverter.InputOutputFields;

import android.app.Activity;
import android.content.Context;

/**
 * Created by root on 15.05.17.
 */

public class CalculateDataAndPerformAnimationForTextEditMiddleBoxes {
    private Context myContext;
    private AnimationForTextEditBoxes boxesForTranslatingText;
    private CalculateHeightsForInputOutput editTextFieldsHeights;

    public CalculateDataAndPerformAnimationForTextEditMiddleBoxes(Context context){
        myContext = context;

    }


    private Activity getActivity(){
        return ((Activity)myContext);
    }

    public void extendUpperBox() {
        boxesForTranslatingText.upperBoxToBig.start();
    }

    public void extendLowerBox() {
        boxesForTranslatingText.lowerBoxToBig.start();
    }

    public void constrictUpperBox() {
        boxesForTranslatingText.upperBoxToSmall.start();
    }

    public void constrictLowerBox() {
        boxesForTranslatingText.lowerBoxToSmall.start();
    }
}
