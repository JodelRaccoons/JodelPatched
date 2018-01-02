package okhttp3;

import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;

/**
 * ? For debugging
 */
@DexEdit
public final class Headers {
    @DexAdd
    public String[] namesAndValues() {
        try {
            return (String[]) this.getClass().getDeclaredFields()[0].get(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return new String[]{};
    }


    @DexIgnore //@DexAdd
    public String name(int index) {
        return namesAndValues()[index * 2];
    }

    @DexAdd
    public String value(int index) {
        return namesAndValues()[index * 2 + 1];
    }

    @DexIgnore
    public int size() {
        return namesAndValues().length / 2;
    }
}