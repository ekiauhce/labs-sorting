public class Stopwatch {

    private final long start;

    public Stopwatch() {
        start = System.currentTimeMillis();
    }

    // returns time in ms
    public long elapsedTime() {
        long now = System.currentTimeMillis();
        return now - start;
    }
}