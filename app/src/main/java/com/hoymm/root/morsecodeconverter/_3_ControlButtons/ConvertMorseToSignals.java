package com.hoymm.root.morsecodeconverter._3_ControlButtons;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
    private int morseCharStart = -1, morseCharEnd = -1, textCharStart = -1, textCharEnd = -1;
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
        if (PlayButton.initAndGetInstance(getActivity()).isActive()) {
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

    public static void saveIndexesOfCurrentlyBroadcastingTextToSP(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt(getStartTextIndexSPKey(activity), ConvertMorseToSignals.initAndGetInstance(activity).getStartBroadcastingTextIndex());
        editor.putInt(getEndTextIndexSPKey(activity), ConvertMorseToSignals.initAndGetInstance(activity).getEndBroadcastingTextIndex());
        editor.putInt(getStartMorseIndexSPKey(activity), ConvertMorseToSignals.initAndGetInstance(activity).getStartBroadcastingMorseIndex());
        editor.putInt(getEndMorseIndexSPKey(activity), ConvertMorseToSignals.initAndGetInstance(activity).getEndBroadcastingMorseIndex());

        editor.apply();
    }

    private static String getStartTextIndexSPKey(Activity activity) {
        return activity.getBaseContext().getString(R.string.textStartIndexSP);
    }

    private static String getEndTextIndexSPKey(Activity activity) {
        return activity.getBaseContext().getString(R.string.textEndIndexSP);
    }

    private static String getStartMorseIndexSPKey(Activity activity) {
        return activity.getBaseContext().getString(R.string.morseStartIndexSP);
    }

    private static String getEndMorseIndexSPKey(Activity activity) {
        return activity.getBaseContext().getString(R.string.morseEndIndexSP);
    }

    public static void restoreIndexesOfCurBroadcastTextOrSetToDefaultIfNotStoredSP(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        if (ifDataAboutIndexesAreStored(activity, sharedPref))
            restoreIndexesOfCurBroadcastText(activity, sharedPref);
        else
            ConvertMorseToSignals.initAndGetInstance(activity).setBroadcastTextIndexToStart();
    }

    private static boolean ifDataAboutIndexesAreStored(Activity activity, SharedPreferences sharedPref) {
        return sharedPref.getInt(getStartTextIndexSPKey(activity), -1) != -1;
    }

    private static void restoreIndexesOfCurBroadcastText(Activity activity, SharedPreferences sharedPref) {
        ConvertMorseToSignals.initAndGetInstance(activity).textCharStart = (sharedPref.getInt(getStartTextIndexSPKey(activity), 0));
        ConvertMorseToSignals.initAndGetInstance(activity).textCharEnd = (sharedPref.getInt(getEndTextIndexSPKey(activity), 0));
        ConvertMorseToSignals.initAndGetInstance(activity).morseCharStart = (sharedPref.getInt(getStartMorseIndexSPKey(activity), 0));
        ConvertMorseToSignals.initAndGetInstance(activity).morseCharEnd = (sharedPref.getInt(getEndMorseIndexSPKey(activity), 0));
    }

    @Override
    public void setNull() {
        instance = null;
    }
}
