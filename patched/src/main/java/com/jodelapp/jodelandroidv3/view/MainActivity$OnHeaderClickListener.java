package com.jodelapp.jodelandroidv3.view;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import static android.view.View.GONE;

/**
 * Created by Admin on 07.12.2017.
 */

public class MainActivity$OnHeaderClickListener implements View.OnClickListener {

    private RelativeLayout headerView;
    private LinearLayout helpLinearLayout;

    public MainActivity$OnHeaderClickListener(RelativeLayout headerView, LinearLayout helpLinearLayout) {
        this.headerView = headerView;
        this.helpLinearLayout = helpLinearLayout;
    }

    @Override
    public void onClick(View v) {
        if (headerView.getVisibility() == GONE) {
            headerView.setVisibility(View.VISIBLE);
            helpLinearLayout.setVisibility(GONE);
        } else {
            headerView.setVisibility(GONE);
            helpLinearLayout.setVisibility(View.VISIBLE);
        }
    }
}
