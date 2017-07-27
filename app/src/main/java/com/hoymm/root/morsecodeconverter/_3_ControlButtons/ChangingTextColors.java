package com.hoymm.root.morsecodeconverter._3_ControlButtons;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;

import com.hoymm.root.morsecodeconverter.R;
import com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextArrowsSwap;
import com.hoymm.root.morsecodeconverter._2_TextBoxes.TextBoxes;

/**
 * File created by Damian Muca - Kaizen on 19.07.17.
 */

public class ChangingTextColors {
    private Activity activity;
    private static ChangingTextColors changingTextColors = null;

    public static void refreshColors(Activity activity){
        if (changingTextColors == null)
            changingTextColors = new ChangingTextColors(activity);
        changingTextColors.colorCurrentlyTranslatingText();
    }

    private ChangingTextColors(Activity activity) {
        this.activity = activity;
    }

    private void colorCurrentlyTranslatingText() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                colorUpperTextBox();
                colorBottomTextBox();
            }
        });
    }

    private void colorUpperTextBox() {
        int selectionIndex = TextBoxes.initAndGetUpperBox(getActivity()).getSelectionStart();
        int scrollY = TextBoxes.initAndGetUpperBox(getActivity()).getScrollY();

        colorTextOfUpperBox();

        TextBoxes.initAndGetUpperBox(getActivity()).setSelection(selectionIndex);
        TextBoxes.initAndGetUpperBox(getActivity()).setScrollY(scrollY);
    }

    private void colorTextOfUpperBox() {
        String upperBoxText = TextBoxes.initAndGetUpperBox(getActivity()).getText().toString();
        Spannable spannable;
        if (MorseToTextArrowsSwap.isConvertingTextToMorse)
            spannable = getColoredText(upperBoxText);
        else
            spannable = getColoredMorse(upperBoxText);
        TextBoxes.initAndGetUpperBox(getActivity()).setText(spannable);
    }

    private void colorBottomTextBox() {
        int scrollY = TextBoxes.initAndGetBottomBox(getActivity()).getScrollY();

        colorTextOfBottomBox();

        TextBoxes.initAndGetBottomBox(getActivity()).setScrollY(scrollY);
    }

    private void colorTextOfBottomBox() {
        String bottomBoxText = TextBoxes.initAndGetBottomBox(getActivity()).getText().toString();
        Spannable spannable;
        if (MorseToTextArrowsSwap.isConvertingTextToMorse)
            spannable = getColoredMorse(bottomBoxText);
        else
            spannable = getColoredText(bottomBoxText);
        TextBoxes.initAndGetBottomBox(getActivity()).setTextKeepState(spannable);
    }

    private Spannable getColoredText(String text) {
        Spannable spannable = new SpannableString(text);
        int backgroundColor = ContextCompat.getColor(getActivity(), R.color.backgroundTextColorWhenBroadcast);
        int startIndex = ConvertMorseToSignals.initAndGetInstance(getActivity()).getStartBroadcastingTextIndex();
        int endIndex = ConvertMorseToSignals.initAndGetInstance(getActivity()).getEndBroadcastingTextIndex();
        spannable.setSpan(new BackgroundColorSpan(backgroundColor), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    private Spannable getColoredMorse(String text) {
        Spannable spannable = new SpannableString(text);
        int backgroundColor = ContextCompat.getColor(getActivity(), R.color.backgroundTextColorWhenBroadcast);
        int startIndex = ConvertMorseToSignals.initAndGetInstance(getActivity()).getStartBroadcastingMorseIndex();
        int endIndex = ConvertMorseToSignals.initAndGetInstance(getActivity()).getEndBroadcastingMorseIndex();
        spannable.setSpan(new BackgroundColorSpan(backgroundColor), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    private Activity getActivity() {
        return activity;
    }
}
