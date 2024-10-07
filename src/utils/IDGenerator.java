package src.utils;

public class IDGenerator {
    private static int currentID = 1;

    public static synchronized String nextID() {
        return String.valueOf(currentID++);
    }
}
