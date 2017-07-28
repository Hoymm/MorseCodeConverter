package com.hoymm.root.morsecodeconverter._1_TopBar;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hoymm.root.morsecodeconverter.R;
import com.hoymm.root.morsecodeconverter._2_TextBoxes.ResizingTextBoxesAnimation;

/**
 * Created by root on 06.05.17.
 */

public class MorseToTextArrowsSwap {

    private Context myContext;
    private ImageButton arrowButton;
    private TextView leftTextView, rightTextView;
    public static boolean isConvertingTextToMorse = true;


    public MorseToTextArrowsSwap(Context context){
        myContext = context;
        linkObjectsWithXML();
    }

    private void linkObjectsWithXML() {
        leftTextView = (TextView) getActivity().findViewById(R.id.left_text_swap_id);
        rightTextView = (TextView) getActivity().findViewById(R.id.right_text_swap_id);
        arrowButton = (ImageButton) getActivity().findViewById(R.id.swap_button_id);
    }

    private Context getContext(){
        return myContext;
    }

    public void restoreTranslationDirection() {
        isConvertingTextToMorse = isLastTranslationFromTextToMorse();
    }

    private boolean isLastTranslationFromTextToMorse(){
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getBoolean(getIsTranslationFromTextToMorseKey(), true);
    }

    public void refreshTextHeaders(){
        if (isConvertingTextToMorse) {
            leftTextView.setText(getActivity().getString(R.string.text));
            rightTextView.setText(getActivity().getString(R.string.morse));
        }
        else{
            leftTextView.setText(getActivity().getString(R.string.morse));
            rightTextView.setText(getActivity().getString(R.string.text));
        }
    }

    public void saveTranslatingDirectionToSP() {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        saveTranslationDirectionInfo(editor);
        editor.apply();
    }

    private void saveTranslationDirectionInfo(SharedPreferences.Editor editor) {
        editor.putBoolean(getIsTranslationFromTextToMorseKey(), isConvertingTextToMorse);
    }

    private String getIsTranslationFromTextToMorseKey(){
        return this.getContext().getString(R.string.isTranslationFromTextToMorseSP);
    }

    private Activity getActivity(){
        return ((Activity)myContext);
    }


    public boolean rotateArrowAnimation(){
        ValueAnimator arrowRotateAnimation = ValueAnimator.ofFloat(arrowButton.getRotationY(), arrowButton.getRotationY() + 180F);
        arrowRotateAnimation.setDuration(ResizingTextBoxesAnimation.animationTime);
        arrowRotateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        arrowRotateAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                arrowButton.setRotationY(animatedValue);
            }
        });
        arrowRotateAnimation.start();
        return true;
    }
}
