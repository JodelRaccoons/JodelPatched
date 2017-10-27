package com.jodelapp.jodelandroidv3.jp;

import android.animation.Animator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.jodelapp.jodelandroidv3.view.JodelFragment;
import com.tellm.android.app.mod.R;

import java.util.TreeMap;

/**
 * Created by Admin on 24.10.2017.
 */

public class JPFeaturesFragment extends JodelFragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private JPStorage mStorage;
    private LinearLayout mBetaFeaturesContainer;


    public JPFeaturesFragment() {
        super(JPFeaturesFragment.class.getSimpleName());
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View mRootView = layoutInflater.inflate(R.layout.fragment_jp_features, null, false);
        mStorage = new JPStorage();
        mBetaFeaturesContainer = (LinearLayout) mRootView.findViewById(R.id.jp_beta_features_container);

        setupBetaFeatures();

        setupToolBar(mRootView.findViewById(R.id.subfeed_toolbar), "JP Features");

        return mRootView;
    }

    private void setupBetaFeatures() {
        TreeMap<String, Boolean> mFeatures = mStorage.getFeatures();
        for (String mFeatureString : mFeatures.keySet()) {
            LinearLayout mFeatureLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.jp_item_beta_features, null, false);

            TextView mTextView = (TextView) mFeatureLayout.findViewById(R.id.jp_text_view_beta_features_name);
            mTextView.setText(mFeatureString);

            Switch mSwich = (Switch) mFeatureLayout.findViewById(R.id.jp_switch_beta_features);
            mSwich.setChecked(mFeatures.get(mFeatureString));

            TextView mDescription = (TextView) mFeatureLayout.findViewById(R.id.jp_item_beta_tv_description);
            mDescription.setTypeface(mDescription.getTypeface(), Typeface.ITALIC);

            mSwich.setOnCheckedChangeListener(this);
            mFeatureLayout.setOnClickListener(this);
            mBetaFeaturesContainer.addView(mFeatureLayout);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        TreeMap<String, Boolean> mFeatures = mStorage.getFeatures();
        View mRootView = null;
        View mIteratorView = buttonView;
        while (mRootView == null) {
            mIteratorView = (View) mIteratorView.getParent();
            if (mIteratorView.getId() == R.id.beta_features_parent_layout) {
                mRootView = mIteratorView;
            }
        }
        TextView mTextView = (TextView) mRootView.findViewById(R.id.jp_text_view_beta_features_name);
        mFeatures.put(mTextView.getText().toString().trim(), isChecked);
        mStorage.setFeatures(mFeatures);
    }

    @Override
    public void onClick(View v) {
        final TextView mDescription = (TextView) v.findViewById(R.id.jp_item_beta_tv_description);
        if (mDescription.getVisibility() == View.GONE) {
            YoYo.with(Techniques.SlideInDown)
                    .onStart(new YoYo.AnimatorCallback() {
                        @Override
                        public void call(Animator animator) {
                            mDescription.setVisibility(View.VISIBLE);
                        }
                    })
                    .duration(700)
                    .playOn(mDescription);
        } else {
            YoYo.with(Techniques.SlideOutUp)
                    .onEnd(new YoYo.AnimatorCallback() {
                        @Override
                        public void call(Animator animator) {
                            mDescription.setVisibility(View.GONE);
                        }
                    })
                    .duration(700)
                    .playOn(mDescription);
        }
    }
}