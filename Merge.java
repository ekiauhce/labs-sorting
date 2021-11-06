public class Merge {
    
    private static int[] aux;

    public static void sort(int[] a) {
        aux = new int[a.length];
        sort(a, 0, a.length);
    }

    public static void main(String[] args) {
        int[] a = { 'M', 'E', 'R', 'G', 'E', 'S', 'O', 'R', 'T', 'E', 'X', 'A', 'M', 'P', 'L', 'E'};
        sort(a);
        show(a);
    }

    private static void sort(int[] a, int lo, int hi) {
        if (hi - lo == 1) return;
        int mid = (lo + hi) / 2;
        sort(a, lo, mid);
        sort(a, mid, hi);
        merge(a, lo, mid, hi);
    }


    private static void merge(int[] a, int lo, int mid, int hi) {
        int i = lo, j = mid;

        for (int k = lo; k < hi; k++) {
            aux[k] = a[k];
        }

        for (int k = lo; k < hi; k++) {
            if      (i >= mid)             a[k] = aux[j++];
            else if (j >= hi)              a[k] = aux[i++];
            else if (less(aux[i], aux[j])) a[k] = aux[i++];
            else                           a[k] = aux[j++];
        }
    }

    
    private static boolean less(int v, int w) {
        return v < w;
    }

    private static void show(int[] a) {
        for (var item: a) {
            System.out.print(item + " ");
        }
        System.out.println();
    }
}
