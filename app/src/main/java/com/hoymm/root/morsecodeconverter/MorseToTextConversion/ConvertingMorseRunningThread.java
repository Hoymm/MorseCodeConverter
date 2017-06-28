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
    private static ConvertingMorseRunningThread convertingMorseRunningThread = null;


    private ConvertingMorseRunningThread(Context context) {
        convertingMorseTextProgram = new ConvertingMorseTextProgram(context);
    }

    public static ConvertingMorseRunningThread getInstance(Context context){
        if (convertingMorseRunningThread == null)
            convertingMorseRunningThread = new ConvertingMorseRunningThread(context);
        return convertingMorseRunningThread;
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

    public void start(Context context) {
        Log.e("App Main Thread", " thread started.");
        convertingMorseTextProgram = new ConvertingMorseTextProgram(context);
        convertingEnabled = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        Log.e("App Main Thread", "thread stopped");
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
