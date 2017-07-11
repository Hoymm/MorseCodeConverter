package com.hoymm.root.morsecodeconverter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter._1_Header.MorseToTextSwappingPanelConversion;
import com.hoymm.root.morsecodeconverter._1_Header.TopBarSpeedSpinner;
import com.hoymm.root.morsecodeconverter._2_ApplicationBody.ControlPanelButtons.PauseButton;
import com.hoymm.root.morsecodeconverter._2_ApplicationBody.ControlPanelButtons.PlayButton;
import com.hoymm.root.morsecodeconverter._2_ApplicationBody.ControlPanelButtons.StopButton;
import com.hoymm.root.morsecodeconverter._2_ApplicationBody.PlayPauseStopButtons;
import com.hoymm.root.morsecodeconverter._2_ApplicationBody.SetToClipboardButtonBehavior;
import com.hoymm.root.morsecodeconverter._4_FooterPanel.FlashlightButtons;
import com.hoymm.root.morsecodeconverter._4_FooterPanel.ScreenButtons;
import com.hoymm.root.morsecodeconverter._4_FooterPanel.SoundButtons;
import com.hoymm.root.morsecodeconverter._4_FooterPanel.VibrationButtons;
import com.hoymm.root.morsecodeconverter._2_ApplicationBody.ConvertingTextBoxesPanel;
import com.hoymm.root.morsecodeconverter._3_MorseKeyboard.MorseKeyboardPanelAndDisableSoftKeyboard;
import com.hoymm.root.morsecodeconverter._1_Header.MorseToTextConversion.ConvertingMorseTextProgram;

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
        topBarSpeedSpinner = new TopBarSpeedSpinner(this);
        morseToTextSwappingPanel = new MorseToTextSwappingPanelConversion(this);
        convertingTextBoxesPanel = new ConvertingTextBoxesPanel(this);
        convertingMorseTextProgram = new ConvertingMorseTextProgram(this);
        initializateControlPanelButtons();
        morseKeyboardPanelAndDisableSoftKeyboard = new MorseKeyboardPanelAndDisableSoftKeyboard(this);
        initializateFooterButtons();
    }

    private void initializateControlPanelButtons() {
        PlayPauseStopButtons.initAndGetInstance(this);
        PlayButton.initAndGetInstance(this);
        PauseButton.initAndGetInstance(this);
        StopButton.initAndGetInstance(this);
    }

    private void initializateFooterButtons() {
        VibrationButtons.initializateAndGetInstance(this);
        SoundButtons.initializateAndGetInstance(this);
        FlashlightButtons.initializateAndGetInstance(this);
        ScreenButtons.initializateAndGetInstance(this);
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
