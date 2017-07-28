package com.hoymm.root.morsecodeconverter._5_FooterPanel;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.hoymm.root.morsecodeconverter.ButtonsTemplate;
import com.hoymm.root.morsecodeconverter.R;
import com.hoymm.root.morsecodeconverter.Singleton;
import com.hoymm.root.morsecodeconverter._2_TextBoxes.TextBoxes;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class ScreenButton extends ButtonsTemplate implements FooterButtonsInterface, Singleton {
    private static ScreenButton instance;

    public static ScreenButton initAndGetInstance(Activity activity){
        if (instance == null)
            instance = new ScreenButton(activity);
        return instance;
    }

    private ScreenButton(Activity activity) {
        super(activity, R.id.screen_button_id);
        setButtonBehavior();
    }

    private void setButtonBehavior() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setActivated(!button.isActivated());
                Toast.makeText(getActivity(),
                        TextBoxes.initAndGetBottomBox(getActivity()).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void start(int time) {

    }

    @Override
    public boolean isPermissionGranted() {
        return true;
    }

    @Override
    public void setNull() {
        instance = null;
    }
}
