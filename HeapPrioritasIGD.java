// Priority queue berbasis min-heap (array asli). Prioritas: tingkat lebih kecil (lebih kritis)
// didahulukan; jika sama, yang datang lebih dulu didahulukan.
public class HeapPrioritasIGD {
    private KasusIGD[] data = new KasusIGD[4];
    private int jumlah = 0;

    private boolean lebihPrioritas(KasusIGD a, KasusIGD b) {
        if (a.getTingkat() != b.getTingkat()) return a.getTingkat() < b.getTingkat();
        return a.getUrutanDatang() < b.getUrutanDatang();
    }

    // O(log n)
    public void masuk(KasusIGD k) {
        if (jumlah == data.length) {
            KasusIGD[] baru = new KasusIGD[data.length * 2];
            for (int i = 0; i < jumlah; i++) baru[i] = data[i];
            data = baru;
        }
        data[jumlah] = k;
        int i = jumlah;
        jumlah++;
        while (i > 0) {                       // sift-up
            int induk = (i - 1) / 2;
            if (lebihPrioritas(data[i], data[induk])) {
                KasusIGD tmp = data[i]; data[i] = data[induk]; data[induk] = tmp;
                i = induk;
            } else break;
        }
    }

    // O(log n)
    public KasusIGD keluar() {
        if (jumlah == 0) return null;
        KasusIGD atas = data[0];
        jumlah--;
        data[0] = data[jumlah];
        data[jumlah] = null;
        int i = 0;
        while (true) {                        // sift-down
            int kiri = 2 * i + 1, kanan = 2 * i + 2, terbaik = i;
            if (kiri < jumlah && lebihPrioritas(data[kiri], data[terbaik])) terbaik = kiri;
            if (kanan < jumlah && lebihPrioritas(data[kanan], data[terbaik])) terbaik = kanan;
            if (terbaik == i) break;
            KasusIGD tmp = data[i]; data[i] = data[terbaik]; data[terbaik] = tmp;
            i = terbaik;
        }
        return atas;
    }

    public int ukuran() { return jumlah; }

    // Urutan layanan tanpa mengubah heap asli (bekerja pada salinan)
    public String urutanLayanan() {
        HeapPrioritasIGD salinan = new HeapPrioritasIGD();
        salinan.data = new KasusIGD[data.length];
        for (int i = 0; i < jumlah; i++) salinan.data[i] = data[i];
        salinan.jumlah = jumlah;
        String hasil = "";
        int no = 1;
        while (salinan.ukuran() > 0) {
            hasil += (no == 1 ? "" : " -> ") + salinan.keluar();
            no++;
        }
        return hasil.length() == 0 ? "(kosong)" : hasil;
    }
}
