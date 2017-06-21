package com.hoymm.root.morsecodeconverter.InputOutputFields;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hoymm.root.morsecodeconverter.MorseToTextSwappingPanel;
import com.hoymm.root.morsecodeconverter.R;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by root on 06.05.17.
 */

public class ConvertingTextFieldsPanel {
    private Context myContext;
    private EditText bottomTextBox;
    private ImageButton copyToClipboardButton;
    private ResizingAnimationForTextBoxes resizingAnimationForTextBoxes;
    private RemovingInsertingTextAnimation removingInsertingTextAnimation;

    public ConvertingTextFieldsPanel(Context context) {
        myContext = context;
        resizingAnimationForTextBoxes = new ResizingAnimationForTextBoxes(context);
        removingInsertingTextAnimation = new RemovingInsertingTextAnimation(context);
        initObjects();
        setClipboardButtonBehavior();

    }

    private void initObjects() {
        bottomTextBox = (EditText) getActivity().findViewById(R.id.bottom_edit_text_box);
    }

    private void setClipboardButtonBehavior() {
        copyToClipboardButton = (ImageButton) getActivity().findViewById(R.id.clipboard_button_id);
        copyToClipboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), getActivity().getString(R.string.copied_to_clipboard)
                        , Toast.LENGTH_SHORT).show();

                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", bottomTextBox.getText().toString());
                clipboard.setPrimaryClip(clip);
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
