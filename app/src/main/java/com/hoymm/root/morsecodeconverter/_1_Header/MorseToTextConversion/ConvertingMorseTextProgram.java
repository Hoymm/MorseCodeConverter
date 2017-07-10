package com.hoymm.root.morsecodeconverter._1_Header.MorseToTextConversion;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.hoymm.root.morsecodeconverter._2_ApplicationBody.ResizingTextBoxesAnimation;
import com.hoymm.root.morsecodeconverter._1_Header.MorseToTextSwappingPanelConversion;
import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 27.06.17.
 */

public class ConvertingMorseTextProgram {
    private Activity activity;
    private TextView bottomBox;
    private EditText upperBox;

    public ConvertingMorseTextProgram(Context context) {
        this.activity = (Activity)context;
        initXMLObjects();
    }

    private void initXMLObjects() {
        upperBox = (EditText) getActivity().findViewById(R.id.upper_edit_text_box);
        bottomBox = (TextView) getActivity().findViewById(R.id.bottom_text_view_box);
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
        upperBox.addTextChangedListener(new TextWatcher() {
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
        upperBox.addTextChangedListener(new TextWatcher() {
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
        final String text = String.valueOf(upperBox.getText());
        translateAndInsertToBottomBox(text);
    }

    private void translateAndInsertToBottomBox(final String text) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bottomBox.setText(MorseToTextSwappingPanelConversion.isConvertingTextToMorse ? toMorse(text) : toText(text));
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
            morse += MorseCodeCipher.getInstance().convertToMorse(charToTranslate) + " ";
        }
        return removeSpaceFromTheEndOfText(morse);
    }

    @NonNull
    private String removeCharOnTheLeft(String text) {
        return text.substring(1);
    }

    private char getCharFromTheLeft(String text) {
        return text.charAt(0);
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
            textResult += translateSingleWord_MorseToText(word) + MorseCodeCipher.getCharSeparator();
        return removeSpaceFromTheEndOfText(textResult);
    }

    @NonNull
    private String[] splitTextToArrayOfWords(String morse) {
        return morse.split(MorseCodeCipher.getInstance().convertToMorse(' ')
                + MorseCodeCipher.getCharSeparator()
                + MorseCodeCipher.getCharSeparator());
    }

    private String translateSingleWord_MorseToText(String morseWord) {
        String textWord = "";
        String [] morseLetter = morseWord.split(MorseCodeCipher.getCharSeparator());
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
