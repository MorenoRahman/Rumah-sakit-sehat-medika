// Modul penjadwalan LO 2, memakai Dokter dan Pasien dari LO 1
public class ModulPenjadwalan {
    public static final int JUMLAH_DOKTER = 50;
    public static final int JUMLAH_HARI = 30;
    public static final int SLOT_PER_HARI = 10;

    private final Dokter[] dokter = new Dokter[JUMLAH_DOKTER];
    private final JadwalArray3D jadwalArray = new JadwalArray3D(JUMLAH_DOKTER, JUMLAH_HARI, SLOT_PER_HARI);
    private final TabelHashJadwal jadwalHash = new TabelHashJadwal(2003);

    private final HeapPrioritasIGD antrianPrioritas = new HeapPrioritasIGD();
    private final AntrianFIFOIGD antrianFIFO = new AntrianFIFOIGD();
    private long urutanDatang = 0;

    public ModulPenjadwalan(Dokter dokterPertama) {
        for (int i = 0; i < JUMLAH_DOKTER; i++) {
            String id = "D" + (i + 1 < 10 ? "00" : "0") + (i + 1);
            if (i == 0) dokter[i] = dokterPertama;
            else dokter[i] = new Dokter(id, "dr. Dokter " + id, "pw" + id, new String[]{"Senin 08.00-12.00"});
            for (int t = 1; t <= JUMLAH_HARI; t++) {
                boolean[] slot = new boolean[SLOT_PER_HARI];
                for (int s = 0; s < SLOT_PER_HARI; s++) {
                    slot[s] = ((i + t + s) % 3 != 0);          // sebagian slot sudah terisi
                    jadwalArray.set(i, t, s, slot[s]);
                }
                jadwalHash.put(TabelHashJadwal.buatKey(id, t), slot);
            }
        }
    }

    // ---------- 2a: antrian IGD ----------
    public void tambahPasienIGD(String nama, int tingkat) {
        urutanDatang++;
        Pasien p = new Pasien("IGD-" + urutanDatang, nama);
        KasusIGD k = new KasusIGD(p, tingkat, urutanDatang);
        antrianPrioritas.masuk(k);
        antrianFIFO.masuk(k);
        System.out.println(k + " masuk antrian IGD. Pasien menunggu: " + antrianPrioritas.ukuran());
    }

    public void panggilPasienIGD() {
        KasusIGD p = antrianPrioritas.keluar();
        KasusIGD f = antrianFIFO.keluar();
        if (p == null) {
            System.out.println("Antrian IGD kosong.");
            return;
        }
        System.out.println("Prioritas (heap): " + p);
        System.out.println("FIFO biasa      : " + f);
        System.out.println("Pasien menunggu : " + antrianPrioritas.ukuran());
    }

    public void tampilkanUrutanIGD() {
        System.out.println("Urutan layanan prioritas (heap): " + antrianPrioritas.urutanLayanan());
        System.out.println("Urutan layanan FIFO biasa      : " + antrianFIFO.urutanLayanan());
    }

    // ---------- 2b: total slot rawat jalan ----------
    public void hitungTotalSlot(int n, int k) {
        SlotRawatJalan.panggilanRekursif = 0;
        SlotRawatJalan.iterasi = 0;
        try {
            long r = SlotRawatJalan.totalRekursif(n, k);
            System.out.println("Rekursif : total = " + r + " (pemanggilan method: " + SlotRawatJalan.panggilanRekursif + ")");
        } catch (StackOverflowError e) {
            System.out.println("Rekursif : StackOverflowError (kedalaman rekursi terlalu besar untuk n = " + n + ")");
        }
        long i = SlotRawatJalan.totalIteratif(n, k);
        System.out.println("Iteratif : total = " + i + " (jumlah iterasi: " + SlotRawatJalan.iterasi + ")");
        System.out.println("Rumus n x k = " + SlotRawatJalan.rumusTertutup(n, k));
    }

    // ---------- 2c: cari slot dokter pada tanggal tertentu ----------
    private int cariIndeksDokter(String id, int[] langkah) {
        langkah[0] = 0;
        for (int i = 0; i < JUMLAH_DOKTER; i++) {
            langkah[0]++;
            if (dokter[i].getId().equals(id)) return i;
        }
        return -1;
    }

    private String daftarSlot(boolean[] slot) {
        String s = "";
        for (int i = 0; i < slot.length; i++) {
            if (slot[i]) s += (s.length() == 0 ? "" : ", ") + (i + 1);
        }
        return s.length() == 0 ? "tidak ada" : s;
    }

    public void cariSlot(String idDokter, int tanggal) {
        int[] langkah = new int[1];
        int idx = cariIndeksDokter(idDokter, langkah);
        if (idx < 0) {
            System.out.println("Dokter " + idDokter + " tidak ditemukan.");
            return;
        }
        System.out.println("Dokter " + dokter[idx].getNama() + ", tanggal " + tanggal + ":");
        boolean[] a = jadwalArray.ambilSlot(idx, tanggal);
        System.out.println("Array 3 dimensi: memetakan ID ke indeks " + langkah[0]
                + " langkah, lalu akses langsung (0 perbandingan key). Slot kosong: " + daftarSlot(a));
        boolean[] h = jadwalHash.get(TabelHashJadwal.buatKey(idDokter, tanggal));
        if (h == null) {
            System.out.println("Hash table     : " + jadwalHash.perbandinganTerakhir
                    + " perbandingan key, tidak ada jadwal untuk dokter dan tanggal ini.");
        } else {
            System.out.println("Hash table     : bucket " + jadwalHash.indeksBucket(TabelHashJadwal.buatKey(idDokter, tanggal))
                    + ", " + jadwalHash.perbandinganTerakhir + " perbandingan key. Slot kosong: " + daftarSlot(h));
        }
    }

    // ---------- 2c(ii): dokter cuti mendadak ----------
    public void dokterCuti(String idDokter, int dari, int sampai) {
        int[] langkah = new int[1];
        int idx = cariIndeksDokter(idDokter, langkah);
        if (idx < 0) {
            System.out.println("Dokter " + idDokter + " tidak ditemukan.");
            return;
        }
        int sel = jadwalArray.cuti(idx, dari, sampai);
        int hapus = 0;
        for (int t = dari; t <= sampai; t++) {
            if (jadwalHash.remove(TabelHashJadwal.buatKey(idDokter, t))) hapus++;
        }
        System.out.println(dokter[idx].getNama() + " cuti tanggal " + dari + "-" + sampai + ".");
        System.out.println("Array 3 dimensi: " + sel + " sel diubah menjadi tidak tersedia (ukuran array tetap " + jadwalArray.totalSel() + " sel).");
        System.out.println("Hash table     : " + hapus + " entri dihapus (jumlah entri sekarang " + jadwalHash.ukuran() + ").");
    }
}
