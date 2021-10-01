import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.DoubleStream;

public class Benchmarking {

    private int ENTRY_BOUND = 5_000;
    private int MIN_N       = 1_000;
    private int MAX_N       = 15_000;
    private int DELTA_N     = 500;
    private int ITERS       = 5;      // number of repetitions per one arraySize

    private Consumer<int[]> sortingMethod;
    private String name;
    private int[] data;
    private Random random;
    
    public Benchmarking(Consumer<int[]> sortingMethod, String name) {
        this.sortingMethod = sortingMethod;    
        this.name = name;
        random = new Random();
    }

    public Benchmarking(Consumer<int[]> sortingMethod, String name, int minN, int maxN, int deltaN, int iters) {
        this(sortingMethod, name);
        MIN_N = minN;
        MAX_N = maxN;
        DELTA_N = deltaN;
        ITERS = iters;
    }

    
    public void run() {
        try (PrintWriter out = new PrintWriter(new FileWriter(name + "-results.txt"))){
            
            int resultsCount = (MAX_N - MIN_N) / DELTA_N + 1;
            out.println(resultsCount);
            
            for (int arraySize = MIN_N; arraySize <= MAX_N; arraySize += DELTA_N) {
                out.print(arraySize + " ");
            }
            out.println();
            
            for (int arraySize = MIN_N; arraySize <= MAX_N; arraySize += DELTA_N) {
                final int N = arraySize; // new final variable to pass the in lambda 
                double avgElapsed = DoubleStream.generate(() -> timeTrial(N))
                    .limit(ITERS)
                    .average()
                    .getAsDouble();
                out.print(avgElapsed + " ");
            }
            out.println();
        } catch (IOException e) {} 
    }
    
    private double timeTrial(int N) {
        data = new int[N];
        for (int i = 0; i < N; i++) {
            data[i] = random.nextInt(ENTRY_BOUND);
        }
        
        Stopwatch timer = new Stopwatch();
        sortingMethod.accept(data); // invoke sorting method
        return timer.elapsedTime();
    }
}
