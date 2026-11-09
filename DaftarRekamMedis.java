// Dynamic (growable) array buatan sendiri: array asli Java, tanpa java.util
public class DaftarRekamMedis {
    private static final int KAPASITAS_AWAL = 2;

    private RekamMedis[] data = new RekamMedis[KAPASITAS_AWAL];
    private int jumlah = 0;
    private int jumlahPembesaran = 0;

    // Amortized O(1)
    public void tambah(RekamMedis rm) {
        if (rm == null) throw new IllegalArgumentException("RekamMedis tidak boleh null");
        if (jumlah == data.length) {
            besarkan();
        }
        data[jumlah] = rm;
        jumlah++;
    }

    // O(n): buat array 2x lebih besar, salin isi lama
    private void besarkan() {
        RekamMedis[] baru = new RekamMedis[data.length * 2];
        for (int i = 0; i < jumlah; i++) {
            baru[i] = data[i];
        }
        data = baru;
        jumlahPembesaran++;
    }

    // O(1)
    public RekamMedis ambil(int indeks) {
        if (indeks < 0 || indeks >= jumlah) {
            throw new IndexOutOfBoundsException("indeks " + indeks + " di luar rentang 0.." + (jumlah - 1));
        }
        return data[indeks];
    }

    public int ukuran()             { return jumlah; }
    public int kapasitas()          { return data.length; }
    public int jumlahPembesaran()   { return jumlahPembesaran; }
}
