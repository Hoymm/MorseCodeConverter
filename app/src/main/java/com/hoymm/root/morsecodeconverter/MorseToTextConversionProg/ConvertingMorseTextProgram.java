package com.hoymm.root.morsecodeconverter.MorseToTextConversionProg;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextArrowsSwap;
import com.hoymm.root.morsecodeconverter._2_TextBoxes.TextBoxes;
import com.hoymm.root.morsecodeconverter._3_ControlButtons.PlayButton;
import com.hoymm.root.morsecodeconverter._3_ControlButtons.StopButton;

/**
 * File created by Damian Muca - Kaizen on 27.06.17.
 */

public class ConvertingMorseTextProgram {
    private static ConvertingMorseTextProgram instance;
    private Activity activity;
    private boolean skipAddingShortGap = false;
    private boolean isConversionEnabled = false;

    public static ConvertingMorseTextProgram initAndGetInstance(Activity activity){
        if (instance == null)
            instance = new ConvertingMorseTextProgram(activity);
        return instance;
    }

    private ConvertingMorseTextProgram(Activity activity) {
        this.activity = activity;
        enableDynamicTextConversionIfStopButtonActive();
        TextBoxes.initAndGetUpperBox(getActivity()).addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (isConversionEnabled)
                            ifNoCurrentlyRunningStartNewConversionInASeparateThread();
                    }
                });
    }

    private void ifNoCurrentlyRunningStartNewConversionInASeparateThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String text = String.valueOf(TextBoxes.initAndGetUpperBox(getActivity()).getText());
                insertToBottomBoxIfPlayButtonInactive(convertToDestination(text));
            }
        }).start();
    }

    private void ifConditionMetTranslateStringAndInsertToBottomBox() {
    }

    public void enableDynamicTextConversionIfStopButtonActive() {
        isConversionEnabled = StopButton.initAndGetInstance(getActivity()).isActive();
    }

    public void disableConversion() {
        isConversionEnabled = false;
    }

    @NonNull
    private String convertToDestination(String text) {
        return MorseToTextArrowsSwap.isConvertingTextToMorse ? toMorse(text) : toText(text);
    }

    private void insertToBottomBoxIfPlayButtonInactive(final String text) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!PlayButton.initAndGetInstance(getActivity()).isActive())
                    TextBoxes.initAndGetBottomBox(getActivity()).setText(text);
            }
        });
    }

    @NonNull
    private String toMorse(String text) {
        text = text.toUpperCase();
        String morse = "";
        int textLength =  text.length();
        for (int i = 0; i < textLength; ++i) {
            char charToTranslate = text.charAt(i);

            morse = addShortGapAtTheEndIfConditionsAreMet(morse);
            String charToAddToMorse = MorseCodeCipher.getInstance().convertToMorse(charToTranslate);
            if (charToAddToMorse.equals(MorseCodeCipher.MEDIUM_GAP))
                morse = removeShortGapFromTheEndOfTextIfItAppears(morse);
            ifCharToAddToMorseIsMediumGapThenSkipShortGapInNextRound(charToAddToMorse);
            morse += charToAddToMorse;
        }
        return morse;
    }

    private String addShortGapAtTheEndIfConditionsAreMet(String morse) {
        if (isNotTextEmpty(morse) && spaceIsNOTCurrentlyAtTheEnd(morse) && !skipAddingShortGap)
            morse += MorseCodeCipher.SHORT_GAP;
        return morse;
    }

    private boolean isNotTextEmpty(String morse) {
        return morse.length() != 0;
    }

    private boolean spaceIsNOTCurrentlyAtTheEnd(String morse) {
        return !morse.substring(morse.length()-1).equals(MorseCodeCipher.SHORT_GAP);
    }

    private void ifCharToAddToMorseIsMediumGapThenSkipShortGapInNextRound(String charToAddToMorse) {
        skipAddingShortGap = charToAddToMorse.equals(MorseCodeCipher.MEDIUM_GAP);
    }

    private String toText(String morse) {
        if (morse.length() > 0)
            return convertMorseCodeToText(morse);
        else
            return "";
    }

    @NonNull
    private String convertMorseCodeToText(String morse) {
        String textResult = "";
        String [] morseWords = morse.split(MorseCodeCipher.MEDIUM_GAP);
        for (String word : morseWords) {
            if (word.length() != 0)
                textResult += translateSingleWordMorseToText(word);
            textResult += MorseCodeCipher.SHORT_GAP;
        }
        return removeShortGapFromTheEndOfTextIfShortOneGapAtTheEnd(textResult);
    }

    private String translateSingleWordMorseToText(String morseWord) {
        String textWord = "";
        String [] morseLetter = morseWord.split(MorseCodeCipher.SHORT_GAP);
        for (String morseChar : morseLetter)
            textWord += MorseCodeCipher.getInstance().convertToText(morseChar);
        return textWord;
    }

    private String removeShortGapFromTheEndOfTextIfShortOneGapAtTheEnd(String textResult) {
        if (textResult.length() > MorseCodeCipher.MEDIUM_GAP.length()
                && textResult.substring(textResult.length()-MorseCodeCipher.MEDIUM_GAP.length()).equals(MorseCodeCipher.MEDIUM_GAP))
            return textResult;
        else
            return removeShortGapFromTheEndOfTextIfItAppears(textResult);
    }

    @NonNull
    private String removeShortGapFromTheEndOfTextIfItAppears(String textResult) {
        if (isMediumGapAtTheEnd(textResult))
            return textResult;
        if (isShortGapAtTheEnd(textResult))
            return textResult.substring(0, textResult.length()-1);
        return textResult;
    }

    private boolean isMediumGapAtTheEnd(String textResult) {
        return textResult.length() >= MorseCodeCipher.MEDIUM_GAP.length()
                && textResult.substring(textResult.length() - MorseCodeCipher.MEDIUM_GAP.length()).equals(MorseCodeCipher.MEDIUM_GAP);
    }

    private boolean isShortGapAtTheEnd(String textResult) {
        return textResult.length() >= MorseCodeCipher.SHORT_GAP.length()
                && textResult.substring(textResult.length() - MorseCodeCipher.SHORT_GAP.length()).equals(MorseCodeCipher.SHORT_GAP);
    }

    private Activity getActivity(){
        return activity;
    }

    public static void setNull() {
        instance = null;
    }
}
