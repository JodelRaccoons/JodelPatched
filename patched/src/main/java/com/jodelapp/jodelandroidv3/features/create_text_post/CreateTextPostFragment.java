package com.jodelapp.jodelandroidv3.features.create_text_post;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.jodelapp.jodelandroidv3.JodelApp;
import com.jodelapp.jodelandroidv3.events.PictureTakenEvent;
import com.jodelapp.jodelandroidv3.jp.JPUtils;
import com.jodelapp.jodelandroidv3.jp.TSnackbar;
import com.jodelapp.jodelandroidv3.view.JodelFragment;
import com.jodelapp.jodelandroidv3.view.PostCreationFragment;
import com.tellm.android.app.mod.R;

import lanchon.dexpatcher.annotation.DexAction;
import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexAppend;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexWrap;

import static android.app.Activity.RESULT_OK;
import static android.widget.LinearLayout.HORIZONTAL;
import static android.widget.LinearLayout.VERTICAL;

/**
 * ? For post color picker
 */
@DexEdit(defaultAction = DexAction.IGNORE, contentOnly = true)
public class CreateTextPostFragment extends JodelFragment{

    @DexIgnore
    EditText etPost;
    @DexIgnore
    CreateTextPostContract.Presenter presenter;
    @DexAdd
    PostCreationFragment mPostCreationFragment;

    @DexIgnore
    public CreateTextPostFragment(String s) {
        super(s);
    }

    @DexAdd
    public static View getColorPickerView() {
        Context ctx = JodelApp.staticContext;

        LinearLayout.LayoutParams colorLayoutParams = new LinearLayout.LayoutParams(JPUtils.dpToPx(70), JPUtils.dpToPx(70));
        colorLayoutParams.setMargins(JPUtils.dpToPx(20), JPUtils.dpToPx(20), JPUtils.dpToPx(20), JPUtils.dpToPx(20));

        LinearLayout rootLayout = new LinearLayout(ctx);
        rootLayout.setOrientation(VERTICAL);
        LinearLayout.LayoutParams rootLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        rootLayout.setLayoutParams(rootLayoutParams);

        LinearLayout firstRow = new LinearLayout(ctx);
        firstRow.setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams firstRowLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        firstRowLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        firstRow.setLayoutParams(firstRowLayoutParams);

        ImageView orange = new ImageView(ctx);
        orange.setTag("cp_orange");
        orange.setBackgroundColor(Color.parseColor("#FFFF9908"));
        orange.setLayoutParams(colorLayoutParams);
        firstRow.addView(orange);

        ImageView yellow = new ImageView(ctx);
        yellow.setTag("cp_yellow");
        yellow.setBackgroundColor(Color.parseColor("#FFFFBA00"));
        yellow.setLayoutParams(colorLayoutParams);
        firstRow.addView(yellow);

        ImageView red = new ImageView(ctx);
        red.setTag("cp_red");
        red.setBackgroundColor(Color.parseColor("#FFDD5F5F"));
        red.setLayoutParams(colorLayoutParams);
        firstRow.addView(red);
        rootLayout.addView(firstRow);

        LinearLayout secondRow = new LinearLayout(ctx);
        secondRow.setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams secondRowLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        secondRowLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        secondRow.setLayoutParams(secondRowLayoutParams);

        ImageView blue = new ImageView(ctx);
        blue.setTag("cp_blue");
        blue.setBackgroundColor(Color.parseColor("#FF06A3CB"));
        blue.setLayoutParams(colorLayoutParams);
        secondRow.addView(blue);

        ImageView bluegrayish = new ImageView(ctx);
        bluegrayish.setTag("cp_bluegrayish");
        bluegrayish.setBackgroundColor(Color.parseColor("#FF8ABDB0"));
        bluegrayish.setLayoutParams(colorLayoutParams);
        secondRow.addView(bluegrayish);

        ImageView green = new ImageView(ctx);
        green.setTag("cp_green");
        green.setBackgroundColor(Color.parseColor("#FF9EC41C"));
        green.setLayoutParams(colorLayoutParams);
        secondRow.addView(green);
        rootLayout.addView(secondRow);

        return rootLayout;
    }

    @DexAppend
    private void initViews() {
        JPUtils.enableLongClick(etPost);
    }

    @DexAdd
    public void setPostCreationFragment(PostCreationFragment mPostCreationFragment) {
        this.mPostCreationFragment = mPostCreationFragment;
    }

