package com.hoymm.root.morsecodeconverter._1_TopBar;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.hoymm.root.morsecodeconverter.R;

/**
 * Created by root on 05.05.17.
 */

public class TopBarSpeedSpinner extends Spinner {
    private static TopBarSpeedSpinner instance = null;
    private Spinner speedSpinner;
    private Activity activity;

    public static TopBarSpeedSpinner initAndGetInstance(Activity activity){
        if (instance == null)
            instance = new TopBarSpeedSpinner(activity);
        return instance;
    }

    private TopBarSpeedSpinner(Activity activity) {
        super(activity);
        this.activity = activity;
        initAndSetAdapterAndSetSelectedItemOfSpinner();
    }

    private void initAndSetAdapterAndSetSelectedItemOfSpinner() {
        initSpinner();
        setCustomAdapter();
        setOnItemClickBehavior();
        restoreLastSelectedValue(getLastSpeedFromSharedPreferences());
    }

    private void initSpinner() {
        speedSpinner = (Spinner) ((Activity)this.getContext()).findViewById(R.id.speed_spinner_id);
    }

    private void setCustomAdapter() {
        String [] spinnerValuesArray = getSpinnerValues();
        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<>(this.getContext(), R.layout.speed_spinner_custom_view, spinnerValuesArray);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        speedSpinner.setAdapter(spinnerAdapter);
    }

    @NonNull
    private String[] getSpinnerValues() {
        return this.getContext().getResources().getStringArray(R.array.speedsIntervals);
    }

    private void setOnItemClickBehavior() {
        speedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                saveSpeedSelectedToSharedPreferences(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void saveSpeedSelectedToSharedPreferences(String newSpeed) {
        SharedPreferences sharedPref = ((Activity)this.getContext()).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat(getConversionSpeedKey(), Float.parseFloat(newSpeed));
        editor.apply();
    }

    private String getConversionSpeedKey(){
        return this.getContext().getString(R.string.conversionSpeedKeySP);
    }

    private void restoreLastSelectedValue(float lastSelectedValue) {
        String [] spinnerValues = getSpinnerValues();
        for (int i = 0 ; i < spinnerValues.length; ++i){
            if (ifValuesAreQual(spinnerValues[i], lastSelectedValue)){
                speedSpinner.setSelection(i);
                break;
            }
        }
    }

    private boolean ifValuesAreQual(String stringValue, float floatValue) {
        return Float.parseFloat(stringValue) == floatValue;
    }

    public float getLastSpeedFromSharedPreferences() {
        SharedPreferences sharedPref = (getActivity()).getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getFloat(getConversionSpeedKey(), 1.0f);
    }

    private Activity getActivity(){
        return activity;
    }

    public static void setNull() {
        instance = null;
    }
}
