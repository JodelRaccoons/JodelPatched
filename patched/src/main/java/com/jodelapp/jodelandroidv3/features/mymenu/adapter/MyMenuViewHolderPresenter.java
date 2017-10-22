package com.jodelapp.jodelandroidv3.features.mymenu.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.jodelapp.jodelandroidv3.features.mymenu.adapter.MyMenuViewHolderContract.Presenter;
import com.jodelapp.jodelandroidv3.jp.JPStorage;
import com.jodelapp.jodelandroidv3.jp.JPUtils;
import com.jodelapp.jodelandroidv3.view.MainActivity;
import com.jodelapp.jodelandroidv3.view.MyMenuItem;

import java.util.Map;
import java.util.TreeMap;

import lanchon.dexpatcher.annotation.DexAction;
import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexWrap;

/**
 * Used the dialog which was in the UserSettingsFragment, changed the listener in a method and implemented the missing
 * interfaces in here.
 *
 * PlacePicker: Starts the PlacePicker intent using the staticActivity from the MainActivity.
 * Callbacks are handeled within the onActivityResult method of the MainActivity
 * Confirmed working: 22.10.2017
 * Known bugs: Within the PlacePicker apk. Apk crashes when pressing back while no place has been chosen
 *
 * FeaturesSelector:
 * Just showing your dialog, copied most of the code, just rearranged some stuff.
 * Confirmed working: 22.10.2017
 * Known bugs: none
 */
@SuppressWarnings("unused")
@DexEdit(defaultAction = DexAction.IGNORE)
public class MyMenuViewHolderPresenter implements DialogInterface.OnClickListener, DialogInterface.OnMultiChoiceClickListener, Presenter{

    @DexAdd
    private TreeMap<String, Boolean> betaFeatures;
    @DexAdd
    private JPStorage jpStorage;
    @DexAdd
    private String[] featureNames;
    @DexAdd
    private AlertDialog dialog;

    @DexWrap
    public void onItemClicked(MyMenuItem myMenuItem) {
        createFeatureDialog();
        onItemClicked(myMenuItem);
        switch (myMenuItem.name) {
            case "jp_location":
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setClassName("io.github.krokofant.placepickerproxy", "io.github.krokofant.placepickerproxy.MainActivity");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "your title text");
                MainActivity.staticActivity.startActivityForResult(sharingIntent, 108);
                break;
            case "jp_enable_features":
                dialog.show();
                break;
        }
    }

    @Override
    public void onViewAttached(MyMenuItem myMenuItem) {}

    @Override
    public void onViewDetached() {}

    @Override
    public void onViewHolderBind(MyMenuItem myMenuItem) {}

    @DexAdd
    private void createFeatureDialog() {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.staticActivity);

        // Set the dialog title
        builder.setTitle("Activate beta features")
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(featureNames, featureStatus, this)
                // Set the action buttons
                .setPositiveButton("Save", this)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {@Override public void onClick(DialogInterface dialog, int which) {}});

        dialog = builder.create();
    }

    @DexAdd
    @Override
    public void onClick(DialogInterface dialog, int which) {
        jpStorage.setFeatures(betaFeatures);
        JPUtils.showMessageSnackbar("Saved new features!");
    }

    @DexAdd
    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        betaFeatures.put(featureNames[which], isChecked);
    }
}
