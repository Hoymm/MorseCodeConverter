package com.hoymm.root.morsecodeconverter.MorseToTextConversion;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

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
    }

    public void translateToMorse_SetText() {
        final String text = String.valueOf(upperBox.getText());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bottomBox.setText(translateTextToMorse(text));
            }
        });
    }

    private String translateTextToMorse(String text) {
        if (text.length() > 0) {
            text = text.toUpperCase();
            String morse = "";
            int textLength =  text.length();
            for (int index = 0; index < textLength; ++index) {
                char currentCharToTranslate = text.charAt(0);
                text = text.substring(1);
                morse += MorseCodeCipher.getInstance().convertToMorse(currentCharToTranslate) + " ";
            }
            return removeSpaceFromTheEndOfText(morse);
        }
        else
            return "";
    }


    public void translateToText_SetText() {
        final String morse = String.valueOf(upperBox.getText());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bottomBox.setText(translateMorseToText(morse));
                translateMorseToText(morse);
            }
        });
    }

    private String translateMorseToText(String morse) {
        if (morse.length() > 0) {
            String textResult = "";
            String [] morseWords = splitTextToArrayOfWords(morse);
            for (String word : morseWords)
                textResult += translateSingleWord_MorseToText(word) + MorseCodeCipher.getCharSeparator();
            return removeSpaceFromTheEndOfText(textResult);
        }
        else
            return "";
    }

    @NonNull
    private String[] splitTextToArrayOfWords(String morse) {
        return morse.split(MorseCodeCipher.getInstance().convertToMorse(' ')
                + MorseCodeCipher.getCharSeparator()
                + MorseCodeCipher.getCharSeparator());
    }

    private String translateSingleWord_MorseToText(String morseWord) {
        String textWord = "";
        String [] morseCharacters = morseWord.split(MorseCodeCipher.getCharSeparator());
        for (String morseChar : morseCharacters) {
            textWord += MorseCodeCipher.getInstance().convertToText(morseChar);
        }
        return textWord;
    }

    @NonNull
    private String removeSpaceFromTheEndOfText(String textResult) {
        return textResult.substring(0, textResult.length()-1);
    }

    private Activity getActivity(){
        return activity;
    }
}
