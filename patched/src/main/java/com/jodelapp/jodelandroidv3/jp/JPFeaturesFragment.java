package com.jodelapp.jodelandroidv3.jp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.jodelapp.jodelandroidv3.analytics.state.EntryPoint;
import com.jodelapp.jodelandroidv3.view.JodelFragment;
import com.tellm.android.app.mod.R;

import java.util.Map;
import java.util.TreeMap;

public class JPFeaturesFragment extends JodelFragment implements CompoundButton.OnCheckedChangeListener {

    private JPStorage mStorage = new JPStorage();
    private LinearLayout mBetaFeaturesContainer;


    public JPFeaturesFragment() {
        super(EntryPoint.UserSettings);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        @SuppressLint("InflateParams")
        View mRootView = layoutInflater.inflate(R.layout.fragment_jp_features, null, false);
        mBetaFeaturesContainer = mRootView.findViewById(R.id.jp_beta_features_container);

        setupBetaFeatures();

        setupToolBar(mRootView.findViewById(R.id.subfeed_toolbar), "JP Features");

        return mRootView;
    }

    private void setupBetaFeatures() {
        TreeMap<String, Boolean> mFeatures = mStorage.getFeatures();
        Map<String,String> mNames = JPUtils.getHashMapResource(getContext(), R.xml.map_feature_names);
        for (String mFeatureString : mFeatures.keySet()) {
            @SuppressLint("InflateParams")
            LinearLayout mFeatureLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.jp_item_beta_features, null, false);

            TextView mTextView = mFeatureLayout.findViewById(R.id.jp_text_view_beta_features_name);
            if (mNames != null) {
                mTextView.setText(mNames.get(mFeatureString));
            }
            mTextView.setHint(mFeatureString);
            System.out.println("FeatureName: " + mFeatureString + " Title: " + (mNames != null ? mNames.get(mFeatureString) : null));

            Switch mSwich = mFeatureLayout.findViewById(R.id.jp_switch_beta_features);
            mSwich.setChecked(mFeatures.get(mFeatureString));

            mSwich.setOnCheckedChangeListener(this);
            mBetaFeaturesContainer.addView(mFeatureLayout);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        TreeMap<String, Boolean> mFeatures = mStorage.getFeatures();
        LinearLayout mParent = (LinearLayout)buttonView.getParent();
        TextView mChild = (TextView) mParent.getChildAt(0);
        System.out.println(mChild.getHint()+": "+isChecked);
        mFeatures.put(mChild.getHint().toString().trim(), isChecked);
        mStorage.setFeatures(mFeatures);
    }
}
