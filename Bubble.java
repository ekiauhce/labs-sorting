public class Bubble {

    public static void sort(int[] a) {
        for (int i = 0; i < a.length-1; i++) {
            for (int j = 0; j < a.length-1-i; j++) {
                if (a[j] > a[j+1]) {
                    exch(a, j, j+1);
                }
            }
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
