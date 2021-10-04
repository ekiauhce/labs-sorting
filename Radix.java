import java.util.Arrays;

public class Radix {
    private static final int[] freq = new int[5001];

    public static void sort(int[] a) {
        int N = a.length;
        int[] sorted = new int[N];

        Arrays.fill(freq, 0);


        for (int v: a) {
            freq[v]++;
        }

        for (int i = 1; i < 5001; i++) {
            freq[i] += freq[i-1];
        }

        for (int i = N-1; i > -1; i--) {
            sorted[--freq[a[i]]] = a[i];
        }

        System.arraycopy(sorted, 0, a, 0, N);
    }

    public static void main(String[] args) {
        int[] a = new int[] {3, 2, 7, 6, 4, 5, 1, 9};

        sort(a);

        for (int v: a) {
            System.out.println(v);
        }
    }
}
