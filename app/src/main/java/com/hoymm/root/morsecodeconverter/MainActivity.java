package com.hoymm.root.morsecodeconverter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter.InputOutputFields.ConvertingTextBoxesPanelAndCopyToClipboard;
import com.hoymm.root.morsecodeconverter.MorseKeyboard.MorseKeyboardPanel;
import com.hoymm.root.morsecodeconverter.MorseToTextConversion.ConvertingMorseTextProgram;

public class MainActivity extends AppCompatActivity {
    private TopBarSpeedSpinner topBarSpeedSpinner;
    private MorseToTextSwappingPanel morseToTextSwappingPanel;
    private ConvertingTextBoxesPanelAndCopyToClipboard convertingTextBoxesPanelAndCopyToClipboard;
    private ConvertingMorseTextProgram convertingMorseTextProgram;
    private PlayPauseStopButtons playPauseStopButtons;
    private MorseKeyboardPanel morseKeyboardPanel;
    private FooterPanel footerPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeProgramComponents();
        initializateSwapButtonAction();
    }

    private void initializeProgramComponents() {
        topBarSpeedSpinner = new TopBarSpeedSpinner(this);
        morseToTextSwappingPanel = new MorseToTextSwappingPanel(this);
        convertingTextBoxesPanelAndCopyToClipboard = new ConvertingTextBoxesPanelAndCopyToClipboard(this);
        convertingMorseTextProgram = new ConvertingMorseTextProgram(this);
        playPauseStopButtons = new PlayPauseStopButtons(this);
        morseKeyboardPanel = new MorseKeyboardPanel(this);
        footerPanel = new FooterPanel(this);
    }

    private void initializateSwapButtonAction() {
        ImageButton swapButton = (ImageButton) findViewById(R.id.swap_button_id);
        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (convertingTextBoxesPanelAndCopyToClipboard.ifNoAnimationCurrentlyRunning()) {
                    MorseToTextSwappingPanel.isConvertingTextToMorse = !MorseToTextSwappingPanel.isConvertingTextToMorse;
                    convertingTextBoxesPanelAndCopyToClipboard.swapTextInsideBoxesAnimation();
                    convertingMorseTextProgram.disableTranslationTemporaryForAnimationTime();
                    refreshAndAdjustApplicationComponentsState();
                    saveInfo_SharedPreferences();
                }
            }
        });
    }

    private void refreshAndAdjustApplicationComponentsState() {

        hideSystemKeyboard(getActivity());
        morseToTextSwappingPanel.rotateArrowAnimation();
        morseToTextSwappingPanel.swapTextHeaders();
        convertingTextBoxesPanelAndCopyToClipboard.resizeBoxesAnimation();
        morseKeyboardPanel.hideOrShowMorsePanel();
        morseKeyboardPanel.disableOrEnableSystemKeyboard();
    }

    public static void hideSystemKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
        refreshAndAdjustApplicationComponentsState();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
