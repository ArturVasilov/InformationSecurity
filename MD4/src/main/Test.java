package main;

import main.md4.MD4;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Artur Vasilov
 */
public class Test {

    public static void main(String[] args) {
        int symbols = 0xd6caea18;
        int size = 256 * 256;
        int char2 = symbols % size;
        symbols >>= 16;
        int char1 = symbols % size;

        /*
        int m0[] = {
                0x13985e12, 0x748a810b, 0x4d1df15a, 0x181d1516,
                0x2d6e09ac, 0x4b6dbdb9, 0x6464b0c8, 0xfba1c097,
                0xabe17be0, 0xed1ed4b3, 0x4120abf5, 0x20771029,
                0x20771027, 0xfdfffbff, 0xffffbffb, 0x6774bed2
        };

        int m1[] = {
                0x13985e12, 0x748a810b, 0x4d1df15a, 0x181d1516,
                0x2d6e09ac, 0x4b6dbdb9, 0x6464b0c8, 0xfba1c097,
                0xabe17be0, 0xed1ed4b3, 0x4120abf5, 0x20771029,
                0x20771028, 0xfdfffbff, 0xffffbffb, 0x6774bed2
        };*/

        String value0 = "13985e12748a810b4d1df15a181d15162d6e09ac4b6dbdb96464b0c8fba1c097" +
                "abe17be0ed1ed4b34120abf52077102920771028fdfffbffffffbffb6774bed2";
        String value1 = "13985e12748a810b4d1df15a181d15162d6e09ac4b6dbdb96464b0c8fba1c097" +
                "abe17be0ed1ed4b34120abf52077102820771028fdfffbffffffbffb6774bed2";
        System.out.println(value0);
        System.out.println(value1);
//
        System.out.println(MD4.hash(value0));
        System.out.println(MD4.hash(value1));

//        System.out.println(Character.toString((char) char2));
//        System.out.println(Character.toString((char) char1));
        String hash = MD4.hash("asaxassxas");
        System.out.println(hash.getBytes().length);
    }

    private static String fromIntArray(int[] array) {
        List<Integer> items = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (int symbols : array) {
            builder.append(BigInteger.valueOf(symbols).toString(16)).append(" ");
        }
        System.out.println(items);
        return builder.toString();
    }
}
