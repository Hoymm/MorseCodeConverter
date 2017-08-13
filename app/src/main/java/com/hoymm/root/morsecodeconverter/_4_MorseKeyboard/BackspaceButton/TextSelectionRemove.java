package com.hoymm.root.morsecodeconverter._4_MorseKeyboard.BackspaceButton;

import android.app.Activity;
import android.util.Log;

import com.hoymm.root.morsecodeconverter.MorseToTextConversionProg.MorseCodeCipher;
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
        this.currentText = TextBoxes.initAndGetUpperBox(getActivity()).getText().toString();
    }

    void removeTextAccordingToSelectionAndSetNewTextAndSetSelection() {
        String newText;
        newText = removeTextAccordingToSelection();
        TextBoxes.initAndGetUpperBox(getActivity()).setText(newText);
        TextBoxes.initAndGetUpperBox(getActivity()).setSelection(newSelectionIndex);
    }

    private String removeTextAccordingToSelection() {
        int textSelectionStart = newSelectionIndex = TextBoxes.initAndGetUpperBox(getActivity()).getSelectionStart();
        if (isAnyTextSelected())
            return getTextWithSelectionCuttedOff();
        else
            return removeNonSelectedText(textSelectionStart);
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

    private String removeNonSelectedText(int textSelectionStart) {
        if (isMediumGapToDelete())
            newSelectionIndex = getSelectionForRemoveMediumGap(textSelectionStart);
        else
            newSelectionIndex = getSelectionForRemoveSingleCharOrNothing(textSelectionStart);
        if (ifTheresShortGapOnTheRightOfSelectionAndWillBeGapOnTheLeftAfterDelete())
            textSelectionStart += MorseCodeCipher.SHORT_GAP.length();
        return removeSubstringFromText(newSelectionIndex, textSelectionStart);
    }

    private boolean isMediumGapToDelete() {
        int selectionStart = TextBoxes.initAndGetUpperBox(getActivity()).getSelectionStart();
        int mediumGapLength = MorseCodeCipher.MEDIUM_GAP.length();

        if (selectionStart >= mediumGapLength)
            return currentText.substring(selectionStart - mediumGapLength, selectionStart).equals(MorseCodeCipher.MEDIUM_GAP);

        return false;
    }

    private int getSelectionForRemoveMediumGap(int textSelectionStart) {
        return textSelectionStart - MorseCodeCipher.MEDIUM_GAP.length();
    }

    private int getSelectionForRemoveSingleCharOrNothing(int textSelectionStart) {
        return textSelectionStart == 0 ? 0 : TextBoxes.initAndGetUpperBox(getActivity()).getSelectionStart() - 1;
    }

    private boolean ifTheresShortGapOnTheRightOfSelectionAndWillBeGapOnTheLeftAfterDelete() {
            return ifShortGapToTheRightOfSelection() && ifThereWillBeGapAfterDeleteToTheLeftOfSelection();
    }

    private boolean ifShortGapToTheRightOfSelection() {
        if (canThereFitMediumGap()) {
            if (isThereMediumGap())
                return false;
            return isThereShortGap();
        }
        return false;
    }

    private boolean canThereFitMediumGap() {
        int selectionEnd = TextBoxes.initAndGetUpperBox(getActivity()).getSelectionEnd();
        return currentText.length() >= selectionEnd + MorseCodeCipher.MEDIUM_GAP.length();
    }

    private boolean isThereMediumGap() {
        int selectionEnd = TextBoxes.initAndGetUpperBox(getActivity()).getSelectionEnd();
        return currentText.substring(selectionEnd, selectionEnd + MorseCodeCipher.MEDIUM_GAP.length()).equals(MorseCodeCipher.MEDIUM_GAP);
    }

    private boolean isThereShortGap() {
        int selectionEnd = TextBoxes.initAndGetUpperBox(getActivity()).getSelectionEnd();
        return currentText.substring(selectionEnd, selectionEnd + MorseCodeCipher.SHORT_GAP.length()).equals(MorseCodeCipher.SHORT_GAP);
    }

    private boolean ifThereWillBeGapAfterDeleteToTheLeftOfSelection() {
        return ifAfterDeletionThereWillBeAnyCharToTheLeft() && ifThatCharIsGointToBeShortGap();
    }

    private boolean ifAfterDeletionThereWillBeAnyCharToTheLeft() {
        int selectionStart = TextBoxes.initAndGetUpperBox(getActivity()).getSelectionStart();
        int shortGapLength = MorseCodeCipher.SHORT_GAP.length();
        return selectionStart-1-shortGapLength>=0;
    }

    private boolean ifThatCharIsGointToBeShortGap() {
        int selectionStart = TextBoxes.initAndGetUpperBox(getActivity()).getSelectionStart();
        int shortGapLength = MorseCodeCipher.SHORT_GAP.length();
        return currentText.substring(selectionStart-1-shortGapLength, selectionStart-1).equals(MorseCodeCipher.SHORT_GAP);
    }

    private String getTextToTheLeftOfSelection() {
        return currentText.substring(0, TextBoxes.initAndGetUpperBox(getActivity()).getSelectionStart());
    }

    private String getTextToTheRightOfSelection() {
        return currentText.substring(TextBoxes.initAndGetUpperBox(getActivity()).getSelectionEnd());
    }

    private String removeSubstringFromText(int startIndex, int endIndex) {
        String result = "";
        if (startIndex > 0)
            result = currentText.substring(0, startIndex);
        return result + currentText.substring(endIndex);
    }

    private Activity getActivity(){
        return activity;
    }
}
