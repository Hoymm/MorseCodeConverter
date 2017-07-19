package com.hoymm.root.morsecodeconverter._4_MorseKeyboard.BackspaceButton;

import android.app.Activity;
import com.hoymm.root.morsecodeconverter._2_TextBoxes.TextBoxes;

/**
 * File created by Damian Muca - Kaizen on 17.07.17.
 */

class TextSelectionRemove {
    private Activity activity;
    private String currentText;
    private int newSelectionIndex;

    TextSelectionRemove(Activity activity) {
        this.activity = activity;
        this.currentText =
                TextBoxes.initAndGetUpperBox(getActivity()).getText().toString();
    }

    void removeTextAccordingToSelectionAndSetNewTextAndSetSelection() {
        String newText;
        newText = removeTextAccordingToSelection();
        TextBoxes.initAndGetUpperBox(getActivity()).setText(newText);
        TextBoxes.initAndGetUpperBox(getActivity()).setSelection(newSelectionIndex);
    }

    private String removeTextAccordingToSelection() {
        if (isAnyTextSelected()) {
            newSelectionIndex =
                    TextBoxes.initAndGetUpperBox(getActivity()).getSelectionStart();
            return getTextWithSelectionCuttedOff();
        }
        else {
            newSelectionIndex =
                    TextBoxes.initAndGetUpperBox(getActivity()).getSelectionStart() == 0
                    ? 0 : TextBoxes.initAndGetUpperBox(getActivity()).getSelectionStart()-1;
            return getTextWithCharToTheLeftOfSelectionDeleted();
        }
    }

    private boolean isAnyTextSelected() {
        return TextBoxes.initAndGetUpperBox(getActivity()).getSelectionEnd() != TextBoxes.initAndGetUpperBox(getActivity()).getSelectionStart();
    }

    private String getTextWithSelectionCuttedOff() {
        String result;
        result = getTextToTheLeftOfSelection();
        result += getTextToTheRightOfSelection();
        return result;
    }

    private String getTextToTheLeftOfSelection() {
        return currentText.substring(0, TextBoxes.initAndGetUpperBox(getActivity()).getSelectionStart());
    }

    private String getTextToTheRightOfSelection() {
        return currentText.substring(TextBoxes.initAndGetUpperBox(getActivity()).getSelectionEnd());
    }

    private String getTextWithCharToTheLeftOfSelectionDeleted() {
        String result = "";
        if (TextBoxes.initAndGetUpperBox(getActivity()).getSelectionStart() != 0)
            result = currentText.substring(0, TextBoxes.initAndGetUpperBox(getActivity()).getSelectionStart() - 1);
        return result + currentText.substring(TextBoxes.initAndGetUpperBox(getActivity()).getSelectionStart());
    }

    private Activity getActivity(){
        return activity;
    }
}
