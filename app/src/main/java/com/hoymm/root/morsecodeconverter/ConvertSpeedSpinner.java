package com.hoymm.root.morsecodeconverter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by root on 05.05.17.
 */

public class ConvertSpeedSpinner {
    private Context myContext;
    private Spinner speedSpinner;

    public ConvertSpeedSpinner(Context context) {
        myContext = context;
        initSpinnerAndCustomizeAndSetSelectedItem();
    }

    private Context getContext(){
        return myContext;
    }

    private void initSpinnerAndCustomizeAndSetSelectedItem() {
        initSpinner();
        setCustomAdapter();
        setOnItemClickBehavior();
        setLastSelectedValue(getLastSpeedFromSharedPreferences());
    }

    private void initSpinner() {
        speedSpinner = (Spinner) ((Activity)getContext()).findViewById(R.id.speed_spinner_id);
    }

    private void setCustomAdapter() {
        String [] spinnerValuesArray = getSpinnerValues();
        ArrayAdapter<String> spinnerAdapter = 
                new ArrayAdapter<>(getContext(), R.layout.speed_spinner_custom_view, spinnerValuesArray);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        speedSpinner.setAdapter(spinnerAdapter);
    }

    private void setOnItemClickBehavior() {
        speedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                saveSpeedSelectedToSharedPreferences(parent.getItemAtPosition(position).toString());
            }

            private void saveSpeedSelectedToSharedPreferences(String newSpeed) {
                SharedPreferences sharedPref = ((Activity)getContext()).getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putFloat(getConversionSpeedKey(), Float.parseFloat(newSpeed));
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private String getConversionSpeedKey(){
        return getContext().getString(R.string.conversionSpeedKey);
    }

    private void setLastSelectedValue(float lastSelectedValue) {
        String [] spinnerValues = getSpinnerValues();
        for (int i = 0 ; i < spinnerValues.length; ++i){
            if (ifValuesAreQual(spinnerValues[i], lastSelectedValue)){
                speedSpinner.setSelection(i);
                break;
            }
        }
    }

    @NonNull
    private String[] getSpinnerValues() {
        return getContext().getResources().getStringArray(R.array.speedsIntervals);
    }

    private boolean ifValuesAreQual(String stringValue, float floatValue) {
        return Float.parseFloat(stringValue) == floatValue;
    }

    private float getLastSpeedFromSharedPreferences() {
        SharedPreferences sharedPref = ((Activity)myContext).getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getFloat(getConversionSpeedKey(), 1.0f);
    }
}
