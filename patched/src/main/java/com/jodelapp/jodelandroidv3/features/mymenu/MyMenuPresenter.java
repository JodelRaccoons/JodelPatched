package com.jodelapp.jodelandroidv3.features.mymenu;

import com.jodelapp.jodelandroidv3.view.MyMenuItem;

import java.util.List;

import lanchon.dexpatcher.annotation.DexAction;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexWrap;

/**
 * *Added entries in the right side menu of Jodel. Clicks are handled in MyMenuViewHolderPresenter
 */

@SuppressWarnings("unused")
@DexEdit(onlyEditMembers = true, defaultAction = DexAction.IGNORE)
public class MyMenuPresenter {

    @SuppressWarnings({"FinalPrivateMethod"})
    @DexWrap
    private final List<MyMenuItem> generateMenuItem() {
        List<MyMenuItem> myMenuItems = generateMenuItem();
        myMenuItems.add(new MyMenuItem("jp_location", "Location"));
        myMenuItems.add(new MyMenuItem("jp_enable_features", "Beta Features"));
        return myMenuItems;
    }

}
