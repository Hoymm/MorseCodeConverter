package com.hoymm.root.morsecodeconverter._4_MorseKeyboard.BackspaceButton;

import android.app.Activity;
import android.widget.EditText;

import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 17.07.17.
 */

class TextSelectionRemove {
    private final EditText upperTextBox;
    private Activity activity;
    private String currentText;
    private int newSelectionIndex;

    TextSelectionRemove(Activity activity) {
        this.activity = activity;
        upperTextBox = (EditText) getActivity().findViewById(R.id.upper_edit_text_box);
        this.currentText = upperTextBox.getText().toString();
    }

    void removeTextAccordingToSelectionAndSetNewTextAndSetSelection() {
        String newText;
        newText = removeTextAccordingToSelection();
        upperTextBox.setText(newText);
        upperTextBox.setSelection(newSelectionIndex);
    }

    private String removeTextAccordingToSelection() {
        if (isAnyTextSelected()) {
            newSelectionIndex = upperTextBox.getSelectionStart();
            return getTextWithSelectionCuttedOff();
        }
        else {
            newSelectionIndex = upperTextBox.getSelectionStart() == 0
                    ? 0 : upperTextBox.getSelectionStart()-1;
            return getTextWithCharToTheLeftOfSelectionDeleted();
        }
    }

    private boolean isAnyTextSelected() {
        return upperTextBox.getSelectionEnd() != upperTextBox.getSelectionStart();
    }

    private String getTextWithSelectionCuttedOff() {
        String result;
        result = getTextToTheLeftOfSelection();
        result += getTextToTheRightOfSelection();
        return result;
    }

    private String getTextToTheLeftOfSelection() {
        return currentText.substring(0, upperTextBox.getSelectionStart());
    }

    private String getTextToTheRightOfSelection() {
        return currentText.substring(upperTextBox.getSelectionEnd());
    }

    private String getTextWithCharToTheLeftOfSelectionDeleted() {
        String result = "";
        if (upperTextBox.getSelectionStart() != 0)
            result = currentText.substring(0, upperTextBox.getSelectionStart() - 1);
        return result + currentText.substring(upperTextBox.getSelectionStart());
    }

    private Activity getActivity(){
        return activity;
    }
}
