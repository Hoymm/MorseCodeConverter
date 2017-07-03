package com.hoymm.root.morsecodeconverter.InputOutputFields;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.TextView;

import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 21.06.17.
 */

public class RemovingInsertingTextAnimation {
    private Context context;
    public ValueAnimator upperRemoveText, upperAddText, bottomRemoveText, bottomAddText;
    private EditText upperBox;
    private TextView bottomBox;
    private static String prevUpperBoxText, prevBottomBoxText;

    public RemovingInsertingTextAnimation(Context context) {
        this.context = context;
        initEditTextsAndPlaceSelectionOnTheEnd();
        saveUppercaseTextToVariables();
        initializateAnimations();
    }

    private void initEditTextsAndPlaceSelectionOnTheEnd() {
        initEditText();
        setSelectionOnTheEnd();
    }

    private void initEditText() {
        upperBox = (EditText) getActivity().findViewById(R.id.upper_edit_text_box);
        bottomBox = (TextView) getActivity().findViewById(R.id.bottom_text_view_box);
    }

    private void setSelectionOnTheEnd() {
        upperBox.setSelection(upperBox.getText().toString().length());
    }

    private void saveUppercaseTextToVariables() {
        prevBottomBoxText = bottomBox.getText().toString().toUpperCase();
        prevUpperBoxText = upperBox.getText().toString().toUpperCase();
    }

    private void initializateAnimations() {
        upperRemoveTextInit();
        upperAddTextInit();
        bottomRemoveTextInit();
        bottomAddTextInit();
    }

    private void upperRemoveTextInit() {
        final String upperBoxString = upperBox.getText().toString();
        upperRemoveText = ValueAnimator.ofFloat(upperBox.getText().toString().length(), 0);
        upperRemoveText.setDuration(ResizingTextBoxesAnimation.animationTime/2);
        upperRemoveText.setInterpolator(new AccelerateDecelerateInterpolator());
        upperRemoveText.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                upperBox.setText(upperBoxString.substring(0,(int) animatedValue));
                upperBox.setSelection((int) animatedValue);
            }
        });

        upperRemoveText.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                upperAddText.start();
            }
        });
    }

    private void upperAddTextInit() {
        upperAddText = ValueAnimator.ofFloat(0, prevBottomBoxText.length());
        upperAddText.setDuration(ResizingTextBoxesAnimation.animationTime/2);
        upperAddText.setInterpolator(new AccelerateDecelerateInterpolator());
        upperAddText.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                upperBox.setText(prevBottomBoxText.substring(0,(int) animatedValue));
                upperBox.setSelection((int) animatedValue);
            }
        });
    }

    private void bottomRemoveTextInit() {
        final String upperBoxString = bottomBox.getText().toString();
        bottomRemoveText = ValueAnimator.ofFloat(bottomBox.getText().toString().length(), 0);
        bottomRemoveText.setDuration(ResizingTextBoxesAnimation.animationTime/2);
        bottomRemoveText.setInterpolator(new AccelerateDecelerateInterpolator());
        bottomRemoveText.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                bottomBox.setText(upperBoxString.substring(0,(int) animatedValue));
            }
        });

        bottomRemoveText.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                bottomAddText.start();
            }
        });
    }

    private void bottomAddTextInit() {
        bottomAddText = ValueAnimator.ofFloat(0, prevUpperBoxText.length());
        bottomAddText.setDuration(ResizingTextBoxesAnimation.animationTime/2);
        bottomAddText.setInterpolator(new AccelerateDecelerateInterpolator());
        bottomAddText.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                bottomBox.setText(prevUpperBoxText.substring(0,(int) animatedValue));
            }
        });
    }

    private Activity getActivity(){
        return (Activity)context;
    }

    void startAnimation(){
        saveUppercaseTextToVariables();
        initializateAnimations();
        upperRemoveText.start();
        bottomRemoveText.start();
    }
}
