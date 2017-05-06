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

public class TopBarSpeedSpinner {
    private static TopBarSpeedSpinner convertSpeedSpinner;

    private Context myContext;
    private Spinner speedSpinner;

    public static TopBarSpeedSpinner initialize(Context context){
        if(convertSpeedSpinner == null)
            convertSpeedSpinner = new TopBarSpeedSpinner(context);
        return convertSpeedSpinner;
    }

    private TopBarSpeedSpinner(Context context) {
        myContext = context;
        initAndSetAdapterAndSetSelectedItemOfSpinner();
    }

    private void initAndSetAdapterAndSetSelectedItemOfSpinner() {
        initSpinner();
        setCustomAdapter();
        setOnItemClickBehavior();
        setLastSelectedValue(getSpeedFromSharedPreferences());
    }

    private void initSpinner() {
        speedSpinner = (Spinner) ((Activity)getContext()).findViewById(R.id.speed_spinner_id);
    }

    private Context getContext(){
        return myContext;
    }

    private void setCustomAdapter() {
        String [] spinnerValuesArray = getSpinnerValues();
        ArrayAdapter<String> spinnerAdapter = 
                new ArrayAdapter<>(getContext(), R.layout.speed_spinner_custom_view, spinnerValuesArray);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        speedSpinner.setAdapter(spinnerAdapter);
    }

    @NonNull
    private String[] getSpinnerValues() {
        return getContext().getResources().getStringArray(R.array.speedsIntervals);
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
        SharedPreferences sharedPref = ((Activity)getContext()).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat(getConversionSpeedKey(), Float.parseFloat(newSpeed));
        editor.apply();
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

    private boolean ifValuesAreQual(String stringValue, float floatValue) {
        return Float.parseFloat(stringValue) == floatValue;
    }

    private float getSpeedFromSharedPreferences() {
        SharedPreferences sharedPref = ((Activity)myContext).getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getFloat(getConversionSpeedKey(), 1.0f);
    }
}
