package com.hoymm.root.morsecodeconverter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter.InputOutputFields.ConvertingTextBoxesPanel;
import com.hoymm.root.morsecodeconverter.MorseKeyboard.MorseKeyboardPanel;
import com.hoymm.root.morsecodeconverter.MorseToTextConversion.ConvertingMorseRunningThread;

public class MainActivity extends AppCompatActivity {
    private TopBarSpeedSpinner topBarSpeedSpinner;
    private MorseToTextSwappingPanel morseToTextSwappingPanel;
    private ConvertingTextBoxesPanel convertingTextFieldsPanel;
    private PlayPauseStopButtons playPauseStopButtons;
    private MorseKeyboardPanel morseKeyboardPanel;
    private FooterPanel footerPanel;
    private ConvertingMorseRunningThread convertingMorseRunningThread;

    private ImageButton swapButton;

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
        convertingTextFieldsPanel = new ConvertingTextBoxesPanel(this);
        playPauseStopButtons = new PlayPauseStopButtons(this);
        morseKeyboardPanel = new MorseKeyboardPanel(this);
        footerPanel = new FooterPanel(this);
        convertingMorseRunningThread = ConvertingMorseRunningThread.getInstance(this);
    }

    private void initializateSwapButtonAction() {
        swapButton = (ImageButton) findViewById(R.id.swap_button_id);
        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (convertingTextFieldsPanel.ifNoAnimationCurrentlyRunning()) {

                    MorseToTextSwappingPanel.isConvertingTextToMorse = !MorseToTextSwappingPanel.isConvertingTextToMorse;
                    refreshAppLastStateAppearance();
                    convertingTextFieldsPanel.swapTextInsideBoxesAnimation();
                    saveInfo_SharedPreferences();
                }
            }
        });
    }

    private void refreshAppLastStateAppearance() {

        hideSystemKeyboard(getActivity());
        morseToTextSwappingPanel.rotateArrowAnimation();
        morseToTextSwappingPanel.swapTextHeaders();
        convertingTextFieldsPanel.resizeBoxesAnimation();
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
        refreshAppLastStateAppearance();
        convertingMorseRunningThread.start(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        convertingMorseRunningThread.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
