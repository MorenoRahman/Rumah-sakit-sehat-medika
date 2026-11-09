// Antrian FIFO biasa: circular array buatan sendiri
public class AntrianFIFOIGD {
    private KasusIGD[] data = new KasusIGD[4];
    private int depan = 0;
    private int jumlah = 0;

    // O(1) amortized
    public void masuk(KasusIGD k) {
        if (jumlah == data.length) {
            KasusIGD[] baru = new KasusIGD[data.length * 2];
            for (int i = 0; i < jumlah; i++) baru[i] = data[(depan + i) % data.length];
            data = baru;
            depan = 0;
        }
        data[(depan + jumlah) % data.length] = k;
        jumlah++;
    }

    // O(1)
    public KasusIGD keluar() {
        if (jumlah == 0) return null;
        KasusIGD k = data[depan];
        data[depan] = null;
        depan = (depan + 1) % data.length;
        jumlah--;
        return k;
    }

    public int ukuran() { return jumlah; }

    public String urutanLayanan() {
        String hasil = "";
        for (int i = 0; i < jumlah; i++) {
            hasil += (i == 0 ? "" : " -> ") + data[(depan + i) % data.length];
        }
        return jumlah == 0 ? "(kosong)" : hasil;
    }
}
