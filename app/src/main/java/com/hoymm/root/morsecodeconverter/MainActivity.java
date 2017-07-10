package com.hoymm.root.morsecodeconverter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter.FooterPanel.FlashlightButton;
import com.hoymm.root.morsecodeconverter.FooterPanel.ScreenButton;
import com.hoymm.root.morsecodeconverter.FooterPanel.SoundButton;
import com.hoymm.root.morsecodeconverter.FooterPanel.VibrationButton;
import com.hoymm.root.morsecodeconverter.InputOutputFields.ConvertingTextBoxesPanel;
import com.hoymm.root.morsecodeconverter.MorseKeyboard.MorseKeyboardPanelAndDisableSoftKeyboard;
import com.hoymm.root.morsecodeconverter.MorseToTextConversion.ConvertingMorseTextProgram;

public class MainActivity extends AppCompatActivity {
    private TopBarSpeedSpinner topBarSpeedSpinner;
    private MorseToTextSwappingPanel morseToTextSwappingPanel;
    private ConvertingTextBoxesPanel convertingTextBoxesPanel;
    private SetToClipboardButtonBehavior copyToClipboard;
    private ConvertingMorseTextProgram convertingMorseTextProgram;
    private PlayPauseStopButtons playPauseStopButtons;
    private MorseKeyboardPanelAndDisableSoftKeyboard morseKeyboardPanelAndDisableSoftKeyboard;

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
        convertingTextBoxesPanel = new ConvertingTextBoxesPanel(this);
        convertingMorseTextProgram = new ConvertingMorseTextProgram(this);
        playPauseStopButtons = new PlayPauseStopButtons(this);
        morseKeyboardPanelAndDisableSoftKeyboard = new MorseKeyboardPanelAndDisableSoftKeyboard(this);
        initializateFooterButtons();
    }

    private void initializateFooterButtons() {
        VibrationButton.initializateAndGetInstance(this);
        SoundButton.initializateAndGetInstance(this);
        FlashlightButton.initializateAndGetInstance(this);
        ScreenButton.initializateAndGetInstance(this);
    }

    private void initializateSwapButtonAction() {
        ImageButton swapButton = (ImageButton) findViewById(R.id.swap_button_id);
        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (convertingTextBoxesPanel.ifNoAnimationCurrentlyRunning()) {
                    MorseToTextSwappingPanel.isConvertingTextToMorse = !MorseToTextSwappingPanel.isConvertingTextToMorse;
                    convertingTextBoxesPanel.swapTextInsideBoxes();
                    morseToTextSwappingPanel.rotateArrowAnimation();
                    refreshAndAdjustApplicationComponentsState();
                }
            }
        });
    }

    private void refreshAndAdjustApplicationComponentsState() {
        adjustCompomentsViaAnimation();
        hideSystemKeyboard(getActivity());
        convertingMorseTextProgram.disableTranslationTemporaryForAnimationTime();
        morseToTextSwappingPanel.swapTextHeaders();
        morseKeyboardPanelAndDisableSoftKeyboard.disableOrEnableSystemKeyboard();
        morseToTextSwappingPanel.saveDataToSharedPreferences();

    }

    private void adjustCompomentsViaAnimation() {
        convertingTextBoxesPanel.resizeBoxesAnimation();
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
        convertingTextBoxesPanel.clearSelection();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
