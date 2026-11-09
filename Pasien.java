public class Pasien {
    private final String noPasien;
    private final String nama;
    private final DaftarRekamMedis riwayat = new DaftarRekamMedis();
    private final RiwayatKunjungan navigasi = new RiwayatKunjungan();   // LO 3

    public Pasien(String noPasien, String nama) {
        this.noPasien = noPasien;
        this.nama = nama;
    }

    public String getNoPasien() { return noPasien; }
    public String getNama()     { return nama; }

    public void tambahKunjungan(RekamMedis rm) {
        riwayat.tambah(rm);
        navigasi.tambahKunjungan(rm.getIdRekam() + " (" + rm.getTanggal() + ")");
    }
    public RiwayatKunjungan getNavigasi()      { return navigasi; }
    public int jumlahKunjungan()               { return riwayat.ukuran(); }
    public int kapasitasRiwayat()              { return riwayat.kapasitas(); }
    public int pembesaranRiwayat()             { return riwayat.jumlahPembesaran(); }

    // Kunjungan terbaru (null jika belum ada)
    public RekamMedis kunjunganTerakhir() {
        if (riwayat.ukuran() == 0) return null;
        return riwayat.ambil(riwayat.ukuran() - 1);
    }

    private String nilai(String s) { return s == null ? "(belum diisi)" : s; }

    // Cetak satu kunjungan (nomor mulai dari 1)
    public void cetakRekam(int nomor, PetugasMedis pembaca) {
        RekamMedis rm = riwayat.ambil(nomor - 1);
        System.out.println(nomor + ". " + rm.getTanggal() + " [" + rm.getIdRekam() + "]");
        System.out.println("   Tanda vital: " + nilai(rm.bacaTandaVital(pembaca)));
        System.out.println("   Diagnosis  : " + nilai(rm.bacaDiagnosis(pembaca)));
        System.out.println("   Resep      : " + nilai(rm.bacaResep(pembaca)));
    }

    // Telusur kronologis (urutan tambah = urutan waktu)
    public void cetakRiwayat(PetugasMedis pembaca) {
        for (int i = 1; i <= riwayat.ukuran(); i++) {
            cetakRekam(i, pembaca);
        }
    }
}
