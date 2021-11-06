public class Heap {

    public static void sort(int[] a) {
        int n = a.length;
        for (int k = n/2; k >= 1; k--) {
            sink(a, k, n);
        }

        int k = n;
        while(k > 1) {
            exch(a, 1, k--);
            sink(a, 1, k);
        }
    }

    private static void sink(int[] a, int k, int n) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && less(a, j, j + 1)) j++;
            if (!less(a, k, j)) break;
            exch(a, k, j);
            k = j;
        }
    }

    private static void exch(int[] a, int i, int j) {
        int temp = a[i - 1];
        a[i - 1] = a[j - 1];
        a[j - 1] = temp;
    }

    private static boolean less(int[] a, int i, int j) {
        return a[i - 1] < a[j - 1];
    }

    public static void main(String[] args) {
        int[] a = new int[] {3, 2, 7, 6, 4, 5, 1, 9};

        sort(a);

        for (int v: a) {
            System.out.println(v);
        }
    }
}
