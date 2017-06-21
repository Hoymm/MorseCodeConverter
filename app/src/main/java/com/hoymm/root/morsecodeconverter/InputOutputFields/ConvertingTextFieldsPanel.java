package com.hoymm.root.morsecodeconverter.InputOutputFields;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter.MorseToTextSwappingPanel;
import com.hoymm.root.morsecodeconverter.R;

/**
 * Created by root on 06.05.17.
 */

public class ConvertingTextFieldsPanel {
    private Context myContext;
    private ImageButton copyToClipboardButton;
    private ResizingAnimationForTextBoxes resizingAnimationForTextBoxes;
    private RemovingInsertingTextAnimation removingInsertingTextAnimation;

    public ConvertingTextFieldsPanel(Context context) {
        myContext = context;
        resizingAnimationForTextBoxes = new ResizingAnimationForTextBoxes(context);
        removingInsertingTextAnimation = new RemovingInsertingTextAnimation(context);
        initObjects();

    }

    private void initObjects() {
        initImageButtons();
    }

    private void initImageButtons() {
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

    public void swapTextInsideBoxesAnimation() {
        removingInsertingTextAnimation.startAnimation();
    }

    private void constrictUpperBoxAndExtendLowerBox() {
        resizingAnimationForTextBoxes.upperBoxToSmall.start();
        resizingAnimationForTextBoxes.lowerBoxToBig.start();
    }

    private void extendUpperBoxAndConstrictLowerBox() {
        resizingAnimationForTextBoxes.upperBoxToBig.start();
        resizingAnimationForTextBoxes.lowerBoxToSmall.start();
    }

    private Context getContext(){
        return myContext;
    }

    private Activity getActivity(){
        return ((Activity)getContext());
    }

    public boolean ifNoAnimationCurrentlyRunning() {
        return !(resizingAnimationForTextBoxes.upperBoxToBig.isRunning() ||
                resizingAnimationForTextBoxes.lowerBoxToBig.isRunning() ||
                resizingAnimationForTextBoxes.upperBoxToSmall.isRunning() ||
                resizingAnimationForTextBoxes.lowerBoxToSmall.isRunning() ||
                removingInsertingTextAnimation.upperRemoveText.isRunning() ||
                removingInsertingTextAnimation.upperAddText.isRunning() ||
                removingInsertingTextAnimation.bottomRemoveText.isRunning() ||
                removingInsertingTextAnimation.bottomAddText.isRunning()
        );
    }
}
