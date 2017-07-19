package com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextConversion;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.hoymm.root.morsecodeconverter._2_TextBoxes.ResizingTextBoxesAnimation;
import com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextSwappingPanelConversion;
import com.hoymm.root.morsecodeconverter._2_TextBoxes.TextBoxes;

/**
 * File created by Damian Muca - Kaizen on 27.06.17.
 */

public class ConvertingMorseTextProgram {
    private Activity activity;

    public ConvertingMorseTextProgram(Context context) {
        this.activity = (Activity)context;
        enableConversion();
    }

    public void disableTranslationTemporaryForAnimationTime(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("Code Translation", "temporary disabled");
                disableConversion();
                try {
                    Thread.sleep(ResizingTextBoxesAnimation.animationTime + 50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i("Code Translation", "enabled");
                enableConversion();
            }
        }).start();
    }

    private void enableConversion() {
        TextBoxes.initAndGetUpperBox(getActivity())
                .addTextChangedListener(new TextWatcher() {
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
        final String text =
                String.valueOf(TextBoxes.initAndGetUpperBox(getActivity()).getText());
        translateAndInsertToBottomBox(text);
    }

    private void translateAndInsertToBottomBox(final String text) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextBoxes.initAndGetBottomBox(getActivity())
                        .setText(MorseToTextSwappingPanelConversion.isConvertingTextToMorse ? toMorse(text) : toText(text));
            }
        });
    }

    @NonNull
    private String toMorse(String text) {
        text = text.toUpperCase();
        String morse = "";
        int textLength =  text.length();
        for (int i = 0; i < textLength; ++i) {
            char charToTranslate = getCharFromTheLeft(text);
            text = removeCharOnTheLeft(text);


            if (notTheFirstCharConvertingCurrently(morse)
                    && !lastCharIsAGap(MorseCodeCipher.getInstance().convertToMorse(charToTranslate))
                    && !lastCharIsAGap(morse)
                    )
                morse += " ";
            morse += MorseCodeCipher.getInstance().convertToMorse(charToTranslate);
        }
        return morse;
    }

    @NonNull
    private String removeCharOnTheLeft(String text) {
        return text.substring(1);
    }

    private char getCharFromTheLeft(String text) {
        return text.charAt(0);
    }

    private boolean lastCharIsAGap(String morse) {
        return morse.substring(morse.length() - 1, morse.length()).equals(MorseCodeCipher.SHORT_GAP);
    }

    private boolean notTheFirstCharConvertingCurrently(String morse) {
        return morse.length()>1;
    }

    private String toText(String morse) {
        morse = removeSpacesFromBothSides(morse);
        if (morse.length() > 0)
            return convertMorseCodeToText(morse);
        else
            return "";
    }

    private String removeSpacesFromBothSides(String morse) {
        morse = removeSpacesOnTheRight(morse);
        morse = removeSpacesOnTheLeft(morse);
        return morse;
    }

    @NonNull
    private String removeSpacesOnTheRight(String morse) {
        while (morse.length() > 0 && morse.substring(morse.length()-1).equals(" "))
            morse = morse.substring(0, morse.length() - 1);
        return morse;
    }

    @NonNull
    private String removeSpacesOnTheLeft(String morse) {
        while (morse.length() > 0 && morse.substring(0, 1).equals(" "))
            morse = morse.substring(1);
        return morse;
    }

    @NonNull
    private String convertMorseCodeToText(String morse) {
        String textResult = "";
        String [] morseWords = splitTextToArrayOfWords(morse);
        for (String word : morseWords)
            textResult += translateSingleWord_MorseToText(word) + MorseCodeCipher.SHORT_GAP;
        return removeSpaceFromTheEndOfText(textResult);
    }

    @NonNull
    private String[] splitTextToArrayOfWords(String morse) {
        return morse.split(MorseCodeCipher.MEDIUM_GAP);
    }

    private String translateSingleWord_MorseToText(String morseWord) {
        String textWord = "";
        String [] morseLetter = morseWord.split(MorseCodeCipher.SHORT_GAP);
        for (String morseChar : morseLetter) {
            textWord += MorseCodeCipher.getInstance().convertToText(morseChar);
        }
        return textWord;
    }

    @NonNull
    private String removeSpaceFromTheEndOfText(String textResult) {
        if (textResult.length() > 0 && textResult.substring(textResult.length()-1).equals(" "))
            return textResult.substring(0, textResult.length()-1);
        return textResult;
    }

    private Activity getActivity(){
        return activity;
    }
}
