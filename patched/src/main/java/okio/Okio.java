package okio;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lanchon.dexpatcher.annotation.DexEdit;

/**
 * ? For debugging
 */
@DexEdit
public final class Okio {
    @DexEdit
    public static Sink sink(OutputStream out) {
        Method[] methods = Okio.class.getDeclaredMethods();
        for (Method m : methods) {
            if (m.getParameterTypes().length == 2 &&
                    m.getGenericReturnType().equals(Sink.class) &&
                    m.getGenericParameterTypes()[0].equals(OutputStream.class) &&
                    m.getGenericParameterTypes()[1].equals(Timeout.class)
                    ) {
                try {
                    return (Sink) m.invoke(null, out, new Timeout());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @DexEdit
    public static Source source(InputStream in) {
        Method[] methods = Okio.class.getDeclaredMethods();
        for (Method m : methods) {
            if (m.getParameterTypes().length == 2 &&
                    m.getGenericReturnType().equals(Source.class) &&
                    m.getGenericParameterTypes()[0].equals(InputStream.class) &&
                    m.getGenericParameterTypes()[1].equals(Timeout.class)
                    ) {
                try {
                    return (Source) m.invoke(null, in, new Timeout());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
