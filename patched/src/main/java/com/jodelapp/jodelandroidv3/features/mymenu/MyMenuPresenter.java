package com.jodelapp.jodelandroidv3.features.mymenu;

import com.jodelapp.jodelandroidv3.view.MyMenuItem;

import java.util.List;

import lanchon.dexpatcher.annotation.DexAction;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexWrap;

/**
 * *Added entries in the right side menu of Jodel. Clicks are handled in MyMenuViewHolderPresenter
 */
@SuppressWarnings({"unused", "InfiniteRecursion"})
@DexEdit(contentOnly = true, defaultAction = DexAction.IGNORE)
public class MyMenuPresenter {

    @SuppressWarnings({"FinalPrivateMethod"})
    @DexWrap
    private final List<MyMenuItem> generateMenuItem() {
        List<MyMenuItem> myMenuItems = generateMenuItem();
        myMenuItems.add(new MyMenuItem("jp_location", "JP Settings"));
        return myMenuItems;
    }
}
