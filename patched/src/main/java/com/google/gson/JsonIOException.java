package com.google.gson;

import lanchon.dexpatcher.annotation.DexAction;
import lanchon.dexpatcher.annotation.DexEdit;

/**
 * Created by Admin on 25.04.2018.
 */
@DexEdit(defaultAction = DexAction.IGNORE)
public class JsonIOException extends JsonParseException {
    private static final long serialVersionUID = 1;

    public JsonIOException(String s) {
        super(s);
    }

    public JsonIOException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public JsonIOException(Throwable throwable) {
        super(throwable);
    }
}
