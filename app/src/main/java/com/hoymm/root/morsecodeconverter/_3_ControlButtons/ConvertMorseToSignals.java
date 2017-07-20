package com.hoymm.root.morsecodeconverter._3_ControlButtons;

import android.app.Activity;

import com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextArrowsSwap;
import com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextConversion.MorseCodeCipher;
import com.hoymm.root.morsecodeconverter._2_TextBoxes.TextBoxes;

import static com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextConversion.MorseCodeCipher.MEDIUM_GAP;
import static com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextConversion.MorseCodeCipher.SHORT_GAP;

/**
 * File created by Damian Muca - Kaizen on 11.07.17.
 */

class ConvertMorseToSignals {
    private static ConvertMorseToSignals instance = null;
    private int morseCharStart = -1, morseCharEnd = -1, textCharStart = -1, textCharEnd = -1;
    private Activity activity;
    private boolean lastTimeWasShortGap = false;

    public static ConvertMorseToSignals initAndGetInstance(Activity activity){
        if (instance == null)
            instance = new ConvertMorseToSignals(activity);
        return instance;
    }

    private ConvertMorseToSignals(Activity activity) {
        this.activity = activity;
    }

    private Activity getActivity(){
        return activity;
    }

    void setBroadcastTextIndexToStart() {
        morseCharStart = 0;
        morseCharEnd = calculateEndIndexOfMorseChar(morseCharStart);
        textCharStart = 0;
        textCharEnd = getTextWholeText().length() > 0 ? 1 : 0;
    }

    private int calculateEndIndexOfMorseChar(int morseCharStart) {
        String morseText = getMorseWholeText();
        if (morseText.length() < morseCharStart + 1)
            return morseCharStart;
        else if (morseText.substring(morseCharStart, morseCharStart + 1).equals(SHORT_GAP)) {
            int howManyGaps = calculateHowManyGaps(morseCharStart, morseText);

            if (!lastTimeWasShortGap) {
                howManyGaps = 1;
                lastTimeWasShortGap = true;
            }
            else
                howManyGaps = howManyGaps > 1 ? MEDIUM_GAP.length() : 1;

            return morseCharStart + howManyGaps;
        } else {
            lastTimeWasShortGap = false;
            return morseCharStart + 1;
        }
    }

    private int calculateHowManyGaps(int index, String morseText) {
        int howManyGaps = 0;
        while (morseText.length() > index && morseText.substring(index, index+1).equals(MorseCodeCipher.SHORT_GAP)){
            index++;
            howManyGaps++;
        }
        return howManyGaps;
    }

    boolean isThereStillTextLeftToBroadcast() {
        return morseCharStart < getMorseWholeText().length();
    }

    private String getMorseWholeText(){
        String morseText;
        if (MorseToTextArrowsSwap.isConvertingTextToMorse)
            morseText = TextBoxes.initAndGetBottomBox(getActivity()).getText().toString();
        else
            morseText = TextBoxes.initAndGetUpperBox(getActivity()).getText().toString();
        return morseText;
    }

    void moveBroadcastingPositionForward() {
        moveMorse();
        moveTextIndexIfGap();
    }

    private void moveMorse() {
        morseCharStart = morseCharEnd;
        morseCharEnd = calculateEndIndexOfMorseChar(morseCharStart);
    }

    private void moveTextIndexIfGap() {
        if (isShortGapRightNow())
            moveTextIndex();
    }

    private boolean isShortGapRightNow() {
        return morseCharStart == morseCharEnd-1 && getMorseWholeText().substring(morseCharStart, morseCharStart + 1).equals(SHORT_GAP);
    }

    private void moveTextIndex() {
        textCharStart++;
        textCharEnd = textCharStart+1 > getTextWholeText().length() ? textCharStart : textCharStart+1;
    }


    private String getTextWholeText(){
        String text;
        if (MorseToTextArrowsSwap.isConvertingTextToMorse)
            text = TextBoxes.initAndGetUpperBox(getActivity()).getText().toString();
        else
            text = TextBoxes.initAndGetBottomBox(getActivity()).getText().toString();
        return text;
    }

    int getStartBroadcastingMorseIndex(){
        return morseCharStart;
    }

    int getEndBroadcastingMorseIndex(){
        return morseCharEnd;
    }

    int getStartBroadcastingTextIndex(){
        return textCharStart;
    }

    int getEndBroadcastingTextIndex(){
        return textCharEnd;
    }

    String getMorseSignToBeBroadcasted() {
        if (morseCharEnd - morseCharStart > 1)
            return MEDIUM_GAP;
        else
            return getMorseWholeText().substring(morseCharStart, morseCharEnd);
    }
}
