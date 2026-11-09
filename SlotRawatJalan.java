// Total slot tersedia dalam n hari dengan k slot per hari
// Relasi rekursif: S(n) = S(n-1) + k, S(0) = 0  ->  closed form S(n) = n * k
public class SlotRawatJalan {
    public static long panggilanRekursif = 0;
    public static long iterasi = 0;

    // Waktu O(n), ruang O(n) (kedalaman call stack)
    public static long totalRekursif(int n, int k) {
        panggilanRekursif++;
        if (n == 0) return 0;
        return totalRekursif(n - 1, k) + k;
    }

    // Waktu O(n), ruang O(1)
    public static long totalIteratif(int n, int k) {
        long total = 0;
        for (int i = 1; i <= n; i++) {
            total += k;
            iterasi++;
        }
        return total;
    }

    // Waktu O(1)
    public static long rumusTertutup(int n, int k) {
        return (long) n * k;
    }
}
