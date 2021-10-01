public class Selection {

    public static void sort(int[] a) {
        int N = a.length;

        for (int i = 0; i < N; i++) {
            int mi = i;                     // index of smallest entry
            for (int j = i+1; j < N; j++) { // find smallest entry in a[i+1...N)
                if (a[j] < a[mi]) mi = j; 
            }
            exch(a, i, mi);
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