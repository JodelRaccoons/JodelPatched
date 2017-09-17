package com.jodelapp.jodelandroidv3.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.jodelapp.jodelandroidv3.jp.JPStorage;

import java.util.Map;
import java.util.TreeMap;

import hack.getid.R;
import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexWrap;

@SuppressLint("ValidFragment")
@DexEdit
public class UserSettingsFragment extends JodelFragment implements CompoundButton.OnCheckedChangeListener {

    @DexIgnore
    public UserSettingsFragment(String s) {
        super(s);
    }

    @DexWrap
    public void onViewCreated(View view, Bundle bundle) {
        onViewCreated(view, bundle);
        new MyListener(this, view);
    }

    @DexIgnore
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}

@DexAdd
class MyListener implements DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnClickListener, View.OnClickListener {

    private TreeMap<String, Boolean> betaFeatures;
    private JPStorage jpStorage;
    private String[] featureNames;
    private AlertDialog dialog;

    MyListener(Fragment fragment, View view) {
        jpStorage = new JPStorage();
        betaFeatures = jpStorage.getFeatures();
        featureNames = new String[betaFeatures.size()];
        boolean[] featureStatus = new boolean[betaFeatures.size()];

        int index = 0;
        for (Map.Entry<String, Boolean> keyValue : betaFeatures.entrySet()) {
            featureNames[index] = keyValue.getKey();
            featureStatus[index] = keyValue.getValue();
            Log.i("JodelSettings", featureNames[index]);
            Log.i("JodelSettings", String.valueOf(featureStatus[index]));
            index++;
        }

        //mSelectedItems = new ArrayList();  // Where we track the selected items
        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getActivity());

        // Set the dialog title
        builder.setTitle("Activate beta features")
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(featureNames, featureStatus, this)
                // Set the action buttons
                .setPositiveButton("Save", this)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        dialog = builder.create();

        view.findViewById(R.id.jp_settings_beta_features_button).setOnClickListener(this);
    }

    // setMultiChoiceItems
    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        betaFeatures.put(featureNames[which], isChecked);
    }

    // OK or Cancel
    @Override
    public void onClick(DialogInterface dialog, int which) {
        jpStorage.setFeatures(betaFeatures);
    }

    // Open dialog
    @Override
    public void onClick(View v) {
        dialog.show();
    }
}