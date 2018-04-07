package com.jodelapp.jodelandroidv3.features.mymenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jodelapp.jodelandroidv3.analytics.state.EntryPoint;
import com.jodelapp.jodelandroidv3.view.JodelFragment;
import com.tellm.android.app.mod.R;

import lanchon.dexpatcher.annotation.DexAction;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexWrap;

/**
 * Created by Admin on 07.04.2018.
 */
@DexEdit(contentOnly = true, defaultAction = DexAction.IGNORE)
public class MyMenuFragment extends JodelFragment {

    @DexIgnore
    protected MyMenuFragment(EntryPoint entryPoint) {
        super(entryPoint);
    }

    @DexWrap
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View rootView = onCreateView(layoutInflater, viewGroup, bundle);
        View inviteView = rootView.findViewById(R.id.inviteGameContainer);
        inviteView.setVisibility(View.GONE);
        return rootView;
    }
}
