package com.hoymm.root.morsecodeconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter.InputOutputFields.ConvertingTextFieldsPanel;

public class MainActivity extends AppCompatActivity {
    private TopBarSpeedSpinner topBarSpeedSpinner;
    private MorseToTextSwappingPanel morseToTextSwappingPanel;
    private ConvertingTextFieldsPanel convertingTextFieldsPanel;
    private PlayPauseStopButtons playPauseStopButtons;

    private ImageButton swapButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeProgramComponents();
        initializateSwapButtonAction();
        resizeTextBoxesAnimation();
    }

    private void initializeProgramComponents() {
        topBarSpeedSpinner = new TopBarSpeedSpinner(this);
        morseToTextSwappingPanel = new MorseToTextSwappingPanel(this);
        convertingTextFieldsPanel = new ConvertingTextFieldsPanel(this);
        playPauseStopButtons = new PlayPauseStopButtons(this);
    }



    private void initializateSwapButtonAction() {
        swapButton = (ImageButton) findViewById(R.id.swap_button_id);
        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(morseToTextSwappingPanel.rotateArrowAnimation()) {
                    morseToTextSwappingPanel.swapTextsAndTags();
                    morseToTextSwappingPanel.saveToSharedPreferencesReversedTranslationDirection();
                    resizeTextBoxesAnimation();
                }
            }
        });
    }

    private void resizeTextBoxesAnimation() {
        convertingTextFieldsPanel.swapConvertingDirection();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
