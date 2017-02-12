package main.md4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class MD4Test {

    @Test
    public void testMD4Hash1() throws Exception {
        String message = "Hello, World!";
        String hash = "94e3cb0fa9aa7a5ee3db74b79e915989";
        assertEquals(hash, MD4.hash(message));
    }

    @Test
    public void testMD4Hash2() throws Exception {
        String message = "123456";
        String hash = "585028aa0f794af812ee3be8804eb14a";
        assertEquals(hash, MD4.hash(message));
    }

    @Test
    public void testMD4Hash3() throws Exception {
        String message = "Тестируем реализацию алгоритма MD4";
        String hash = "eaa927e95a05a7a01285419442fe5256";
        assertEquals(hash, MD4.hash(message));
    }
}