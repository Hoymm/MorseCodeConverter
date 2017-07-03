package com.hoymm.root.morsecodeconverter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter.InputOutputFields.ConvertingTextBoxesPanelAndCopyToClipboard;
import com.hoymm.root.morsecodeconverter.MorseKeyboard.MorseKeyboardPanelAndDisableSoftKeyboard;
import com.hoymm.root.morsecodeconverter.MorseToTextConversion.ConvertingMorseTextProgram;

public class MainActivity extends AppCompatActivity {
    private TopBarSpeedSpinner topBarSpeedSpinner;
    private MorseToTextSwappingPanel morseToTextSwappingPanel;
    private ConvertingTextBoxesPanelAndCopyToClipboard convertingTextBoxesPanelAndCopyToClipboard;
    private ConvertingMorseTextProgram convertingMorseTextProgram;
    private PlayPauseStopButtons playPauseStopButtons;
    private MorseKeyboardPanelAndDisableSoftKeyboard morseKeyboardPanelAndDisableSoftKeyboard;
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
        morseKeyboardPanelAndDisableSoftKeyboard = new MorseKeyboardPanelAndDisableSoftKeyboard(this);
        footerPanel = new FooterPanel(this);
    }

    private void initializateSwapButtonAction() {
        ImageButton swapButton = (ImageButton) findViewById(R.id.swap_button_id);
        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (convertingTextBoxesPanelAndCopyToClipboard.ifNoAnimationCurrentlyRunning()) {
                    MorseToTextSwappingPanel.isConvertingTextToMorse = !MorseToTextSwappingPanel.isConvertingTextToMorse;
                    refreshAndAdjustApplicationComponentsState();
                }
            }
        });
    }

    private void refreshAndAdjustApplicationComponentsState() {
        hideSystemKeyboard(getActivity());
        adjustCompomentsViaAnimation();
        convertingMorseTextProgram.disableTranslationTemporaryForAnimationTime();
        morseToTextSwappingPanel.swapTextHeaders();
        morseKeyboardPanelAndDisableSoftKeyboard.disableOrEnableSystemKeyboard();
        morseToTextSwappingPanel.saveDataToSharedPreferences();
    }

    private void adjustCompomentsViaAnimation() {
        convertingTextBoxesPanelAndCopyToClipboard.swapTextInsideBoxesAnimation();
        morseToTextSwappingPanel.rotateArrowAnimation();
        convertingTextBoxesPanelAndCopyToClipboard.resizeBoxesAnimation();
        morseKeyboardPanelAndDisableSoftKeyboard.hideOrShowMorsePanelAnimation();
    }

    public static void hideSystemKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
