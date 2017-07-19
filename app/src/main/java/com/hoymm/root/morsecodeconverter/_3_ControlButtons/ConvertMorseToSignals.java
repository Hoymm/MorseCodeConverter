package com.hoymm.root.morsecodeconverter._3_ControlButtons;

import android.app.Activity;
import com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextConversion.MorseCodeCipher;
import com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextSwappingPanelConversion;
import com.hoymm.root.morsecodeconverter._2_TextBoxes.TextBoxes;

/**
 * File created by Damian Muca - Kaizen on 11.07.17.
 */

class ConvertMorseToSignals {
    private static ConvertMorseToSignals instance = null;
    private String textToBroadcast = "";
    private Activity activity;

    public static ConvertMorseToSignals initAndGetInstance(Activity activity){
        if (instance == null)
            instance = new ConvertMorseToSignals(activity);
        return instance;
    }

    private ConvertMorseToSignals(Activity activity) {
        this.activity = activity;
    }

    void refreshTextToBroadcast() {
        if (MorseToTextSwappingPanelConversion.isConvertingTextToMorse)
            textToBroadcast =
                    TextBoxes.initAndGetBottomBox(getActivity()).getText().toString();
        else
            textToBroadcast =
                    TextBoxes.initAndGetUpperBox(getActivity()).getText().toString();
    }

    boolean isThereStillTextToBroadcast(){
        return textToBroadcast.length() > 0;
    }

    String getNextMorseSignToBroadcast(){
        int firstCharEndIndex = 0;
        while (textToBroadcast.length() > firstCharEndIndex
                && textToBroadcast.substring(firstCharEndIndex, 1).equals(MorseCodeCipher.SHORT_GAP))
            firstCharEndIndex++;

        if (firstCharEndIndex > 1)
            return MorseCodeCipher.MEDIUM_GAP;
        else
            return textToBroadcast.substring(0,1);
    }

    void removeNextCharFromBroadcast(){
        try{
            if (!isNextCharInBroadcastSpace())
                removeNextCharFromText();
            else
                removeAllNextGapsFromText();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isNextCharInBroadcastSpace() {
        return textToBroadcast.substring(0,1).equals(MorseCodeCipher.SHORT_GAP);
    }

    private void removeNextCharFromText(){
        textToBroadcast = textToBroadcast.substring(1);
    }

    private void removeAllNextGapsFromText() {
        while (textToBroadcast.length() > 0 &&
                textToBroadcast.substring(0, 1).equals(MorseCodeCipher.SHORT_GAP))
            textToBroadcast = textToBroadcast.substring(1);
    }

    private Activity getActivity(){
        return activity;
    }
}
