package main.md4;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.win32.StdCallLibrary;

import java.io.File;

/**
 * @author Artur Vasilov
 */
public class MD4Native {

    private static final String NATIVE_LIBRARY_PATH = "nativelibs";
    private static final String NATIVE_LIBRARY = "nativelibs\\MD4Attack";

    static {
        NativeLibrary.addSearchPath("MD4Attack", new File(NATIVE_LIBRARY_PATH).getAbsolutePath());
    }

    public interface MD4Library extends StdCallLibrary {
        MD4Library INSTANCE = (MD4Library) Native.loadLibrary(new File(NATIVE_LIBRARY).getAbsolutePath(), MD4Library.class);

        String findCollision(String hash);
    }

    public static String findCollisionNative(String hash) {
        return MD4Library.INSTANCE.findCollision(hash);
    }

}
