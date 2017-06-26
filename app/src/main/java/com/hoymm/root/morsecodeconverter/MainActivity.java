package com.hoymm.root.morsecodeconverter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter.InputOutputFields.ConvertingTextFieldsPanel;
import com.hoymm.root.morsecodeconverter.MorseKeyboard.MorseKeyboardPanel;

public class MainActivity extends AppCompatActivity {
    private TopBarSpeedSpinner topBarSpeedSpinner;
    private MorseToTextSwappingPanel morseToTextSwappingPanel;
    private ConvertingTextFieldsPanel convertingTextFieldsPanel;
    private PlayPauseStopButtons playPauseStopButtons;
    private MorseKeyboardPanel morseKeyboardPanel;
    private FooterPanel footerPanel;
    private ConvertingMorseDynamically convertingMorseDynamically;

    private ImageButton swapButton;

    public static void hideSystemKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeProgramComponents();
        initializateSwapButtonAction();
        swapButton.callOnClick();
    }

    private void initializeProgramComponents() {
        topBarSpeedSpinner = new TopBarSpeedSpinner(this);
        morseToTextSwappingPanel = new MorseToTextSwappingPanel(this);
        convertingTextFieldsPanel = new ConvertingTextFieldsPanel(this);
        playPauseStopButtons = new PlayPauseStopButtons(this);
        morseKeyboardPanel = new MorseKeyboardPanel(this);
        footerPanel = new FooterPanel(this);
        convertingMorseDynamically = new ConvertingMorseDynamically(this);
    }



    private void initializateSwapButtonAction() {
        swapButton = (ImageButton) findViewById(R.id.swap_button_id);
        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noAnimationIsWorkingRightNow()) {
                    MorseToTextSwappingPanel.isConvertingTextToMorse = !MorseToTextSwappingPanel.isConvertingTextToMorse;
                    hideSystemKeyboard(getActivity());
                    rotateArrowButton();
                    swapTexts();
                    resizeBoxes();
                    showHideMorseKeyboard();
                    disableSystemKeyboardWhenMorse();
                    saveInfo_SharedPreferences();
                }
            }
        });
    }
    private boolean noAnimationIsWorkingRightNow() {
        return convertingTextFieldsPanel.ifNoAnimationCurrentlyRunning();
    }

    private void rotateArrowButton() {
        morseToTextSwappingPanel.rotateArrowAnimation();
    }

    private void resizeBoxes() {
        convertingTextFieldsPanel.resizeBoxesAnimation();
    }

    private void swapTexts() {
        morseToTextSwappingPanel.swapTextHeaders();
        convertingTextFieldsPanel.swapTextInsideBoxesAnimation();
    }

    private void showHideMorseKeyboard() {
        morseKeyboardPanel.hideOrShowMorsePanel();
    }

    private void disableSystemKeyboardWhenMorse() {
        morseKeyboardPanel.disableOrEnableSystemKeyboardWhenEditTextSelected();
    }

    private void saveInfo_SharedPreferences() {
        morseToTextSwappingPanel.saveToSharedPreferencesReversedTranslationDirection();
    }

    private Activity getActivity() {
        return this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        convertingMorseDynamically.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        convertingMorseDynamically.pause();
    }
}
