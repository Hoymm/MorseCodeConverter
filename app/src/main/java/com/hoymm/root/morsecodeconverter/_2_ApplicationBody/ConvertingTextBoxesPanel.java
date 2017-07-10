package com.hoymm.root.morsecodeconverter._2_ApplicationBody;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.hoymm.root.morsecodeconverter._1_Header.MorseToTextSwappingPanelConversion;
import com.hoymm.root.morsecodeconverter.R;
/**
 * Created by root on 06.05.17.
 */

public class ConvertingTextBoxesPanel {
    private Context myContext;
    private EditText upperTextBox;
    private TextView bottomTextBox;

    private ResizingTextBoxesAnimation resizingTextBoxesAnimation;

    public ConvertingTextBoxesPanel(Context context) {
        myContext = context;
        resizingTextBoxesAnimation = new ResizingTextBoxesAnimation(context);
        initXMLObjects();

    }

    private void initXMLObjects() {
        upperTextBox = (EditText) getActivity().findViewById(R.id.upper_edit_text_box);
        bottomTextBox = (TextView) getActivity().findViewById(R.id.bottom_text_view_box);
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
        upperTextBox.clearFocus();
    }

    public void swapTextInsideBoxes() {
        Long time = System.currentTimeMillis();
        String tempText = bottomTextBox.getText().toString();
        bottomTextBox.setText(upperTextBox.getText().toString());
        upperTextBox.setText(tempText);
        Log.i("Convert Time: ", (System.currentTimeMillis()-time) + "ms");
    }
}
