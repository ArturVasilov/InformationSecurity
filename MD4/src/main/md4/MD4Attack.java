package main.md4;

import utils.HashesStorage;

/**
 * @author Artur Vasilov
 */
public class MD4Attack {

    public static CollisionData findCollisions() {
        return MD4Native.findCollisionNative();
    }

    public static String tryPreimageAttack(String hash) {
        try {
            HashesStorage.generateDatabaseIfNeeded();
            return HashesStorage.findValueFromHash(hash);
        } catch (Exception e) {
            return "";
        }
    }

}
