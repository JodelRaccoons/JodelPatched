package com.jodelapp.jodelandroidv3.features.create_text_post;

import com.jodelapp.jodelandroidv3.utilities.Util;

import lanchon.dexpatcher.annotation.DexAction;
import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;

/**
 * ? For post color picker
 */
@SuppressWarnings({"WeakerAccess", "unused"})
@DexEdit(defaultAction = DexAction.IGNORE, contentOnly = true)
public class CreateTextPostPresenter {

    String postColor;
    private Util util;


    @DexAdd
    public void setPostColor(String postColor) {
        this.postColor = postColor;
    }

    @DexAdd
    public Util getUtil(){
        return util;
    }

}
