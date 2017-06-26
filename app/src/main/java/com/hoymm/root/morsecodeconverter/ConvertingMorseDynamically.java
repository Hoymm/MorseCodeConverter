package com.hoymm.root.morsecodeconverter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

/**
 * File created by Damian Muca - Kaizen on 26.06.17.
 */

public class ConvertingMorseDynamically implements Runnable {
    private boolean connvertingEnabled;
    private Thread thread;
    private HashMap<Character, String> morseCodeCoding = new HashMap<>();
    private Activity activity;
    private EditText upperBox;
    private TextView bottomBox;


    public ConvertingMorseDynamically(Context context) {
        activity = (Activity) context;
        initMorseCodeCoding();
        initXMLObjects();
    }

    private void initMorseCodeCoding() {
        morseCodeCoding.put(' ',"/");
        morseCodeCoding.put('.',".-.-.-");
        morseCodeCoding.put('A',".-");
        morseCodeCoding.put('B',"-...");
        morseCodeCoding.put('C',"-.-.");
        morseCodeCoding.put('D',"-..");
        morseCodeCoding.put('E',".");
        morseCodeCoding.put('F',"..-.");
        morseCodeCoding.put('G',"--.");
        morseCodeCoding.put('H',"....");
        morseCodeCoding.put('I',"..");
        morseCodeCoding.put('J',".---");
        morseCodeCoding.put('K',"-.-");
        morseCodeCoding.put('L',".-..");
        morseCodeCoding.put('M',"--");
        morseCodeCoding.put('N',"-.");
        morseCodeCoding.put('O',"---");
        morseCodeCoding.put('P',".--.");
        morseCodeCoding.put('Q',"--.-");
        morseCodeCoding.put('R',".-.");
        morseCodeCoding.put('S',"...");
        morseCodeCoding.put('T',"-");
        morseCodeCoding.put('U',"..-");
        morseCodeCoding.put('V',"...-");
        morseCodeCoding.put('W',".--");
        morseCodeCoding.put('X',"-..-");
        morseCodeCoding.put('Y',"-.--");
        morseCodeCoding.put('Z',"--..");
        morseCodeCoding.put('0',"-----");
        morseCodeCoding.put('1',".----");
        morseCodeCoding.put('2',"..---");
        morseCodeCoding.put('3',"...--");
        morseCodeCoding.put('4',"....-");
        morseCodeCoding.put('5',".....");
        morseCodeCoding.put('6',"-....");
        morseCodeCoding.put('7',"--...");
        morseCodeCoding.put('8',"---..");
        morseCodeCoding.put('9',"----.");
        morseCodeCoding.put(',',"--..--");
        morseCodeCoding.put(':',"---...");
        morseCodeCoding.put('?',"..--..");
        morseCodeCoding.put('\'',".----.");
        morseCodeCoding.put('(',"-.--.-");
        morseCodeCoding.put(')',"-.--.-");
        morseCodeCoding.put('\"',".-..-.");
        morseCodeCoding.put('@',".--.-.");
        morseCodeCoding.put('=',"-...-");
        morseCodeCoding.put('\n',".-.-");
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
        text = text.toUpperCase();
        String morse = "";
        for(int index = text.length()-1 ; index >= 0 ; --index){
            char currentCharToTranslate = text.charAt(index);
            text = text.substring(0, index);
            morse += getMorseEquivalent(currentCharToTranslate) + " ";
        }
        return morse.substring(0, morse.length()-1);
    }

    private String getMorseEquivalent(char text) {
        String morseCode = morseCodeCoding.get(text);
        return morseCode;
    }


    private void translateToText_SetText() {
        String morse = String.valueOf(upperBox.getText());
        //bottomBox.setText(translateMorseToText(morse));
    }

    private void translateMorseToText(String morse) {
        /*String text = "";
        for(int index = x.length()-1 ; index >= 0 ; --index){
            char currentCharToTranslate = x.charAt(index);
            x = x.substring(0, index);
            morse += getMorseEquivalent(currentCharToTranslate);
        }
        return text;*/

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
