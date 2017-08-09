package com.hoymm.root.morsecodeconverter._4_MorseKeyboard;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter.MorseToTextConversionProg.MorseCodeCipher;
import com.hoymm.root.morsecodeconverter.R;
import com.hoymm.root.morsecodeconverter._2_TextBoxes.TextBoxes;
import com.hoymm.root.morsecodeconverter._3_ControlButtons.StopButton;

/**
 * File created by Damian Muca - Kaizen on 27.06.17.
 */

public class SpaceButton extends ImageButton {
    private static ImageButton instance = null;
    private Activity activity;

    public static ImageButton initAndGetInstance(Activity activity){
        if (instance == null)
            instance = new SpaceButton(activity);
        return instance;
    }

    private SpaceButton(Activity activity) {
        super(activity);
        this.activity = activity;
        initObjects();
        setBackspaceButtonBehavior();
    }

    private void initObjects() {
        instance = (ImageButton) getActivity().findViewById(R.id.space_button_id);
    }

    private void setBackspaceButtonBehavior() {
        instance.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StopButton.initAndGetInstance(getActivity()).isActive())
                    onClickAction();
                else
                    TextBoxes.showToastEditTextAllowedOnlyWhenStopButtonActive(getActivity());
            }
        });
    }

    private void onClickAction(){
        removeSelectedTextIfAnythingSelected();
        insertShortOrLongSpace();
    }

    private void removeSelectedTextIfAnythingSelected() {
        String originalText = TextBoxes.initAndGetUpperBox(getActivity()).getText().toString();
        int selStart = TextBoxes.initAndGetUpperBox(getActivity()).getSelectionStart();
        int selEnd = TextBoxes.initAndGetUpperBox(getActivity()).getSelectionEnd();
        String textAfterRemovingSelectedText = originalText.substring(0, selStart) + originalText.substring(selEnd);

        TextBoxes.initAndGetUpperBox(getActivity()).setText(textAfterRemovingSelectedText);
        TextBoxes.initAndGetUpperBox(getActivity()).setSelection(selStart);
    }

    private void insertShortOrLongSpace() {
        int howManySpacesWereDeletedFromText = deleteSpacesFromTextAndCountDeletions();
        if (howManySpacesWereDeletedFromText != 1)
            insertShortSpace();
        else
            insertLongSpace();

    }

    private int deleteSpacesFromTextAndCountDeletions() {

        int howManySpacesDeleted = 0;
        while(ifSpaceIsOnTheLeftSelectionSide()){
            deleteSpaceOnLeft();
            howManySpacesDeleted++;
        }

        while(ifSpaceIsOnTheRightSelectionSide()){
            deleteSpaceOnRight();
            howManySpacesDeleted++;
        }

        return howManySpacesDeleted;
    }

    private boolean ifSpaceIsOnTheLeftSelectionSide() {
        String currentText = TextBoxes.initAndGetUpperBox(getActivity()).getText().toString();
        int selectionStart = TextBoxes.initAndGetUpperBox(getActivity()).getSelectionStart();
        return selectionStart > 0 && currentText.substring(selectionStart-1,selectionStart).equals(MorseCodeCipher.SHORT_GAP);
    }

    private void deleteSpaceOnLeft() {
        String currentText = TextBoxes.initAndGetUpperBox(getActivity()).getText().toString();
        int selectionStart = TextBoxes.initAndGetUpperBox(getActivity()).getSelectionStart();
        currentText = currentText.substring(0, selectionStart-1) + currentText.substring(selectionStart);

        TextBoxes.initAndGetUpperBox(getActivity()).setText(currentText);
        TextBoxes.initAndGetUpperBox(getActivity()).setSelection(selectionStart-1);
    }

    private boolean ifSpaceIsOnTheRightSelectionSide() {
        String currentText = TextBoxes.initAndGetUpperBox(getActivity()).getText().toString();
        int selectionStart = TextBoxes.initAndGetUpperBox(getActivity()).getSelectionStart();
        return currentText.length() > selectionStart && currentText.substring(selectionStart,selectionStart+1).equals(MorseCodeCipher.SHORT_GAP);
    }

    private void deleteSpaceOnRight() {
        String currentText = TextBoxes.initAndGetUpperBox(getActivity()).getText().toString();
        int selectionStart = TextBoxes.initAndGetUpperBox(getActivity()).getSelectionStart();
        currentText = currentText.substring(0, selectionStart) + currentText.substring(selectionStart+1);

        TextBoxes.initAndGetUpperBox(getActivity()).setText(currentText);
        TextBoxes.initAndGetUpperBox(getActivity()).setSelection(selectionStart);
    }

    private void insertLongSpace() {
        String currentText = TextBoxes.initAndGetUpperBox(getActivity()).getText().toString();
        int selectionStart = TextBoxes.initAndGetUpperBox(getActivity()).getSelectionStart();
        int selectionEnd = TextBoxes.initAndGetUpperBox(getActivity()).getSelectionEnd();

        currentText =
                currentText.substring(0, selectionStart)
                        + MorseCodeCipher.getInstance().convertToMorse(' ')
                        + currentText.substring(selectionEnd);
        TextBoxes.initAndGetUpperBox(getActivity()).setText(currentText);
        TextBoxes.initAndGetUpperBox(getActivity()).setSelection(selectionStart + MorseCodeCipher.getInstance().convertToMorse(' ').length());
    }

    private void insertShortSpace() {
        String currentText = TextBoxes.initAndGetUpperBox(getActivity()).getText().toString();
        int selectionStart = TextBoxes.initAndGetUpperBox(getActivity()).getSelectionStart();
        int selectionEnd = TextBoxes.initAndGetUpperBox(getActivity()).getSelectionEnd();

        currentText =
                currentText.substring(0, selectionStart)
                        + MorseCodeCipher.SHORT_GAP
                        + currentText.substring(selectionEnd);
        TextBoxes.initAndGetUpperBox(getActivity()).setText(currentText);
        TextBoxes.initAndGetUpperBox(getActivity()).setSelection(selectionStart + 1);
    }

    private Activity getActivity(){
        return activity;
    }

    public static void setNull() {
        instance = null;
    }
}
