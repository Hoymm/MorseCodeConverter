package com.hoymm.root.morsecodeconverter.InputOutputFields;

import android.app.Activity;
import android.content.Context;

/**
 * Created by root on 15.05.17.
 */

class ResizeEditTextFieldsAnimations {
    private AnimationForEditTextBoxes animationForEditTextBoxes;

    ResizeEditTextFieldsAnimations(Context context){
        animationForEditTextBoxes = new AnimationForEditTextBoxes(context);
    }

    void extendUpperBox() {
        animationForEditTextBoxes.upperBoxToBig.start();
    }

    void extendLowerBox() {
        animationForEditTextBoxes.lowerBoxToBig.start();
    }

    void constrictUpperBox() {
        animationForEditTextBoxes.upperBoxToSmall.start();
    }

    void constrictLowerBox() {
        animationForEditTextBoxes.lowerBoxToSmall.start();
    }
}
