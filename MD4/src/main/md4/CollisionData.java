package main.md4;

/**
 * @author Artur Vasilov
 */
public class CollisionData {

    private static final int INTS_COUNT = 16;

    private final int[] firstBytes;
    private final int[] secondBytes;

    public CollisionData(int[] firstBytes, int[] secondBytes) {
        if (firstBytes.length != INTS_COUNT || secondBytes.length != INTS_COUNT) {
            throw new IllegalArgumentException(String.format("MD4 collisions must have length of %d", INTS_COUNT));
        }
        this.firstBytes = firstBytes;
        this.secondBytes = secondBytes;
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder("first = int[] {").append("\n");
        int columnsCount = (int) Math.sqrt(INTS_COUNT);

        for (int i = 0; i < INTS_COUNT; i++) {
            if ((i + 1) % columnsCount == 0) {
                message.append("0x").append(String.format("%08x", firstBytes[i])).append("\n");
            }
            else {
                message.append("0x").append(String.format("%08x", firstBytes[i])).append(", ");
            }
        }
        message.append("}\n\n");

        message.append("second = int[] {").append("\n");
        for (int i = 0; i < INTS_COUNT; i++) {
            if ((i + 1) % columnsCount == 0) {
                message.append("0x").append(String.format("%08x", secondBytes[i])).append("\n");
            }
            else {
                message.append("0x").append(String.format("%08x", secondBytes[i])).append(", ");
            }
        }
        message.append("}\n");

        return message.toString();
    }
}
