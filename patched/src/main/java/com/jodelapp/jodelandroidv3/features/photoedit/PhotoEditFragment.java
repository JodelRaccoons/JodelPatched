package com.jodelapp.jodelandroidv3.features.photoedit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jodelapp.jodelandroidv3.view.JodelFragment;
import com.jodelapp.jodelandroidv3.view.drawing.ColorImageButton;
import com.jodelapp.jodelandroidv3.view.drawing.DrawingView;
import com.tellm.android.app.mod.R;

import lanchon.dexpatcher.annotation.DexAction;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexWrap;

@DexEdit(contentOnly = true, defaultAction = DexAction.IGNORE)
public class PhotoEditFragment extends JodelFragment{
    @DexIgnore
    ImageView picPreview;
    @DexIgnore
    DrawingView drawingCanvas;
    @DexIgnore
    ColorImageButton paintButton;


    protected PhotoEditFragment(String s) { super(s); }

    @DexWrap
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View rootView = onCreateView(layoutInflater, viewGroup, bundle);
        rootView.findViewById(R.id.ibToggleScale).setOnClickListener(new PhotoEditFragment$OnScaleListener(picPreview));
        rootView.findViewById(R.id.ibPainterPalette).setOnClickListener(new PhotoEditFragment$OnPainterClick(getActivity(), new OnColorChoosenCallbackImpl()));
        rootView.findViewById(R.id.ibStrokeWidth).setOnClickListener(new PhotoEditFragment$OnStokeWidthClick(getActivity(), drawingCanvas));
        return rootView;
    }

    @DexIgnore
    public void onPaintButtonClick() {}


    class OnColorChoosenCallbackImpl implements PhotoEditFragment$OnPainterClick.OnColorChoosenCallback {

        @Override
        public void choosen(int color) {
            picPreview.requestFocus();
            drawingCanvas.setEnabled(true);
            drawingCanvas.setPaintStrokeColor(color);
        }
    }
}