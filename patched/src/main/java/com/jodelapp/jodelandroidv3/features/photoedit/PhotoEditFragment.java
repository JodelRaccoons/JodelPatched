package com.jodelapp.jodelandroidv3.features.photoedit;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jodelapp.jodelandroidv3.analytics.state.EntryPoint;
import com.jodelapp.jodelandroidv3.features.posting_to_channel.PostingToChannelFragment;
import com.jodelapp.jodelandroidv3.jp.JPStorage;
import com.jodelapp.jodelandroidv3.jp.JPUtils;
import com.jodelapp.jodelandroidv3.view.JodelFragment;
import com.jodelapp.jodelandroidv3.view.drawing.DrawingView;
import com.tellm.android.app.mod.R;

import lanchon.dexpatcher.annotation.DexAction;
import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexWrap;

/**
 * ? For enhanced photo editing
 */
@SuppressLint("ValidFragment")
@SuppressWarnings("InfiniteRecursion")
@DexEdit(defaultAction = DexAction.IGNORE)
public class PhotoEditFragment extends JodelFragment implements PhotoEditFragment$OnPainterClick.OnColorChoosenCallback, TextWatcher, PhotoEditContract.View  {
    @DexIgnore
    ImageView picPreview;
    @DexIgnore
    DrawingView drawingCanvas;
    @DexAdd
    View mPhotoEditTools;

    @SuppressLint("ValidFragment")
    @DexIgnore
    protected PhotoEditFragment(String s) { super(EntryPoint.PhotoEdit); }

    @DexWrap
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup rootView = (ViewGroup) onCreateView(layoutInflater, viewGroup, bundle);

        ((ImageView)rootView.findViewById(R.id.image_edit_preview)).setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        //adjust the padding of the geofilter button if enabled and inflate depending on activated geofilter
        JPStorage mStorage = new JPStorage();
        if (mStorage.getFeatures().get("picture_geo_filter")) {
            rootView.findViewById(R.id.geo_filter_switch).setPadding(JPUtils.dpToPx(36), JPUtils.dpToPx(24), JPUtils.dpToPx(17), JPUtils.dpToPx(17));
            mPhotoEditTools = layoutInflater.inflate(R.layout.jp_photo_edit_tools_geofilter, rootView, true);
        } else {
            mPhotoEditTools = layoutInflater.inflate(R.layout.jp_photo_edit_tools_undo, rootView, true);
        }

        //And set onClickListeners
        mPhotoEditTools.findViewById(R.id.ibScaleImage).setOnClickListener(new PhotoEditFragment$OnScaleListener(picPreview));
        mPhotoEditTools.findViewById(R.id.ibPainterPalette).setOnClickListener(new PhotoEditFragment$OnPainterClick(getActivity(), this));
        mPhotoEditTools.findViewById(R.id.ibStrokeWidth).setOnClickListener(new PhotoEditFragment$OnStokeWidthClick(getActivity(), drawingCanvas));

        return rootView;
    }

    @DexWrap
    public void onPaintButtonClick() {
        onPaintButtonClick();
        mPhotoEditTools.findViewById(R.id.ibPainterPalette).setVisibility(View.VISIBLE);
        mPhotoEditTools.findViewById(R.id.ibStrokeWidth).setVisibility(View.VISIBLE);
    }

    @DexWrap
    public void onTextButtonClick() {
        onTextButtonClick();
        mPhotoEditTools.findViewById(R.id.ibPainterPalette).setVisibility(View.GONE);
        mPhotoEditTools.findViewById(R.id.ibStrokeWidth).setVisibility(View.GONE);
    }

    @DexAdd
    @Override
    public void chosen(int color) {
        picPreview.requestFocus();
        drawingCanvas.setEnabled(true);
        drawingCanvas.setPaintStrokeColor(color);
    }

    /* Ignore this methods, they are just for programmatical reasons implemented...*/

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void afterTextChanged(Editable editable) {}

    @Override
    public void enableFilter(String s) {}

    @Override
    public void goBack() {}

    @Override
    public void goToChannelSelectionView(PostingToChannelFragment postingToChannelFragment) {}

    @Override
    public void hideFilter() {}

    @Override
    public void hideTextOnPhoto() {}

    @Override
    public void prepareImage(String s) {}

    @Override
    public void setNextButton() {}

    @Override
    public void setupFilterGestureListener() {}

    @Override
    public void setupPicPreviewTouchListener() {}

    @Override
    public void showFileError() {}

    @Override
    public void showFilter() {}

    @Override
    public void showProgressBar(boolean b) {}
}