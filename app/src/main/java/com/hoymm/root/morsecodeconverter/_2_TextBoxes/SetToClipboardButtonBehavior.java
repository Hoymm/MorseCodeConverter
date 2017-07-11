package com.hoymm.root.morsecodeconverter._2_TextBoxes;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hoymm.root.morsecodeconverter.R;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * File created by Damian Muca - Kaizen on 03.07.17.
 */

public class SetToClipboardButtonBehavior {
    private TextView bottomTextBox;
    private Activity activity;

    public SetToClipboardButtonBehavior(Context context) {
        ImageButton copyToClipboardButton = (ImageButton) getActivity().findViewById(R.id.clipboard_button_id);
        activity = (Activity)context;
        copyToClipboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), getActivity().getString(R.string.copied_to_clipboard)
                        , Toast.LENGTH_SHORT).show();

                bottomTextBox = (TextView) getActivity().findViewById(R.id.bottom_text_view_box);
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", bottomTextBox.getText().toString());
                clipboard.setPrimaryClip(clip);
            }
        });
    }

    private Activity getActivity(){
        return activity;
    }
}
