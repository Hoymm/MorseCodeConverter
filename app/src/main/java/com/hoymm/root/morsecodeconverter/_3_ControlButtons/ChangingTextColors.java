package com.hoymm.root.morsecodeconverter._3_ControlButtons;

import android.app.Activity;
import android.os.Build;
import android.text.Html;

import com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextArrowsSwap;
import com.hoymm.root.morsecodeconverter._2_TextBoxes.TextBoxes;

/**
 * File created by Damian Muca - Kaizen on 19.07.17.
 */

class ChangingTextColors {
    private Activity activity;

    ChangingTextColors(Activity activity) {
        this.activity = activity;
    }

    void colorCurrentlyTranslatingText() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (MorseToTextArrowsSwap.isConvertingTextToMorse){
                    setUpperBoxFromHTMLAndRefreshSelection(generateHTMLStringForText());
                    setBottomBoxFromHTML(generateHTMLStringForMorse());
                }
                else{
                    setUpperBoxFromHTMLAndRefreshSelection(generateHTMLStringForMorse());
                    setBottomBoxFromHTML(generateHTMLStringForText());
                }
            }
        });
    }

    private String generateHTMLStringForText() {
        return ConvertMorseToSignals.initAndGetInstance(getActivity()).getTextWholeText();
    }

    @SuppressWarnings("deprecation")
    private void setUpperBoxFromHTMLAndRefreshSelection(String text) {
        int selectionIndex = TextBoxes.initAndGetUpperBox(getActivity()).getSelectionStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            TextBoxes.initAndGetUpperBox(getActivity()).setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY));
        else
            TextBoxes.initAndGetUpperBox(getActivity()).setText(Html.fromHtml(text));
        TextBoxes.initAndGetUpperBox(getActivity()).setSelection(selectionIndex);
    }

    private String generateHTMLStringForMorse() {
        String morse = ConvertMorseToSignals.initAndGetInstance(getActivity()).getMorseWholeText();
        int charStartIndex = ConvertMorseToSignals.initAndGetInstance(getActivity()).getCurrentBroadcastingCharIndex();

        if (charStartIndex != ConvertMorseToSignals.initAndGetInstance(getActivity()).getMorseWholeText().length()) {
            String morseFirstPart = morse.substring(0, charStartIndex);
            String morseCharToColor = "<font color='#EE0000'>" + morse.substring(charStartIndex, charStartIndex+1) + "</font>";
            String morseLastPart = morse.substring(charStartIndex+1);
            return morseFirstPart + morseCharToColor + morseLastPart;
        }
        return morse;
    }

    @SuppressWarnings("deprecation")
    private void setBottomBoxFromHTML(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            TextBoxes.initAndGetBottomBox(getActivity()).setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY));
        else
            TextBoxes.initAndGetBottomBox(getActivity()).setText(Html.fromHtml(text));
    }

    private Activity getActivity() {
        return activity;
    }
}
