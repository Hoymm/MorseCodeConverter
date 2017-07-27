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
                colorUpperTextBoxAndScrollFollowingCurChar();
                colorBottomTextBoxAndScrollFollowingCurChar();
            }
        });
    }

    private void colorUpperTextBoxAndScrollFollowingCurChar() {
        String upperBoxText = TextBoxes.initAndGetUpperBox(getActivity()).getText().toString();
        Spannable spannable;
        if (MorseToTextArrowsSwap.isConvertingTextToMorse) {
            spannable = getColoredText(upperBoxText);
            TextBoxes.initAndGetUpperBox(getActivity()).setText(spannable);
            TextBoxes.initAndGetUpperBox(getActivity()).setSelection(getColoredTextStartIndex());

        } else {
            spannable = getColoredMorse(upperBoxText);
            TextBoxes.initAndGetUpperBox(getActivity()).setText(spannable);
            TextBoxes.initAndGetUpperBox(getActivity()).setSelection(getColoredMorseStartIndex());
        }
    }

    private void colorBottomTextBoxAndScrollFollowingCurChar() {
        String bottomBoxText = TextBoxes.initAndGetBottomBox(getActivity()).getText().toString();
        Spannable spannable;
        if (MorseToTextArrowsSwap.isConvertingTextToMorse) {
            spannable = getColoredMorse(bottomBoxText);
            TextBoxes.initAndGetUpperBox(getActivity()).setSelection(getColoredMorseStartIndex());
            TextBoxes.initAndGetBottomBox(getActivity()).setText(spannable);
        }
        else {
            spannable = getColoredText(bottomBoxText);
            TextBoxes.initAndGetUpperBox(getActivity()).setSelection(getColoredTextStartIndex());
            TextBoxes.initAndGetBottomBox(getActivity()).setText(spannable);
        }
    }

    private Spannable getColoredText(String text) {
        Spannable spannable = new SpannableString(text);
        int backgroundColor = ContextCompat.getColor(getActivity(), R.color.backgroundTextColorWhenBroadcast);
        int startIndex = getColoredTextStartIndex();
        int endIndex = ConvertMorseToSignals.initAndGetInstance(getActivity()).getEndBroadcastingTextIndex();
        spannable.setSpan(new BackgroundColorSpan(backgroundColor), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    private int getColoredTextStartIndex() {
        return ConvertMorseToSignals.initAndGetInstance(getActivity()).getStartBroadcastingTextIndex();
    }

    private Spannable getColoredMorse(String text) {
        Spannable spannable = new SpannableString(text);
        int backgroundColor = ContextCompat.getColor(getActivity(), R.color.backgroundTextColorWhenBroadcast);
        int startIndex = getColoredMorseStartIndex();
        int endIndex = ConvertMorseToSignals.initAndGetInstance(getActivity()).getEndBroadcastingMorseIndex();
        spannable.setSpan(new BackgroundColorSpan(backgroundColor), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    private int getColoredMorseStartIndex() {
        return ConvertMorseToSignals.initAndGetInstance(getActivity()).getStartBroadcastingMorseIndex();
    }

    private Activity getActivity() {
        return activity;
    }
}
