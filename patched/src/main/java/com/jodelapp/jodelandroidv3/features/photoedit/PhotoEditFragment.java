package com.jodelapp.jodelandroidv3.features.photoedit;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jodelapp.jodelandroidv3.jp.JPUtils;
import com.jodelapp.jodelandroidv3.view.JodelFragment;
import com.tellm.android.app.mod.R;

import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexWrap;

import static android.widget.ImageView.ScaleType.CENTER_CROP;
import static android.widget.ImageView.ScaleType.FIT_CENTER;
import static android.widget.ImageView.ScaleType.FIT_XY;


@DexIgnore
interface PhotoEditContract {

    @DexIgnore
    interface View {
    }
}

// fragment_photo_edit.xml
@DexEdit
public class PhotoEditFragment extends JodelFragment implements TextWatcher, PhotoEditContract.View {
    @DexIgnore
    ImageView picPreview;

    @DexIgnore
    RelativeLayout imageContainer;

    @DexIgnore
    public PhotoEditFragment() {
        super("");
    }

    @DexWrap
    public void onViewCreated(android.view.View view, Bundle bundle) {
        onViewCreated(view, bundle);
        ViewGroup parent1 = (ViewGroup) imageContainer.getParent();
        for (int i = 0; i < parent1.getChildCount(); i++) {
            View child = parent1.getChildAt(i);
            Log.i("Jodel", "child id:" + String.valueOf(child.getId()));
        }
        Log.i("Jodel", "-----" + R.id.toggle_scale_button);
        ViewGroup parent2 = (ViewGroup) parent1.getParent();
        for (int i = 0; i < parent2.getChildCount(); i++) {
            View child = parent2.getChildAt(i);
            Log.i("Jodel", "child id:" + String.valueOf(child.getId()));
        }
        View undoButton = parent2.findViewById(R.id.undo_button);
        int undoButtonIndex = parent2.indexOfChild(undoButton);

        LayoutInflater vi = (LayoutInflater) view.getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.jp_single_image_view, null);

        // Get 38dip
        int width = JPUtils.getDiptoPx(this.getContext(), 38);
        int height = JPUtils.getDiptoPx(this.getContext(), 38);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, -1);
        //params.addRule(RelativeLayout.END_OF, undoButton.getId());
        v.setPadding(
                0, // l
                JPUtils.getDiptoPx(this.getContext(), 52), // t
                JPUtils.getDiptoPx(this.getContext(), 10), // r
                0); // b
        //v.setX(JPUtils.getDiptoPx(this.getContext(), 48));
        v.setOnClickListener(new ScaleListener(picPreview));
        parent2.addView(v, undoButtonIndex, params);
    }

    @DexIgnore
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @DexIgnore
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @DexIgnore
    @Override
    public void afterTextChanged(Editable s) {

    }
}

@DexAdd
class ScaleListener implements View.OnClickListener {
    private ImageView imageView;

    ScaleListener(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public void onClick(View v) {
        switch (imageView.getScaleType()) {
            case CENTER_CROP:
                imageView.setScaleType(FIT_CENTER);
                break;
            case FIT_CENTER:
                imageView.setScaleType(FIT_XY);
                break;
            case FIT_XY:
                imageView.setScaleType(CENTER_CROP);
                break;
        }
    }
}