package com.play;


import com.ngan.play.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;
import android.util.Log;

public class PlayPreferenceActivity extends PreferenceActivity{
	private static final int DIALOG_READ_ME = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        // Handle read me
        Preference preference = findPreference(getText(R.string.preference_key_read_me));
        preference.setOnPreferenceClickListener(new OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference) {
                showDialog(DIALOG_READ_ME);
                return true;
            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;

        switch (id) {
            case DIALOG_READ_ME:
                dialog = createReadMeDialog();
                break;
            default:
                Log.w(PlayExtensionsService.LOG_TAG, "Not a valid dialog id: " + id);
                break;
        }

        return dialog;
    }

    /**
     * Create the Read me dialog
     *
     * @return the Dialog
     */
    private Dialog createReadMeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Read me")
                .setTitle("Read Me")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton(android.R.string.ok, new OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

}
