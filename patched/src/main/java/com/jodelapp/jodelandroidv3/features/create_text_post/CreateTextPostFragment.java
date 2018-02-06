package com.jodelapp.jodelandroidv3.features.create_text_post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.jodelapp.jodelandroidv3.jp.JPUtils;
import com.jodelapp.jodelandroidv3.view.JodelFragment;
import com.tellm.android.app.mod.R;

import lanchon.dexpatcher.annotation.DexAction;
import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexWrap;

/**
 * Created by Admin on 06.12.2017.
 */
@DexEdit(defaultAction = DexAction.IGNORE, contentOnly = true)
public class CreateTextPostFragment extends JodelFragment {

    @DexIgnore
    EditText etPost;
    @DexIgnore
    CreateTextPostContract.Presenter presenter;
    @DexAdd
    public static CreateTextPostFragment mInstance;

    @DexAdd
    public CreateTextPostFragment$Views getViews() {
        return mViews;
    }

    @DexAdd
    private CreateTextPostFragment$Views mViews;

    @DexIgnore
    public CreateTextPostFragment(String s) { super(s); }

    @DexWrap
    public android.view.View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View rootView = onCreateView(layoutInflater, viewGroup, bundle);
        mInstance = this;

        mViews = new CreateTextPostFragment$Views();
        mViews.initiateViews(rootView);

        rootView.findViewWithTag("colorPickerButton")
                .setOnClickListener(new CreateTextPostFragment$OnColorButtonClickListener(this));
        rootView.findViewWithTag("galleryPickerButton")
                .setOnClickListener(new CreateTextPostFragment$OnGalleryPickerClickListener());

        enablePasting(rootView);

        JPUtils.enableLongClick(etPost);

        return rootView;
    }

    @DexAdd
    private void enablePasting(View rootView) {
        LinearLayout scrollContainer = (LinearLayout) ((ScrollView) rootView.findViewById(R.id.scrollContainer)).getChildAt(0);
        scrollContainer.setClickable(true);
        scrollContainer.setLongClickable(true);
        for (int i = 0; i < scrollContainer.getChildCount(); i++) {
            scrollContainer.getChildAt(i).setClickable(true);
            scrollContainer.getChildAt(i).setLongClickable(true);
        }
    }

    @DexIgnore
    public void setContainerBackgroundColor(String str) {}
}
