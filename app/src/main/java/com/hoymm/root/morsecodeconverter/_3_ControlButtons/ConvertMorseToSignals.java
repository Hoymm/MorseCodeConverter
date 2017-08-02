package com.hoymm.root.morsecodeconverter._3_ControlButtons;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.hoymm.root.morsecodeconverter.MainActivity;
import com.hoymm.root.morsecodeconverter.R;
import com.hoymm.root.morsecodeconverter.Singleton;
import com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextArrowsSwap;
import com.hoymm.root.morsecodeconverter.MorseToTextConversionProg.MorseCodeCipher;
import com.hoymm.root.morsecodeconverter._2_TextBoxes.TextBoxes;

import static com.hoymm.root.morsecodeconverter.MorseToTextConversionProg.MorseCodeCipher.MEDIUM_GAP;
import static com.hoymm.root.morsecodeconverter.MorseToTextConversionProg.MorseCodeCipher.SHORT_GAP;

/**
 * File created by Damian Muca - Kaizen on 11.07.17.
 */

public class ConvertMorseToSignals implements Singleton {
    private static ConvertMorseToSignals instance = null;
    int morseCharStart = -1;
    int morseCharEnd = -1;
    int textCharStart = -1;
    int textCharEnd = -1;
    private Activity activity;
    private boolean lastTimeWasAMediumGap = false;

    public static ConvertMorseToSignals initAndGetInstance(Activity activity) {
        if (instance == null)
            instance = new ConvertMorseToSignals(activity);
        return instance;
    }

    private ConvertMorseToSignals(Activity activity) {
        this.activity = activity;
    }

    private Activity getActivity() {
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

        else if (isCurIndexIsAShortGap(morseCharStart, morseText)) {
            int howManyGaps = calculateHowManyGapsAreThereInFront(morseCharStart, morseText);
            howManyGaps = demandWhetherNowShortGapOrLongShouldBeBroadcasted(howManyGaps);
            return morseCharStart + howManyGaps;
        } else {
            return morseCharStart + 1;
        }
    }

    private boolean isCurIndexIsAShortGap(int morseCharStart, String morseText) {
        return morseText.substring(morseCharStart, morseCharStart + SHORT_GAP.length()).equals(SHORT_GAP);
    }

    private int demandWhetherNowShortGapOrLongShouldBeBroadcasted(int howManyGaps) {
        return howManyGaps > 1 ? MEDIUM_GAP.length() : 1;
    }

    private int calculateHowManyGapsAreThereInFront(int index, String morseText) {
        int howManyGaps = 0;
        while (morseText.length() > index && morseText.substring(index, index + 1).equals(MorseCodeCipher.SHORT_GAP)) {
            index++;
            howManyGaps++;
        }
        return howManyGaps;
    }

    boolean isThereStillTextLeftToBroadcast() {
        return morseCharStart < getMorseWholeText().length();
    }

    private String getMorseWholeText() {
        String morseText;
        if (MorseToTextArrowsSwap.isConvertingTextToMorse)
            morseText = TextBoxes.initAndGetBottomBox(getActivity()).getText().toString();
        else
            morseText = TextBoxes.initAndGetUpperBox(getActivity()).getText().toString();
        return morseText;
    }

    void moveBroadcastingPositionForwardIfPlayButtonActive() {
        if (!MainActivity.destroyed &&
                PlayButton.initAndGetInstance(getActivity()).isActive()) {
            moveMorse();
            moveTextIndexIfShortOrMediumGapOrIfMediumGapWasLastTime();
        }
    }

    private void moveMorse() {
        morseCharStart = morseCharEnd;
        morseCharEnd = calculateEndIndexOfMorseChar(morseCharStart);
    }

    private void moveTextIndexIfShortOrMediumGapOrIfMediumGapWasLastTime() {
        if (lastTimeWasAMediumGap || isShortOrMediumGapRightNow()) {
            moveTextIndex();
            lastTimeWasAMediumGap = isMediumGapRightNow();
        }

    }

    private boolean isShortOrMediumGapRightNow() {
        return isShortGapRightNow() || isMediumGapRightNow();
    }

    private boolean isShortGapRightNow() {
        return morseCharStart == morseCharEnd - SHORT_GAP.length() && isCurIndexIsAShortGap(morseCharStart, getMorseWholeText());
    }

    boolean isMediumGapRightNow() {
        return morseCharStart == morseCharEnd - MEDIUM_GAP.length() && isCurIndexIsAMediumGap(morseCharStart, getMorseWholeText());
    }

    private boolean isCurIndexIsAMediumGap(int morseCharStart, String morseText) {
        return morseText.substring(morseCharStart, morseCharStart + MEDIUM_GAP.length()).equals(MEDIUM_GAP);
    }

    private void moveTextIndex() {
        textCharStart++;
        textCharEnd = textCharStart + 1 > getTextWholeText().length() ? textCharStart : textCharStart + 1;
    }


    private String getTextWholeText() {
        String text;
        if (MorseToTextArrowsSwap.isConvertingTextToMorse)
            text = TextBoxes.initAndGetUpperBox(getActivity()).getText().toString();
        else
            text = TextBoxes.initAndGetBottomBox(getActivity()).getText().toString();
        return text;
    }

    public int getStartBroadcastingMorseIndex() {
        return morseCharStart;
    }

    int getEndBroadcastingMorseIndex() {
        return morseCharEnd;
    }

    int getStartBroadcastingTextIndex() {
        return textCharStart;
    }

    int getEndBroadcastingTextIndex() {
        return textCharEnd == 0 && getTextWholeText().length() > 0 ? 1 : textCharEnd;
    }

    String getMorseSignToBeBroadcasted() {
        if (morseCharEnd - morseCharStart > 1)
            return MEDIUM_GAP;
        else
            return getMorseWholeText().substring(morseCharStart, morseCharEnd);

    }

    @Override
    public void setNull() {
        instance = null;
    }
}
