package com.hoymm.root.morsecodeconverter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextArrowsSwap;
import com.hoymm.root.morsecodeconverter._1_TopBar.TopBarSpeedSpinner;
import com.hoymm.root.morsecodeconverter._3_ControlButtons.ChangingTextColors;
import com.hoymm.root.morsecodeconverter._3_ControlButtons.ConvertMorseToSignals;
import com.hoymm.root.morsecodeconverter._3_ControlButtons.PauseButton;
import com.hoymm.root.morsecodeconverter._3_ControlButtons.PlayButton;
import com.hoymm.root.morsecodeconverter._3_ControlButtons.StopButton;
import com.hoymm.root.morsecodeconverter._2_TextBoxes.SetToClipboardButtonBehavior;
import com.hoymm.root.morsecodeconverter._4_MorseKeyboard.BackspaceButton.BackspaceButton;
import com.hoymm.root.morsecodeconverter._4_MorseKeyboard.DotButton;
import com.hoymm.root.morsecodeconverter._4_MorseKeyboard.SpaceButton;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.FlashlightButton;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.ScreenButton;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.SoundButton;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.VibrationButton;
import com.hoymm.root.morsecodeconverter._2_TextBoxes.HandleTextBoxesConversion;
import com.hoymm.root.morsecodeconverter._4_MorseKeyboard.MorseKeyboardPanelAndDisableSoftKeyboard;
import com.hoymm.root.morsecodeconverter.MorseToTextConversionProg.ConvertingMorseTextProgram;

public class MainActivity extends AppCompatActivity {
    private TopBarSpeedSpinner topBarSpeedSpinner;
    private MorseToTextArrowsSwap morseToTextSwappingPanel;
    private HandleTextBoxesConversion handleTextBoxesConversion;
    private SetToClipboardButtonBehavior copyToClipboard;
    private ConvertingMorseTextProgram convertingMorseTextProgram;
    private MorseKeyboardPanelAndDisableSoftKeyboard morseKeyboardPanelAndDisableSoftKeyboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeProgramComponents();
        setArrowsSwapButtonBehavior();
    }

    private void initializeProgramComponents() {
        TopBarSpeedSpinner.initAndGetInstance(getActivity());
        morseToTextSwappingPanel = new MorseToTextArrowsSwap(getActivity());
        handleTextBoxesConversion = new HandleTextBoxesConversion(getActivity());
        convertingMorseTextProgram = new ConvertingMorseTextProgram(getActivity());
        morseKeyboardPanelAndDisableSoftKeyboard =
                        new MorseKeyboardPanelAndDisableSoftKeyboard(getActivity());
    }

    private void initializateControlPanelButtons() {
        PlayButton.initAndGetInstance(getActivity());
        PauseButton.initAndGetInstance(getActivity());
        initAndActivateStopButton();
    }

    private void initAndActivateStopButton() {
        StopButton.initAndGetInstance(getActivity()).callOnClick();
    }

    private void initializateFooterButtons() {
        VibrationButton.initializateAndGetInstance(getActivity());
        SoundButton.initializateAndGetInstance(getActivity());
        FlashlightButton.initializateAndGetInstance(getActivity());
        ScreenButton.initializateAndGetInstance(getActivity());
    }

    private void setArrowsSwapButtonBehavior() {
        ImageButton swapButton = (ImageButton) findViewById(R.id.swap_button_id);
        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (handleTextBoxesConversion.ifNoAnimationCurrentlyRunning()) {
                    MorseToTextArrowsSwap.isConvertingTextToMorse = !MorseToTextArrowsSwap.isConvertingTextToMorse;
                    handleTextBoxesConversion.swapTextInsideBoxes();
                    morseToTextSwappingPanel.rotateArrowAnimation();
                    refreshAndAdjustApplicationComponentsState();
                    refreshTextColorsIfNotFirstIndexCurrentlyBroadcast();
                    decideAndSetUpperBoxSelectable();
                    handleTextBoxesConversion.setSelectionsAtTheEnd();
                }
            }
        });
    }

    private void decideAndSetUpperBoxSelectable() {
        PlayButton.setUpperBoxSelectableFalseIfPlayOrPauseButtonActive(getActivity());
    }

    private void refreshTextColorsIfNotFirstIndexCurrentlyBroadcast() {
        if (ConvertMorseToSignals.initAndGetInstance(getActivity()).getStartBroadcastingMorseIndex() != 0)
            ChangingTextColors.refreshColors(getActivity());
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
        handleTextBoxesConversion.resizeBoxesAnimation();
        morseKeyboardPanelAndDisableSoftKeyboard.hideOrShowMorsePanelAnimation();
    }

    public static void hideSystemKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void initButtons() {
        copyToClipboard = new SetToClipboardButtonBehavior(getActivity());
        initializateControlPanelButtons();
        initializateFooterButtons();
    }

    private Activity getActivity() {
        return this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshAndAdjustApplicationComponentsState();
        initButtons();
    }

    @Override
    protected void onPause() {
        handleTextBoxesConversion.clearSelection();
        PauseButton.initAndGetInstance(getActivity()).ifButtonInactiveThenCallOnclick();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        StopButton.initAndGetInstance(getActivity()).ifButtonInactiveThenCallOnclick();
        setObjectsNull();
        super.onDestroy();
    }

    private void setObjectsNull() {
        TopBarSpeedSpinner.setNull();

        PlayButton.setNull();
        PauseButton.setNull();
        StopButton.setNull();

        SpaceButton.setNull();
        DotButton.setNull();
        SpaceButton.setNull();
        BackspaceButton.setNull();

        VibrationButton.setNull();
        SoundButton.setNull();
        FlashlightButton.setNull();
        ScreenButton.setNull();
    }
}
