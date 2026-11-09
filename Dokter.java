public class Dokter extends PetugasMedis implements PenulisResep {
    public Dokter(String id, String nama, String password, String[] jadwal) {
        super(id, nama, password, jadwal);
    }

    @Override
    public void tulisResep(RekamMedis rm, String resep) {
        rm.isiResep(this, resep);
    }

    public void tulisDiagnosis(RekamMedis rm, String diagnosis) {
        rm.isiDiagnosis(this, diagnosis);
    }
}
