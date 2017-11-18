package com.jodelapp.jodelandroidv3.features.mymenu.adapter;

import com.jodelapp.jodelandroidv3.jp.JPSettingsFragment;
import com.jodelapp.jodelandroidv3.jp.JPUtils;
import com.jodelapp.jodelandroidv3.view.MainActivity;
import com.jodelapp.jodelandroidv3.view.MyMenuItem;

import lanchon.dexpatcher.annotation.DexAction;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexWrap;

/**
 * This class handles the click on "Settings" in the MyMenuFragment
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@DexEdit(defaultAction = DexAction.IGNORE, contentOnly = true)
public class MyMenuViewHolderPresenter{

    @DexWrap
    public void onItemClicked(MyMenuItem myMenuItem) {
        if (myMenuItem.name.equals("jp_location"))
            JPUtils.addFragmentToContent(MainActivity.staticActivity, new JPSettingsFragment());
        else
            onItemClicked(myMenuItem);
    }
}
