package com.hoymm.root.morsecodeconverter._2_TextBoxes;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hoymm.root.morsecodeconverter.R;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * File created by Damian Muca - Kaizen on 03.07.17.
 */

public class CopyToClipboardButton {
    private static CopyToClipboardButton copyToClipboardButton = null;
    private ImageButton imageButton;

    public static void setBehavior(final Activity activity) {
        if (copyToClipboardButton == null)
            copyToClipboardButton = new CopyToClipboardButton(activity);
    }

    private CopyToClipboardButton(Activity activity) {
        imageButton = (ImageButton) activity.findViewById(R.id.clipboard_button_id);
        imageButton.setOnClickListener(getCopyToCliboardBehavior(activity));
    }

    private static View.OnClickListener getCopyToCliboardBehavior(final Activity activity) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToastMessage(activity);
                getTextFromBottomBoxToClipboard(activity);
            }
        };
    }

    private static void getTextFromBottomBoxToClipboard(Activity activity) {
        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", TextBoxes.initAndGetBottomBox(activity).getText().toString());
        clipboard.setPrimaryClip(clip);
    }

    private static void showToastMessage(Activity activity) {
        Toast.makeText(activity, activity.getString(R.string.copied_to_clipboard), Toast.LENGTH_SHORT).show();
    }

    public static void setNull(){
        copyToClipboardButton = null;
    }
}
