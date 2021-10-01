import java.util.Random;

public class Quick {
    
    public static void sort(int[] a) {
        // shuffle the array to prevent worst case O(n^2)
        shuffle(a);
        sort(a, 0, a.length-1);
    }

    private static void sort(int[] a, int lo, int hi) {
        if (lo >= hi) return;

        int pi = partition(a, lo, hi); // partition index

        sort(a, lo, pi-1);
        sort(a, pi+1, hi);
    }

    private static int partition(int[] a, int lo, int hi) {
        int i = lo, j = hi+1;
        int pivot = a[lo];

        while (true) {
            while (a[++i] < pivot) if (i == hi) break; // scan left
            while (a[--j] > pivot) if (j == lo) break; // scan right
            if (i >= j) break;
            exch(a, i, j);
        }

        exch(a, lo, j);

        return j;
    }

    // uniformly random shuffle with the Fisher–Yates Algorithm
    private static void shuffle(int[] a) {
        Random rand = new Random();

        for (int i = a.length-1; i > 0; i--) {
            exch(a, i, rand.nextInt(i+1));
        }
    }

    private static void exch(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
        int[] a = new int[] {4, 5, 2, 3, 1, 7, 9, 10};
        sort(a);
        for (int v: a) {
            System.out.println(v);
        }
    }
}