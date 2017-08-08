package com.hoymm.root.morsecodeconverter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextArrowsSwap;
import com.hoymm.root.morsecodeconverter._1_TopBar.TopBarSpeedSpinner;
import com.hoymm.root.morsecodeconverter._2_TextBoxes.TextBoxes;
import com.hoymm.root.morsecodeconverter._3_ControlButtons.BroadcastingTextIndexesSharedPreferencesHandle;
import com.hoymm.root.morsecodeconverter._3_ControlButtons.ChangingTextColors;
import com.hoymm.root.morsecodeconverter._3_ControlButtons.ControlButtonsSharedPreferences;
import com.hoymm.root.morsecodeconverter._3_ControlButtons.ConvertMorseToSignals;
import com.hoymm.root.morsecodeconverter._3_ControlButtons.PauseButton;
import com.hoymm.root.morsecodeconverter._3_ControlButtons.PlayButton;
import com.hoymm.root.morsecodeconverter._3_ControlButtons.StopButton;
import com.hoymm.root.morsecodeconverter._2_TextBoxes.SetToClipboardButtonBehavior;
import com.hoymm.root.morsecodeconverter._4_MorseKeyboard.BackspaceButton.BackspaceButton;
import com.hoymm.root.morsecodeconverter._4_MorseKeyboard.DotButton;
import com.hoymm.root.morsecodeconverter._4_MorseKeyboard.LineButton;
import com.hoymm.root.morsecodeconverter._4_MorseKeyboard.MorseKeyboardSharedPreferences;
import com.hoymm.root.morsecodeconverter._4_MorseKeyboard.SpaceButton;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.FlashlightButton;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.FlashlightPermissions;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.ScreenButton;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.SoundButton;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.VibrationButton;
import com.hoymm.root.morsecodeconverter._2_TextBoxes.HandleTextBoxesConversion;
import com.hoymm.root.morsecodeconverter._4_MorseKeyboard.MorseKeyboardPanelAndDisableSoftKeyboard;
import com.hoymm.root.morsecodeconverter.MorseToTextConversionProg.ConvertingMorseTextProgram;

public class MainActivity extends AppCompatActivity {
    private MorseToTextArrowsSwap morseToTextSwappingPanel;
    private HandleTextBoxesConversion handleTextBoxesConversion;
    private SetToClipboardButtonBehavior copyToClipboard;
    private ConvertingMorseTextProgram convertingMorseTextProgram;
    private MorseKeyboardPanelAndDisableSoftKeyboard morseKeyboardPanelAndDisableSoftKeyboard;

