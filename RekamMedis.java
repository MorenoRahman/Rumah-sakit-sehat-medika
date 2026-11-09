// Enkapsulasi: semua field private, akses hanya lewat method yang memeriksa pelaku
public class RekamMedis {
    private final String idRekam;
    private final String tanggal;
    private String diagnosis;
    private String resep;
    private String tandaVital;

    public RekamMedis(String idRekam, String tanggal) {
        this.idRekam = idRekam;
        this.tanggal = tanggal;
    }

    public String getIdRekam() { return idRekam; }
    public String getTanggal() { return tanggal; }

    // Pemeriksaan terpusat: pelaku harus ada dan sudah login
    private void periksa(Autentikasi pelaku, String aksi) {
        if (pelaku == null || !pelaku.isLogin()) {
            throw new SecurityException("akses '" + aksi + "' pada " + idRekam
                    + " ditolak: petugas belum login");
        }
    }

    // Tulis diagnosis & resep: hanya tipe PenulisResep (Dokter)
    public void isiDiagnosis(PenulisResep dokter, String diagnosis) {
        periksa(dokter, "isi diagnosis");
        this.diagnosis = diagnosis;
    }

    public void isiResep(PenulisResep dokter, String resep) {
        periksa(dokter, "isi resep");
        this.resep = resep;
    }

    // Tulis tanda vital: hanya tipe PencatatTandaVital (Perawat)
    public void isiTandaVital(PencatatTandaVital perawat, String tandaVital) {
        periksa(perawat, "catat tanda vital");
        this.tandaVital = tandaVital;
    }

    // Baca: petugas medis yang sudah login
    public String bacaDiagnosis(PetugasMedis p)  { periksa(p, "baca diagnosis");   return diagnosis; }
    public String bacaResep(PetugasMedis p)      { periksa(p, "baca resep");       return resep; }
    public String bacaTandaVital(PetugasMedis p) { periksa(p, "baca tanda vital"); return tandaVital; }
}