    @DexWrap
    public android.view.View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View rootView = onCreateView(layoutInflater, viewGroup, bundle);
        ImageView mColorPickerButton = (ImageView) rootView.findViewById(R.id.colorPickerButton);
        mColorPickerButton.setOnClickListener(new OnColorPickerClickListener());

        ImageView mGalleryPickerButton = (ImageView) rootView.findViewById(R.id.galleryPickerButton);
        mGalleryPickerButton.setOnClickListener(new OnGalleryPickerClickListener());

        //Enable pasting
        RelativeLayout scrollContainer = (RelativeLayout) ((ScrollView) rootView.findViewById(R.id.scrollContainer)).getChildAt(0);
        scrollContainer.setClickable(true);
        scrollContainer.setLongClickable(true);
        for (int i = 0; i < scrollContainer.getChildCount(); i++) {
            scrollContainer.getChildAt(i).setClickable(true);
            scrollContainer.getChildAt(i).setLongClickable(true);
        }

        return rootView;
    }

    @DexIgnore
    public void setContainerBackgroundColor(String str) {}

    @DexAdd
    @Override
    public void onActivityResult(int i, int i1, Intent intent) {
        if (i == 90 && i1 == RESULT_OK) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), intent.getData());
                PostCreationFragment.mInstance.handle(new PictureTakenEvent(bitmap));
            } catch (Exception e) {
                TSnackbar.make("Error while picking image: "+e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public class OnGalleryPickerClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent photoPicker = new Intent(Intent.ACTION_PICK);
            photoPicker.setType("image/*");
            CreateTextPostFragment.this.startActivityForResult(photoPicker, 90);
        }
    }

    class OnColorPickerClickListener implements  View.OnClickListener {

        @Override
        public void onClick(View v) {
            View dialoglayout = getColorPickerView();

            AlertDialog.Builder builder = new AlertDialog.Builder(CreateTextPostFragment.this.getActivity());
            builder.setTitle("Pick your desired color");
            builder.setView(dialoglayout);

            AlertDialog alertDialog = builder.create();

            ColorPickerOnClickListener colorPickerOnClickListener = new ColorPickerOnClickListener(alertDialog);

            dialoglayout.findViewWithTag("cp_orange").setOnClickListener(colorPickerOnClickListener);
            dialoglayout.findViewWithTag("cp_yellow").setOnClickListener(colorPickerOnClickListener);
            dialoglayout.findViewWithTag("cp_red").setOnClickListener(colorPickerOnClickListener);
            dialoglayout.findViewWithTag("cp_blue").setOnClickListener(colorPickerOnClickListener);
            dialoglayout.findViewWithTag("cp_bluegrayish").setOnClickListener(colorPickerOnClickListener);
            dialoglayout.findViewWithTag("cp_green").setOnClickListener(colorPickerOnClickListener);

            alertDialog.show();
        }
    }


    @DexAdd
    private class ColorPickerOnClickListener implements View.OnClickListener {
        private AlertDialog alertDialog;

        ColorPickerOnClickListener(AlertDialog alertDialog) {
            this.alertDialog = alertDialog;
        }

        @Override
        public void onClick(View v) {
            CreateTextPostPresenter mPresenter = (CreateTextPostPresenter) presenter;
            
            final String tag = (String) v.getTag();
            switch (tag) {
                case "cp_orange":
                    setContainerBackgroundColor(JPUtils.Colors.Colors.get(0));
                    mPresenter.setPostColor(JPUtils.Colors.Colors.get(0));
                    break;
                case "cp_yellow":
                    setContainerBackgroundColor(JPUtils.Colors.Colors.get(1));
                    mPresenter.setPostColor(JPUtils.Colors.Colors.get(1));
                    break;
                case "cp_red":
                    setContainerBackgroundColor(JPUtils.Colors.Colors.get(2));
                    mPresenter.setPostColor(JPUtils.Colors.Colors.get(2));
                    break;
                case "cp_blue":
                    setContainerBackgroundColor(JPUtils.Colors.Colors.get(3));
                    mPresenter.setPostColor(JPUtils.Colors.Colors.get(3));
                    break;
                case "cp_bluegrayish":
                    setContainerBackgroundColor(JPUtils.Colors.Colors.get(4));
                    mPresenter.setPostColor(JPUtils.Colors.Colors.get(4));
                    break;
                case "cp_green":
                    setContainerBackgroundColor(JPUtils.Colors.Colors.get(5));
                    mPresenter.setPostColor(JPUtils.Colors.Colors.get(5));
                    break;
            }
            alertDialog.dismiss();
        }
    }

}
