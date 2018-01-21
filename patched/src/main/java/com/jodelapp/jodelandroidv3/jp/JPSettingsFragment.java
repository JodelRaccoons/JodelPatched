package com.jodelapp.jodelandroidv3.jp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jodelapp.jodelandroidv3.view.JodelFragment;
import com.jodelapp.jodelandroidv3.view.MainActivity;
import com.tellm.android.app.mod.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jodelapp.jodelandroidv3.jp.JPUtils.isValidDouble;
import static com.jodelapp.jodelandroidv3.jp.JPUtils.isValidLatLng;

/**
 * Created by Admin on 22.10.2017.
 */

public class JPSettingsFragment extends JodelFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, OnMapReadyCallback {

    private MapView mMapView;
    private JPStorage mStorage;
    private EditText mLatitude;
    private EditText mLongtitude;
    private Bundle mBundle;
    private Switch mLocationToggle;

    public JPSettingsFragment() {
        super(JPSettingsFragment.class.getSimpleName());
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View mRootView = layoutInflater.inflate(R.layout.fragment_jp_settings, null, false);

        mMapView = mRootView.findViewById(R.id.jp_settings_map_view);
        mMapView.onCreate(mBundle);
        mMapView.a(this);
        LinearLayout mBetaFeaturesButton = mRootView.findViewById(R.id.jp_ll_beta_features);
        mBetaFeaturesButton.setOnClickListener(this);

        mLatitude = mRootView.findViewById(R.id.jp_edit_text_latitude);
        mLongtitude = mRootView.findViewById(R.id.jp_edit_text_longtitude);

        Button mSaveCoords = mRootView.findViewById(R.id.jp_btn_save_coords);
        mSaveCoords.setOnClickListener(this);

        Button mChooseLocation = mRootView.findViewById(R.id.jp_btn_choose_location);
        mChooseLocation.setOnClickListener(this);

        mLocationToggle = mRootView.findViewById(R.id.jp_location_toggle_switch);
        mLocationToggle.setOnCheckedChangeListener(this);

        this.mBundle = bundle;

        setupToolBar(mRootView.findViewById(R.id.subfeed_toolbar), "JodelPatched");
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        mStorage = new JPStorage();

        mLocationToggle.setChecked(mStorage.isSpoofLocation());

        double[] mLocation = mStorage.getSpoofLocation();
        String mLat = String.valueOf(mLocation[0]);
        String mLng = String.valueOf(mLocation[1]);
        if (mLat.length() > 8) {
            mLat = mLat.substring(0, 8);
        }
        if (mLat.length() > 8) {
            mLng = mLng.substring(0, 8);
        }
        mLatitude.setText(mLat);
        mLongtitude.setText(mLng);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void mapCameraUpdate(){
        mapCameraUpdate(mMapView.getMap());
    }

    private void mapCameraUpdate(GoogleMap map) {
        Location mLocation = JPUtils.getLocation();

        if (mLocation != null) {
            LatLng mLatLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());

            try {
                CameraUpdate cameraUpdate = CameraUpdateFactory.a(mLatLng, 10);

                MarkerOptions mMarkerOptions = new MarkerOptions();
                mMarkerOptions.f(mLatLng);

                if (map != null) {
                    map.a(cameraUpdate);
                    map.a(mMarkerOptions);
                } else {
                    Log.d(getClass().getSimpleName(),"GoogleMap is null");
                }
            } catch (NullPointerException e) {
                Log.d(getClass().getSimpleName(),e.getMessage());
            }
        } else {
            TSnackbar.make("We cannot find your location...");
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null)
            mMapView.onResume();


        double[] mCoords = mStorage.getSpoofLocation();
        mLatitude.setText(String.valueOf(mCoords[0]));
        mLongtitude.setText(String.valueOf(mCoords[1]));

        mapCameraUpdate();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMapView != null)
            mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMapView != null)
            mMapView.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMapView != null)
            mMapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mMapView != null)
            mMapView.onLowMemory();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.jp_ll_beta_features) {
            JPUtils.addFragmentToContent(getActivity(), new JPFeaturesFragment());
        } else if (v.getId() == R.id.jp_btn_choose_location) {
            try {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setClassName("io.github.krokofant.placepickerproxy",
                        "io.github.krokofant.placepickerproxy.MainActivity");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "your title text");
                MainActivity.staticActivity.startActivityForResult(sharingIntent, 108);
            } catch (ActivityNotFoundException e) {
                TSnackbar.make("Please install the JodelTools first!");
            }
        } else if (v.getId() == R.id.jp_btn_save_coords) {
            double[] mNewLocation = new double[2];
            String mLatitudeString = mLatitude.getText().toString();
            String mLongtitudeString = mLongtitude.getText().toString();

            if (isValidDouble(mLatitudeString) && isValidDouble(mLongtitudeString)) {
                mNewLocation[0] = Location.convert(mLatitudeString);
                mNewLocation[1] = Location.convert(mLongtitudeString);

                if (isValidLatLng(mNewLocation[0], mNewLocation[1])) {
                    mStorage.setSpoofLocation(mNewLocation[0], mNewLocation[1]);

                    JPUtils.updateJodelLocation();
                    mapCameraUpdate();
                    TSnackbar.make("Saved new location!", TSnackbar.LENGTH_SHORT);
                }
            } else {
                YoYo.with(Techniques.Shake)
                        .duration(300)
                        .playOn(mLatitude);
                YoYo.with(Techniques.Shake)
                        .duration(300)
                        .playOn(mLongtitude);
                TSnackbar.make("Please enter  valid location");
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.jp_location_toggle_switch) {
            if (mStorage.getSpoofLocation()[0] != 0 && mStorage.getSpoofLocation()[1] != 0) {
                mStorage.isSpoofLocation(isChecked);
                JPUtils.updateJodelLocation();
                mapCameraUpdate();
            } else {
                buttonView.setChecked(false);
                YoYo.with(Techniques.Shake)
                        .duration(300)
                        .playOn(mLatitude);
                YoYo.with(Techniques.Shake)
                        .duration(300)
                        .playOn(mLongtitude);
                TSnackbar.make("Please choose a location first!");
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // TODO: add dynamic get for methods
        googleMap.Bd().bd(false);
        mapCameraUpdate(googleMap);
    }
}
