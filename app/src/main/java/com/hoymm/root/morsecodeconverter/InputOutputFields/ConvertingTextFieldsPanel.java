package com.hoymm.root.morsecodeconverter.InputOutputFields;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter.MorseToTextSwappingPanel;
import com.hoymm.root.morsecodeconverter.R;

/**
 * Created by root on 06.05.17.
 */

public class ConvertingTextFieldsPanel {
    private Context myContext;
    private ImageButton copyToClipboardButton;
    private AnimationForEditTextBoxes animationForEditTextBoxes;

    public ConvertingTextFieldsPanel(Context context) {
        myContext = context;
        animationForEditTextBoxes = new AnimationForEditTextBoxes(context);
        initializeImageButtons();

    }

    private void initializeImageButtons() {
        copyToClipboardButton = (ImageButton) getActivity().findViewById(R.id.clipboard_button_id);

        // TODO delete that what's below
        copyToClipboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    public void resizeBoxesAnimation(){
        if (MorseToTextSwappingPanel.convertTextToMorse)
            constrictUpperBoxAndExtendLowerBox();
        else
            extendUpperBoxAndConstrictLowerBox();
    }

    private void constrictUpperBoxAndExtendLowerBox() {
        animationForEditTextBoxes.upperBoxToSmall.start();
        animationForEditTextBoxes.lowerBoxToBig.start();
    }

    private void extendUpperBoxAndConstrictLowerBox() {
        animationForEditTextBoxes.upperBoxToBig.start();
        animationForEditTextBoxes.lowerBoxToSmall.start();
    }

    private Context getContext(){
        return myContext;
    }

    private Activity getActivity(){
        return ((Activity)getContext());
    }

    public boolean ifNoAnimationCurrentlyRunning() {
        return !(animationForEditTextBoxes.upperBoxToBig.isRunning() ||
                animationForEditTextBoxes.lowerBoxToBig.isRunning() ||
                animationForEditTextBoxes.upperBoxToSmall.isRunning() ||
                animationForEditTextBoxes.lowerBoxToSmall.isRunning());
    }

}
