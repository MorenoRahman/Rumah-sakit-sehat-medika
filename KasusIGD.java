// Satu kasus pasien di IGD. Tingkat: 1 = kritis, 2 = sedang, 3 = ringan
public class KasusIGD {
    private final Pasien pasien;
    private final int tingkat;
    private final long urutanDatang;

    public KasusIGD(Pasien pasien, int tingkat, long urutanDatang) {
        this.pasien = pasien;
        this.tingkat = tingkat;
        this.urutanDatang = urutanDatang;
    }

    public Pasien getPasien()     { return pasien; }
    public int getTingkat()       { return tingkat; }
    public long getUrutanDatang() { return urutanDatang; }

    public static String namaTingkat(int t) {
        if (t == 1) return "Kritis";
        if (t == 2) return "Sedang";
        return "Ringan";
    }

    @Override
    public String toString() {
        return pasien.getNama() + " (" + namaTingkat(tingkat) + ")";
    }
}
