package com.hoymm.root.morsecodeconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hoymm.root.morsecodeconverter.InputOutputFields.ConvertingPaneEditTextBoxes;

public class MainActivity extends AppCompatActivity {
    TopBarSpeedSpinner topBarSpeedSpinner;
    MorseToTextSwappingPanel morseToTextSwappingPanel;
    ConvertingPaneEditTextBoxes convertingPaneEditTextBoxes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeProgramComponents();
        convertingPaneEditTextBoxes.swapConvertingDirection();
    }

    private void initializeProgramComponents() {
        topBarSpeedSpinner = new TopBarSpeedSpinner(this);
        morseToTextSwappingPanel = new MorseToTextSwappingPanel(this);
        convertingPaneEditTextBoxes = new ConvertingPaneEditTextBoxes(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
