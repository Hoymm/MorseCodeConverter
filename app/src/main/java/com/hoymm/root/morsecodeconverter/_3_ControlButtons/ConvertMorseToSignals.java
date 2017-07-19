package com.hoymm.root.morsecodeconverter._3_ControlButtons;

import android.app.Activity;
import android.util.Log;

import com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextConversion.MorseCodeCipher;
import com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextArrowsSwap;
import com.hoymm.root.morsecodeconverter._2_TextBoxes.TextBoxes;

/**
 * File created by Damian Muca - Kaizen on 11.07.17.
 */

class ConvertMorseToSignals {
    private static ConvertMorseToSignals instance = null;
    private int charIndexToBroadcast = -1;
    private Activity activity;

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
        charIndexToBroadcast = 0;
    }

    boolean isThereStillTextLeftToBroadcast() {
        return charIndexToBroadcast < getMorseWholeText().length();
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
        charIndexToBroadcast += ifNoGapsThenReturnOneMeansThereIsAnOrdinaryChar(getHowManyGapsNextMorseCharacterHas());
    }

    private int ifNoGapsThenReturnOneMeansThereIsAnOrdinaryChar(int howManyGaps) {
        return howManyGaps > 0 ? howManyGaps : 1;
    }

    String getMorseSignToBeBroadcasted() {

        int howManyCharacters = getHowManyGapsNextMorseCharacterHas();

        if (howManyCharacters > 1)
            return MorseCodeCipher.MEDIUM_GAP;
        else
            return getMorseWholeText().substring(charIndexToBroadcast, charIndexToBroadcast+1);
    }

    private int getHowManyGapsNextMorseCharacterHas() {
        int howManyGapCharacters = 0;
        int leftTextToBroadcastLength = getMorseWholeText().length() - charIndexToBroadcast;
        while (leftTextToBroadcastLength > howManyGapCharacters
                && getMorseWholeText().substring(howManyGapCharacters, 1).equals(MorseCodeCipher.SHORT_GAP))
            howManyGapCharacters++;
        return howManyGapCharacters;
    }
}
