package com.jodelapp.jodelandroidv3.jp;

import android.location.Location;
import android.os.Bundle;
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
    private boolean isMapReady = false;

    public JPSettingsFragment() {
        super(JPSettingsFragment.class.getSimpleName());
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View mRootView = layoutInflater.inflate(R.layout.fragment_jp_settings, null, false);
        ButterKnife.bR(mRootView);

        mMapView = (MapView) mRootView.findViewById(R.id.jp_settings_map_view);
        LinearLayout mBetaFeaturesButton = (LinearLayout) mRootView.findViewById(R.id.jp_ll_beta_features);
        mBetaFeaturesButton.setOnClickListener(this);

        mLatitude = (EditText) mRootView.findViewById(R.id.jp_edit_text_latitude);
        mLongtitude = (EditText) mRootView.findViewById(R.id.jp_edit_text_longtitude);

        Button mSaveCoords = (Button) mRootView.findViewById(R.id.jp_btn_save_coords);
        mSaveCoords.setOnClickListener(this);

        Button mChooseLocation = (Button) mRootView.findViewById(R.id.jp_btn_choose_location);
        mChooseLocation.setOnClickListener(this);

        mLocationToggle = (Switch) mRootView.findViewById(R.id.jp_location_toggle_switch);
        mLocationToggle.setOnCheckedChangeListener(this);

        this.mBundle = bundle;

        setupToolBar(mRootView.findViewById(R.id.subfeed_toolbar), "JodelPatched");
        return mRootView;
    }

    @OnClick()
    public void btnChooseLocation(){

    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        mStorage = new JPStorage();

        mLocationToggle.setChecked(mStorage.isSpoofLocation());

        double[] mLocation = mStorage.getSpoofLocation();
        String mLat = String.valueOf(mLocation[0]);
        String mLng = String.valueOf(mLocation[1]);
        if (mLat.length()>8) {
            mLat = mLat.substring(0,8);
        }
        if (mLat.length()>8) {
            mLng = mLng.substring(0,8);
        }
        mLatitude.setText(mLat);
        mLongtitude.setText(mLng);
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onCreate(mBundle);
        mMapView.a(this);
    }

    private void mapCameraUpdate() {
        if (isMapReady) {
            Location mLocation = JPUtils.getLocation();

            GoogleMap map = mMapView.getMap();

            LatLng mLatLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());

            CameraUpdate cameraUpdate = CameraUpdateFactory.a(mLatLng, 10);

            MarkerOptions mMarkerOptions = new MarkerOptions();
            mMarkerOptions.f(mLatLng);

            if (map != null) {
                map.a(cameraUpdate);
                map.a(mMarkerOptions);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null)
            mMapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMapView != null)
            mMapView.onDestroy();

        isMapReady = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMapView != null)
            mMapView.onPause();

        isMapReady = false;
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
        } else if (v.getId() == R.id.jp_btn_save_coords){
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
                    Toast.makeText(getActivity(), "Saved new location!", Toast.LENGTH_LONG).show();
                    return;
                }
            } else  {
                YoYo.with(Techniques.Shake)
                        .duration(300)
                        .playOn(mLatitude);
                YoYo.with(Techniques.Shake)
                        .duration(300)
                        .playOn(mLongtitude);
                Toast.makeText(getActivity(), "Please enter  valid location", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.jp_location_toggle_switch) {
            mStorage.isSpoofLocation(isChecked);
            if (isChecked) {
                JPUtils.updateJodelLocation();
            } else {
                JPUtils.updateJodelLocation(JPLocationManager.getLocation());
            }
            mapCameraUpdate();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        isMapReady = true;
        googleMap.zm().aR(false);
        mapCameraUpdate();
    }
}
