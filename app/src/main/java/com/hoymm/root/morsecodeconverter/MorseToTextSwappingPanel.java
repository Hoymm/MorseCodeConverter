package com.hoymm.root.morsecodeconverter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by root on 06.05.17.
 */

public class MorseToTextSwappingPanel {

    private Context myContext;

    private ImageButton swapButton;
    private TextView leftTextView, rightTextView;

    public MorseToTextSwappingPanel(Context context){
        myContext = context;
        linkObjectsWithXML();
        restoreLastTextViewsStatus();
        setSwapButtonAction();
    }

    private void linkObjectsWithXML() {
        swapButton = (ImageButton) ((Activity)this.getContext()).findViewById(R.id.swap_button_id);
        leftTextView = (TextView) ((Activity)this.getContext()).findViewById(R.id.left_text_swap_id);
        rightTextView = (TextView) ((Activity)this.getContext()).findViewById(R.id.right_text_swap_id);
    }

    private Context getContext(){
        return myContext;
    }

    private void restoreLastTextViewsStatus() {
        restoreTags();
        restoreTexts();
    }

    private void restoreTags() {
        String textTag = this.getContext().getString(R.string.text_tag);
        String morseTag = this.getContext().getString(R.string.morse_tag);
        if(isLastTranslationFromMorseToText()) {
            leftTextView.setTag(morseTag);
            rightTextView.setTag(textTag);
        }
        else{
            leftTextView.setTag(textTag);
            rightTextView.setTag(morseTag);
        }
    }

    private void restoreTexts() {
        String textText = this.getContext().getString(R.string.text);
        String morseText = this.getContext().getString(R.string.morse);
        if(isLastTranslationFromMorseToText()) {
            leftTextView.setText(morseText);
            rightTextView.setText(textText);
        }
        else{
            leftTextView.setText(textText);
            rightTextView.setText(morseText);
        }
    }

    private boolean isLastTranslationFromMorseToText(){
        SharedPreferences sharedPref = ((Activity)myContext).getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getBoolean(getIsTranslationFromMorseToTextKey(), true);
    }

    private void setSwapButtonAction() {
        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapTags();
                swapTexts();
                saveToSharedPreferencesReversedTranslationDirection();
            }
        });
    }

    private void swapTags(){
        String leftTag = leftTextView.getTag().toString();
        String rightTag = rightTextView.getTag().toString();
        leftTextView.setTag(rightTag);
        rightTextView.setTag(leftTag);
    }

    private void swapTexts(){
        String leftText = leftTextView.getText().toString();
        String rightText = rightTextView.getText().toString();
        leftTextView.setText(rightText);
        rightTextView.setText(leftText);
    }

    private void saveToSharedPreferencesReversedTranslationDirection() {
        SharedPreferences sharedPref = ((Activity)this.getContext()).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        boolean putTo = !isTranslationFromTextToMorse();
        editor.putBoolean(getIsTranslationFromMorseToTextKey(), putTo);
        editor.apply();
    }

    private String getIsTranslationFromMorseToTextKey(){
        return this.getContext().getString(R.string.isTranslationFromTextToMorse);
    }

    private boolean isTranslationFromTextToMorse() {
        return leftTextView.getTag().equals(this.getContext().getString(R.string.text_tag));
    }
}
