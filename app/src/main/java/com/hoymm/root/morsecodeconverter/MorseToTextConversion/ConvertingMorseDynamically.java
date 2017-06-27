package com.hoymm.root.morsecodeconverter.MorseToTextConversion;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.hoymm.root.morsecodeconverter.MorseToTextSwappingPanel;
import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 26.06.17.
 */

public class ConvertingMorseDynamically implements Runnable {
    private boolean connvertingEnabled;
    private Thread thread;
    private Activity activity;
    private EditText upperBox;
    private TextView bottomBox;


    public ConvertingMorseDynamically(Context context) {
        activity = (Activity) context;
        initXMLObjects();
    }

    private void initXMLObjects() {
        upperBox = (EditText) getActivity().findViewById(R.id.upper_edit_text_box);
        bottomBox = (TextView) getActivity().findViewById(R.id.bottom_text_view_box);
    }

    @Override
    public void run() {
        while(connvertingEnabled) {
            try {
                Thread.sleep(700);
                if (MorseToTextSwappingPanel.isConvertingTextToMorse)
                    translateToMorse_SetText();
                else
                    translateToText_SetText();
            } catch (InterruptedException e) {
                Log.e("ConvertingMorseDyn", " thread stopped.");
            }
        }
    }

    private void translateToMorse_SetText() {
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
                morse += getMorseEquivalent(currentCharToTranslate) + " ";
            }
            return morse.substring(0, morse.length() - 1);
        }
        else
            return "";
    }

    private String getMorseEquivalent(char text) {
        String morseCode = MorseCodeCipher.getInstance().getMorse(text);
        return morseCode;
    }


    private void translateToText_SetText() {
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
            String [] morseWords = splitTextToArrrayOfWords(morse);
            for (int i = 0; i < morseWords.length; ++i) {
                textResult += translateSingleWord_MorseToText(morseWords[i]) + MorseCodeCipher.getCharSeparator();
            }
            return textResult.substring(0, textResult.length()-1);
        }
        else
            return "";
    }

    @NonNull
    private String[] splitTextToArrrayOfWords(String morse) {
        return morse.split(MorseCodeCipher.getInstance().getMorse(' ') 
                + MorseCodeCipher.getCharSeparator() 
                + MorseCodeCipher.getCharSeparator());
    }

    private String translateSingleWord_MorseToText(String morseWord) {
        String textWord = "";
        String [] morseChars = morseWord.split(MorseCodeCipher.getCharSeparator());
        for (int i = 0; i < morseChars.length; ++i){
            textWord += MorseCodeCipher.getInstance().getText(morseChars[i]);
        }
        return textWord;
    }

    public void start() {
        connvertingEnabled = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        connvertingEnabled = false;
        while(true)
        {
            try
            {
                thread.join();
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            break;
        }
        thread = null;
    }

    private Activity getActivity(){
        return activity;
    }
}
