package com.hoymm.root.morsecodeconverter.MorseToTextConversion;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.hoymm.root.morsecodeconverter.MorseToTextSwappingPanel;

/**
 * File created by Damian Muca - Kaizen on 26.06.17.
 */

public class ConvertingMorseRunningThread implements Runnable {
    private boolean convertingEnabled;
    private Thread thread;
    private ConvertingMorseTextProgram convertingMorseTextProgram;


    public ConvertingMorseRunningThread(Context context) {
        convertingMorseTextProgram = new ConvertingMorseTextProgram(context);
    }

    @Override
    public void run() {
        while(convertingEnabled) {
            try {
                Thread.sleep(750);
                if (MorseToTextSwappingPanel.isConvertingTextToMorse)
                    convertingMorseTextProgram.translateToMorse_SetText();
                else
                    convertingMorseTextProgram.translateToText_SetText();
            } catch (InterruptedException e) {
                Log.e("ConvertingMorseDyn", " thread stopped.");
            }
        }
    }

    public void start() {
        convertingEnabled = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        convertingEnabled = false;
        try {
            thread.join();
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
        thread = null;
    }
}
