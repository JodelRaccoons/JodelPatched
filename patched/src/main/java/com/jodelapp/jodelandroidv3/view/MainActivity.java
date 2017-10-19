package com.jodelapp.jodelandroidv3.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.api.Status;
import com.jodelapp.jodelandroidv3.api.model.ConnectionErrorMessage;
import com.jodelapp.jodelandroidv3.api.model.PushNotification;
import com.jodelapp.jodelandroidv3.jp.JPStorage;
import com.jodelapp.jodelandroidv3.view.activities.mainactivity.MainActivityContract;

import hack.getid.R;
import lanchon.dexpatcher.annotation.DexAction;
import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexWrap;

@DexAdd
class ClickListener implements View.OnLongClickListener {
    MainActivity activity;

    ClickListener(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onLongClick(View v) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setClassName("io.github.krokofant.placepickerproxy",
                "io.github.krokofant.placepickerproxy.MainActivity");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "your title text");
        activity.startActivityForResult(sharingIntent, 108);
        return true;
    }
}

@DexEdit
public class MainActivity extends JodelActivity implements LoaderCallbacks<Void>, MainActivityContract.View {

    @SuppressLint("MissingSuperCall")
    @DexWrap
    protected void onCreate(Bundle bundle) {
        onCreate(bundle);
        View feedTab = findViewById(R.id.feed_tab);
        feedTab.setOnLongClickListener(new ClickListener(this));
    }

    @DexWrap
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == 108) {
            JPStorage storage = new JPStorage();
            storage.isSpoofLocation(true);
            double[] latlng = data.getDoubleArrayExtra("latlng");
            Log.i("JodelPatched", "I got location! woho!");
            storage.setSpoofLocation(latlng[0], latlng[1]);
        } else
            onActivityResult(requestCode, resultCode, data);
    }

    @DexIgnore
    public Loader<Void> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @DexIgnore
    public void onLoadFinished(Loader<Void> loader, Void data) {

    }

    @DexIgnore
    public void onLoaderReset(Loader<Void> loader) {

    }

    @DexIgnore
    public void closeUserProfilingDialog() {

    }

    @DexIgnore
    public void enableUserProfilingHandler() {

    }

    @DexIgnore
    public void getDeeplinkInstallReferrerAndRegister() {

    }

    @DexIgnore
    public void hideChannelsRedDot() {

    }

    @DexIgnore
    public void hideOrangeDotNearKarma() {

    }

    @DexIgnore
    public void hideRedDotNearKarma() {

    }

    @DexIgnore
    public void popupLocationPermissionDialog() {

    }

    @DexIgnore
    public void showAppLocationSettingDialog(Status status) {

    }

    @DexIgnore
    public void showChannelsRedDot() {

    }

    @DexIgnore
    public void showErrorTypeOnTopSnackbar(ConnectionErrorMessage.ErrorType errorType) {

    }

    @DexIgnore
    public void showInAppNotification(PushNotification pushNotification) {

    }

    @DexIgnore
    public void showOrangeDotNearKarma() {

    }

    @DexIgnore
    public void showPhoneVerification() {

    }

    @DexIgnore
    public void showRedDotNearKarma() {

    }

    @DexIgnore
    public void showUserProfiling() {

    }

    @DexIgnore
    public void showUserProfilingDialog() {

    }

    @DexIgnore
    public void updateKarma(String s) {

    }
}
