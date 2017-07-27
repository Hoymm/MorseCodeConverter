package com.hoymm.root.morsecodeconverter.MorseToTextConversionProg;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.hoymm.root.morsecodeconverter._2_TextBoxes.ResizingTextBoxesAnimation;
import com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextArrowsSwap;
import com.hoymm.root.morsecodeconverter._2_TextBoxes.TextBoxes;

/**
 * File created by Damian Muca - Kaizen on 27.06.17.
 */

public class ConvertingMorseTextProgram {
    private Activity activity;
    private boolean skipAddingShortGap = false;

    public ConvertingMorseTextProgram(Context context) {
        this.activity = (Activity)context;
        enableConversion();
    }

    public void disableTranslationTemporaryForAnimationTime(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                disableConversion();
                try {
                    Thread.sleep(ResizingTextBoxesAnimation.animationTime + 50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                enableConversion();
            }
        }).start();
    }

    private void enableConversion() {
        TextBoxes.initAndGetUpperBox(getActivity()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                translateStringAndInsertToBottomBox();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void disableConversion() {
        TextBoxes.initAndGetUpperBox(getActivity())
                .addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void translateStringAndInsertToBottomBox() {
        final String text = String.valueOf(TextBoxes.initAndGetUpperBox(getActivity()).getText());
        translateAndInsertToBottomBox(text);
    }

    private void translateAndInsertToBottomBox(final String text) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextBoxes.initAndGetBottomBox(getActivity()).setText(MorseToTextArrowsSwap.isConvertingTextToMorse ? toMorse(text) : toText(text));
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
        for (String morseChar : morseLetter) {
            textWord += MorseCodeCipher.getInstance().convertToText(morseChar);
        }
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
}
