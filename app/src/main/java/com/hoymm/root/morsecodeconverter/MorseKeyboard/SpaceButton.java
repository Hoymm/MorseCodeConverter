package com.hoymm.root.morsecodeconverter.MorseKeyboard;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter.MorseToTextConversion.MorseCodeCipher;
import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 27.06.17.
 */

public class SpaceButton extends ImageButton {
    private EditText upperTextBox;
    private Activity activity;

    public SpaceButton(Context context) {
        super(context);
    }

    public SpaceButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializateObjects((Activity) context);
    }

    public SpaceButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializateObjects((Activity) context);
    }

    private void initializateObjects(Activity context) {
        activity = context;
        initXMLObjects();
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAction();
            }
        });
    }

    private void initXMLObjects() {
        if(upperTextBox == null)
            upperTextBox = (EditText) getActivity().findViewById(R.id.upper_edit_text_box);
    }

     private void onClickAction(){
        removeSelectedTextIfAnythingSelected();
        insertShortOrLongSpace();
    }

    private void removeSelectedTextIfAnythingSelected() {
        initXMLObjects();

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
            Log.e("Delete space", " LEFT");
            deleteSpaceOnLeft();
            howManySpacesDeleted++;
        }

        while(ifSpaceIsOnTheRightSelectionSide()){
            deleteSpaceOnRight();
            Log.e("Delete space", " RIGHT");
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
                        + MorseCodeCipher.getInstance().getMorse(' ')
                        + MorseCodeCipher.getCharSeparator()
                        + MorseCodeCipher.getCharSeparator()
                        + currentText.substring(selectionEnd);
        upperTextBox.setText(currentText);
        upperTextBox.setSelection(selectionStart + MorseCodeCipher.getInstance().getMorse(' ').length() + 2);
    }

    private void insertShortSpace() {
        String currentText = upperTextBox.getText().toString();
        int selectionStart = upperTextBox.getSelectionStart();
        int selectionEnd = upperTextBox.getSelectionEnd();

        currentText =
                currentText.substring(0, selectionStart)
                        + MorseCodeCipher.getCharSeparator()
                        + currentText.substring(selectionEnd);
        upperTextBox.setText(currentText);
        upperTextBox.setSelection(selectionStart + 1);
    }

    private Activity getActivity(){
        return activity;
    }
}
