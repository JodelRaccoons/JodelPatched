package com.jodelapp.jodelandroidv3.features.replychatbox;

import com.jodelapp.jodelandroidv3.jp.JPUtils;
import com.pixplicity.fontview.FontEditText;

import lanchon.dexpatcher.annotation.DexAppend;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;

/**
 * ? For copy/paste
 */
@DexEdit(contentOnly = true)
public class ReplyChatBox {

    @DexIgnore
    public FontEditText etReply;

    @DexAppend
    private void init() {
        JPUtils.enableLongClick(etReply);
    }
}