package main.md4;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCallLibrary;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Artur Vasilov
 */
public class MD4Native {

    private static final String NATIVE_LIBRARY_PATH = "nativelibs";
    private static final String NATIVE_LIBRARY = "nativelibs\\MD4Attack";

    static {
        NativeLibrary.addSearchPath("MD4Attack", new File(NATIVE_LIBRARY_PATH).getAbsolutePath());
    }

    public static class ArrayStruct extends Structure implements Structure.ByReference {

        public int[] values = {
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,

                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
        };

        public ArrayStruct(Pointer p) {
            super(p);
            read();
        }

        @Override
        protected List<String> getFieldOrder() {
            List<String> order = new ArrayList<>();
            order.add("values");
            return order;
        }
    }

    public interface MD4Library extends StdCallLibrary {
        MD4Library INSTANCE = (MD4Library) Native.loadLibrary(new File(NATIVE_LIBRARY).getAbsolutePath(), MD4Library.class);

        int findCollisions(PointerByReference p);

        int releaseResources(PointerByReference p);
    }

    public static CollisionData findCollisionNative() {
        PointerByReference pointerByReference = new PointerByReference();
        MD4Library.INSTANCE.findCollisions(pointerByReference);
        ArrayStruct struct = new ArrayStruct(pointerByReference.getValue());
        int[] first = new int[16];
        int[] second = new int[16];
        for (int i = 0; i < 16; i++) {
            first[i] = struct.values[i];
            second[i] = struct.values[i + 16];
        }
        CollisionData result = new CollisionData(first, second);
        MD4Library.INSTANCE.releaseResources(pointerByReference);
        return result;
    }

}
