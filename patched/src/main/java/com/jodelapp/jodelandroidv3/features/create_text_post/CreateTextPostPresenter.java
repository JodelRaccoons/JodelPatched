package com.jodelapp.jodelandroidv3.features.create_text_post;

import com.jodelapp.jodelandroidv3.utilities.Util;

import lanchon.dexpatcher.annotation.DexAction;
import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;

/**
 * Created by Admin on 06.12.2017.
 */
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
