package com.hoymm.root.morsecodeconverter._4_MorseKeyboard;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextConversion.MorseCodeCipher;
import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 27.06.17.
 */

public class SpaceButton extends ImageButton {
    private static ImageButton instance = null;
    private EditText upperTextBox;
    private Activity activity;

    static ImageButton initAndGetInstance(Activity activity){
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
        upperTextBox = (EditText) getActivity().findViewById(R.id.upper_edit_text_box);
    }

    private void setBackspaceButtonBehavior() {
        instance.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAction();
            }
        });
    }

    private void onClickAction(){
        removeSelectedTextIfAnythingSelected();
        insertShortOrLongSpace();
    }

    private void removeSelectedTextIfAnythingSelected() {
        String originalText = upperTextBox.getText().toString();
        int selStart = upperTextBox.getSelectionStart();
        int selEnd = upperTextBox.getSelectionEnd();
        String textAfterRemovingSelectedText = originalText.substring(0, selStart) + originalText.substring(selEnd);

        upperTextBox.setText(textAfterRemovingSelectedText);
        upperTextBox.setSelection(selStart);
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
        String currentText = upperTextBox.getText().toString();
        int selectionStart = upperTextBox.getSelectionStart();
        return selectionStart > 0 && currentText.substring(selectionStart-1,selectionStart).equals(" ");
    }

    private void deleteSpaceOnLeft() {
        String currentText = upperTextBox.getText().toString();
        int selectionStart = upperTextBox.getSelectionStart();
        currentText = currentText.substring(0, selectionStart-1) + currentText.substring(selectionStart);

        upperTextBox.setText(currentText);
        upperTextBox.setSelection(selectionStart-1);
    }

    private boolean ifSpaceIsOnTheRightSelectionSide() {
        String currentText = upperTextBox.getText().toString();
        int selectionStart = upperTextBox.getSelectionStart();
        return currentText.length() > selectionStart && currentText.substring(selectionStart,selectionStart+1).equals(" ");
    }

    private void deleteSpaceOnRight() {
        String currentText = upperTextBox.getText().toString();
        int selectionStart = upperTextBox.getSelectionStart();
        currentText = currentText.substring(0, selectionStart) + currentText.substring(selectionStart+1);

        upperTextBox.setText(currentText);
        upperTextBox.setSelection(selectionStart);
    }

    private void insertLongSpace() {
        String currentText = upperTextBox.getText().toString();
        int selectionStart = upperTextBox.getSelectionStart();
        int selectionEnd = upperTextBox.getSelectionEnd();

        currentText =
                currentText.substring(0, selectionStart)
                        + MorseCodeCipher.getInstance().convertToMorse(' ')
                        + currentText.substring(selectionEnd);
        upperTextBox.setText(currentText);
        upperTextBox.setSelection(selectionStart + MorseCodeCipher.getInstance().convertToMorse(' ').length());
    }

    private void insertShortSpace() {
        String currentText = upperTextBox.getText().toString();
        int selectionStart = upperTextBox.getSelectionStart();
        int selectionEnd = upperTextBox.getSelectionEnd();

        currentText =
                currentText.substring(0, selectionStart)
                        + MorseCodeCipher.SHORT_GAP
                        + currentText.substring(selectionEnd);
        upperTextBox.setText(currentText);
        upperTextBox.setSelection(selectionStart + 1);
    }

    private Activity getActivity(){
        return activity;
    }

    public static void setNull() {
        instance = null;
    }
}
