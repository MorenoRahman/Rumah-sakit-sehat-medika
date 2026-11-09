public class Perawat extends PetugasMedis implements PencatatTandaVital {
    public Perawat(String id, String nama, String password, String[] jadwal) {
        super(id, nama, password, jadwal);
    }

    @Override
    public void catatTandaVital(RekamMedis rm, String tandaVital) {
        rm.isiTandaVital(this, tandaVital);
    }
}
