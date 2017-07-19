package com.hoymm.root.morsecodeconverter._2_TextBoxes;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextArrowsSwap;

/**
 * Created by Hoymm - Damian Muca on 06.05.17.
 */

public class HandleTextBoxesConversion {
    private Context myContext;

    private ResizingTextBoxesAnimation resizingTextBoxesAnimation;

    public HandleTextBoxesConversion(Context context) {
        myContext = context;
        resizingTextBoxesAnimation = new ResizingTextBoxesAnimation(context);
    }

    public void resizeBoxesAnimation(){
        if (MorseToTextArrowsSwap.isConvertingTextToMorse)
            constrictUpperBoxAndExtendLowerBox();
        else
            extendUpperBoxAndConstrictLowerBox();
    }

    private void constrictUpperBoxAndExtendLowerBox() {
        resizingTextBoxesAnimation.upperBoxToSmall.start();
        resizingTextBoxesAnimation.lowerBoxToBig.start();
    }

    private void extendUpperBoxAndConstrictLowerBox() {
        resizingTextBoxesAnimation.upperBoxToBig.start();
        resizingTextBoxesAnimation.lowerBoxToSmall.start();
    }

    private Context getContext(){
        return myContext;
    }

    private Activity getActivity(){
        return ((Activity)getContext());
    }

    public boolean ifNoAnimationCurrentlyRunning() {
        return !(resizingTextBoxesAnimation.upperBoxToBig.isRunning() ||
                resizingTextBoxesAnimation.lowerBoxToBig.isRunning() ||
                resizingTextBoxesAnimation.upperBoxToSmall.isRunning() ||
                resizingTextBoxesAnimation.lowerBoxToSmall.isRunning()
        );
    }

    public void clearSelection() {
        upperTextBox().clearFocus();
    }

    public void swapTextInsideBoxes() {
        Long time = System.currentTimeMillis();
        String tempText = bottomTextBox().getText().toString();
        bottomTextBox().setText(upperTextBox().getText().toString());
        upperTextBox().setText(tempText);
        Log.i("Convert Time: ", (System.currentTimeMillis()-time) + "ms");
    }

    public void setSelectionsAtTheEnd() {
        upperTextBox().setSelection(upperTextBox().getText().toString().length());
    }

    private TextView bottomTextBox() {
        return TextBoxes.initAndGetBottomBox(getActivity());
    }

    private EditText upperTextBox() {
        return TextBoxes.initAndGetUpperBox(getActivity());
    }
}
