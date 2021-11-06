public class Insertion {
    public static void sort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            for (int j = i; j > 0 && a[j] < a[j-1]; j--)
            exch(a, j, j-1);
      }
    }  

    private static void exch(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
        int[] a = new int[] {3, 2, 7, 6, 4, 5, 1, 9};

        sort(a);

        for (int v: a) {
            System.out.println(v);
        }
    }
}
