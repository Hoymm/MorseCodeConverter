package com.hoymm.root.morsecodeconverter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextSwappingPanelConversion;
import com.hoymm.root.morsecodeconverter._1_TopBar.TopBarSpeedSpinner;
import com.hoymm.root.morsecodeconverter._3_ControlButtons.PauseButton;
import com.hoymm.root.morsecodeconverter._3_ControlButtons.PlayButton;
import com.hoymm.root.morsecodeconverter._3_ControlButtons.StopButton;
import com.hoymm.root.morsecodeconverter._3_ControlButtons.PlayPauseStopButtons;
import com.hoymm.root.morsecodeconverter._2_TextBoxes.SetToClipboardButtonBehavior;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.FlashlightButton;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.ScreenButton;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.SoundButton;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.VibrationButton;
import com.hoymm.root.morsecodeconverter._2_TextBoxes.ConvertingTextBoxesPanel;
import com.hoymm.root.morsecodeconverter._4_MorseKeyboard.MorseKeyboardPanelAndDisableSoftKeyboard;
import com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextConversion.ConvertingMorseTextProgram;

public class MainActivity extends AppCompatActivity {
    private TopBarSpeedSpinner topBarSpeedSpinner;
    private MorseToTextSwappingPanelConversion morseToTextSwappingPanel;
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
        topBarSpeedSpinner = new TopBarSpeedSpinner(getActivity());
        morseToTextSwappingPanel = new MorseToTextSwappingPanelConversion(getActivity());
        convertingTextBoxesPanel = new ConvertingTextBoxesPanel(getActivity());
        convertingMorseTextProgram = new ConvertingMorseTextProgram(getActivity());
        morseKeyboardPanelAndDisableSoftKeyboard =
                        new MorseKeyboardPanelAndDisableSoftKeyboard(getActivity());
        initializateControlPanelButtons();
        initializateFooterButtons();
    }

    private void initializateControlPanelButtons() {
        PlayPauseStopButtons.initAndGetInstance(this);
        PlayButton.initAndGetInstance(this);
        PauseButton.initAndGetInstance(this);
        StopButton.initAndGetInstance(this);
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
                    MorseToTextSwappingPanelConversion.isConvertingTextToMorse = !MorseToTextSwappingPanelConversion.isConvertingTextToMorse;
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
