package com.hoymm.root.morsecodeconverter._2_TextBoxes;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextSwappingPanelConversion;

/**
 * Created by Hoymm - Damian Muca on 06.05.17.
 */

public class ConvertingTextBoxesPanel {
    private Context myContext;

    private ResizingTextBoxesAnimation resizingTextBoxesAnimation;

    public ConvertingTextBoxesPanel(Context context) {
        myContext = context;
        resizingTextBoxesAnimation = new ResizingTextBoxesAnimation(context);
    }

    public void resizeBoxesAnimation(){
        if (MorseToTextSwappingPanelConversion.isConvertingTextToMorse)
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
        TextBoxes.initAndGetUpperBox(getActivity()).clearFocus();
    }

    public void swapTextInsideBoxes() {
        Long time = System.currentTimeMillis();
        String tempText = TextBoxes.initAndGetBottomBox(getActivity()).getText().toString();
        TextBoxes.initAndGetBottomBox(getActivity()).setText
                (TextBoxes.initAndGetUpperBox(getActivity()).getText().toString());
        TextBoxes.initAndGetUpperBox(getActivity()).setText(tempText);
        Log.i("Convert Time: ", (System.currentTimeMillis()-time) + "ms");
    }
}
