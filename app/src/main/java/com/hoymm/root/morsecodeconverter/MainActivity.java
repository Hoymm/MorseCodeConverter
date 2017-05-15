package com.hoymm.root.morsecodeconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    TopBarSpeedSpinner topBarSpeedSpinner;
    MorseToTextSwappingPanel morseToTextSwappingPanel;
    MiddleTextBoxes middleTextBoxes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeProgramComponents();
    }

    private void initializeProgramComponents() {
        topBarSpeedSpinner = new TopBarSpeedSpinner(this);
        morseToTextSwappingPanel = new MorseToTextSwappingPanel(this);
        middleTextBoxes = new MiddleTextBoxes(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
