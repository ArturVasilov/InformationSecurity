package utils;

import jdbm.PrimaryTreeMap;
import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import main.md4.MD4;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @author Artur Vasilov
 */
public final class HashesStorage {

    private static final String PASSWORDS_FILE = "storage/passwords.txt";
    private static final String HASHES_STORAGE_FILE = "storage/md4hashes";
    private static final String HASHES_STORAGE_NODE = "all";
    private static final String HASHES_STORAGE_DATABASE_FILE = HASHES_STORAGE_FILE + ".dbr.0";

    private HashesStorage() {
    }

    public static void generateDatabaseIfNeeded() throws Exception {
        if (Files.exists(Paths.get(HASHES_STORAGE_DATABASE_FILE))) {
            return;
        }

        RecordManager recMan = RecordManagerFactory.createRecordManager(HASHES_STORAGE_FILE);
        PrimaryTreeMap<String, String> treeMap = recMan.treeMap(HASHES_STORAGE_NODE);

        try (Stream<String> stream = Files.lines(Paths.get(PASSWORDS_FILE))) {
            stream.forEach(password -> treeMap.put(MD4.hash(password), password));
        }
        recMan.commit();
        recMan.close();
    }

    public static String findValueFromHash(String hash) throws Exception {
        RecordManager recMan = RecordManagerFactory.createRecordManager(HASHES_STORAGE_FILE);
        PrimaryTreeMap<String, String> treeMap = recMan.treeMap(HASHES_STORAGE_NODE);
        String password = treeMap.get(hash);
        recMan.close();
        return password;
    }

}
