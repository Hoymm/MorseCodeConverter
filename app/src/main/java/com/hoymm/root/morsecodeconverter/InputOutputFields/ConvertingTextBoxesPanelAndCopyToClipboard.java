package com.hoymm.root.morsecodeconverter.InputOutputFields;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hoymm.root.morsecodeconverter.MorseToTextSwappingPanel;
import com.hoymm.root.morsecodeconverter.R;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by root on 06.05.17.
 */

public class ConvertingTextBoxesPanelAndCopyToClipboard {
    private Context myContext;
    private EditText upperTextBox;

    private ResizingTextBoxesAnimation resizingTextBoxesAnimation;
    private RemovingInsertingTextAnimation removingInsertingTextAnimation;

    public ConvertingTextBoxesPanelAndCopyToClipboard(Context context) {
        myContext = context;
        resizingTextBoxesAnimation = new ResizingTextBoxesAnimation(context);
        removingInsertingTextAnimation = new RemovingInsertingTextAnimation(context);
        initXMLObjects();

    }

    private void initXMLObjects() {
        upperTextBox = (EditText) getActivity().findViewById(R.id.upper_edit_text_box);
    }

    public void resizeBoxesAnimation(){
        if (MorseToTextSwappingPanel.isConvertingTextToMorse)
            constrictUpperBoxAndExtendLowerBox();
        else
            extendUpperBoxAndConstrictLowerBox();
    }

    public void swapTextInsideBoxesAnimation() {
        removingInsertingTextAnimation.startAnimation();

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
                resizingTextBoxesAnimation.lowerBoxToSmall.isRunning() ||
                removingInsertingTextAnimation.upperRemoveText.isRunning() ||
                removingInsertingTextAnimation.upperAddText.isRunning() ||
                removingInsertingTextAnimation.bottomRemoveText.isRunning() ||
                removingInsertingTextAnimation.bottomAddText.isRunning()
        );
    }

    public void clearSelection() {
        upperTextBox.clearFocus();
    }
}