    public static boolean destroyed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        destroyed = false;
        initializeProgramComponents();
        setArrowsSwapButtonBehavior();
    }

    private void initializeProgramComponents() {
        TopBarSpeedSpinner.initAndGetInstance(getActivity());
        morseToTextSwappingPanel = new MorseToTextArrowsSwap(getActivity());
        handleTextBoxesConversion = new HandleTextBoxesConversion(getActivity());
        convertingMorseTextProgram = new ConvertingMorseTextProgram(getActivity());
        morseKeyboardPanelAndDisableSoftKeyboard = new MorseKeyboardPanelAndDisableSoftKeyboard(getActivity());
    }

    private void initializateControlPanelButtons() {
        PlayButton.initAndGetInstance(getActivity());
        PauseButton.initAndGetInstance(getActivity());
        StopButton.initAndGetInstance(getActivity());
    }

    private void initializateFooterButtons() {
        VibrationButton.initAndGetInstance(getActivity());
        SoundButton.initAndGetInstance(getActivity());
        FlashlightButton.initAndGetInstance(getActivity());
        ScreenButton.initAndGetInstance(getActivity());
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
                    handleTextBoxesConversion.setSelectionsAtTheEnd();
                }
            }
        });
    }

    private void ifStopButtonDisabledSetTextSelectableFalse() {
        PlayButton.initAndGetInstance(getActivity()).decideSetUpperBoxSelectableDueToControlButtonActive();
    }

    private void refreshTextColorsIfNotFirstIndexCurrentlyBroadcast() {
        if (ConvertMorseToSignals.initAndGetInstance(getActivity()).getStartBroadcastingMorseIndex() != 0)
            ChangingTextColors.initAndGetInstance(getActivity()).refreshColors();
        TextBoxes.setProperTextColor(getActivity());
    }

    private void refreshAndAdjustApplicationComponentsState() {
        adjustCompomentsViaAnimation();
        hideSystemKeyboard(getActivity());
        convertingMorseTextProgram.disableTranslationTemporaryForAnimationTime();
        morseToTextSwappingPanel.refreshTextHeaders();
        morseKeyboardPanelAndDisableSoftKeyboard.disableOrEnableSystemKeyboard();
        refreshTextColorsIfNotFirstIndexCurrentlyBroadcast();
        ifStopButtonDisabledSetTextSelectableFalse();
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

    private void initAndAdjustButtonsState() {
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
        restoreDataFromSharedPreferences();
        refreshAndAdjustApplicationComponentsState();
        initAndAdjustButtonsState();
    }

    private void restoreDataFromSharedPreferences() {
        morseToTextSwappingPanel.restoreTranslationDirection();
        TextBoxes.restoreTextBoxesContentFromSharedPreferences(getActivity());
        BroadcastingTextIndexesSharedPreferencesHandle.restoreIndexesOfCurBroadcastTextOrSetToDefaultIfNotStoredSP(getActivity());
        ControlButtonsSharedPreferences.restoreLatelyActivatedButton(getActivity());
        MorseKeyboardSharedPreferences.restoreLatelyActiveButtons(getActivity());
    }

    @Override
    protected void onPause() {
        handleTextBoxesConversion.clearSelection();
        ifPlayButtonActiveThenCallPause();
        saveDataToSharedPreferences();
        super.onPause();
    }

    private void ifPlayButtonActiveThenCallPause() {
        if (PlayButton.initAndGetInstance(getActivity()).isActive())
            PauseButton.initAndGetInstance(getActivity()).ifButtonInactiveThenCallOnclick();
    }

    private void saveDataToSharedPreferences() {
        morseToTextSwappingPanel.saveTranslatingDirectionToSP();
        TextBoxes.saveTextBoxesContentDataToSP(getActivity());
        BroadcastingTextIndexesSharedPreferencesHandle.saveIndexesOfCurrentlyBroadcastingTextToSP(getActivity());
        ControlButtonsSharedPreferences.saveCurrentlyActiveButton(getActivity());
        MorseKeyboardSharedPreferences.saveCurrentlyActiveButton(getActivity());
    }

    @Override
    protected void onDestroy() {
        destroyed = true;
        setObjectsNull();
        super.onDestroy();
    }

    private void setObjectsNull() {
        Log.i("onDestroy()", " set all objects null");
        ConvertMorseToSignals.initAndGetInstance(getActivity()).setNull();
        ChangingTextColors.initAndGetInstance(getActivity()).setNull();

        TopBarSpeedSpinner.initAndGetInstance(getActivity()).setNull();

        PlayButton.initAndGetInstance(getActivity()).setNull();
        PauseButton.initAndGetInstance(getActivity()).setNull();
        StopButton.initAndGetInstance(getActivity()).setNull();

        TextBoxes.setNull();

        ((SpaceButton)SpaceButton.initAndGetInstance(getActivity())).setNull();
        ((DotButton)DotButton.initAndGetInstance(getActivity())).setNull();
        ((LineButton)LineButton.initAndGetInstance(getActivity())).setNull();
        ((BackspaceButton)BackspaceButton.initAndGetInstance(getActivity())).setNull();

        VibrationButton.initAndGetInstance(getActivity()).setNull();
        SoundButton.initAndGetInstance(getActivity()).setNull();
        FlashlightButton.initAndGetInstance(getActivity()).ifNotNullSetNullAndReleaseCamera();
        ScreenButton.initAndGetInstance(getActivity()).setNull();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case FlashlightPermissions.FLASHLIGHT_BUTTON_PERMISSIONS:
                FlashlightPermissions.callOnClickIfPermissionGranted(grantResults, getActivity());
                break;
        }
    }
}
