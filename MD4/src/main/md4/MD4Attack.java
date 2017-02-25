package main.md4;

import utils.HashesStorage;

/**
 * @author Artur Vasilov
 */
public class MD4Attack {

    public static String findCollisionMessage(String hash) {
        String localTry = tryFindExisting(hash);
        if (localTry != null && !localTry.isEmpty()) {
            return localTry;
        }
        return MD4Native.findCollisionNative(hash);
    }

    private static String tryFindExisting(String hash) {
        try {
            HashesStorage.generateDatabaseIfNeeded();
            return HashesStorage.findValueFromHash(hash);
        } catch (Exception e) {
            return "";
        }
    }

}
