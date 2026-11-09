// Jadwal sebagai array 3 dimensi [dokter][tanggal][slot]
public class JadwalArray3D {
    private final boolean[][][] tersedia;
    private final int jumlahDokter, jumlahHari, slotPerHari;

    public JadwalArray3D(int jumlahDokter, int jumlahHari, int slotPerHari) {
        this.jumlahDokter = jumlahDokter;
        this.jumlahHari = jumlahHari;
        this.slotPerHari = slotPerHari;
        this.tersedia = new boolean[jumlahDokter][jumlahHari][slotPerHari];
    }

    public void set(int dokter, int tanggal, int slot, boolean nilai) {
        tersedia[dokter][tanggal - 1][slot] = nilai;
    }

    // Akses langsung O(1) ke baris slot; membaca slotPerHari sel
    public boolean[] ambilSlot(int dokter, int tanggal) {
        return tersedia[dokter][tanggal - 1];
    }

    // Dokter cuti: semua sel pada rentang tanggal diubah -> O(hari x slot)
    public int cuti(int dokter, int dari, int sampai) {
        int diubah = 0;
        for (int t = dari; t <= sampai; t++) {
            for (int s = 0; s < slotPerHari; s++) {
                tersedia[dokter][t - 1][s] = false;
                diubah++;
            }
        }
        return diubah;
    }

    public int totalSel() { return jumlahDokter * jumlahHari * slotPerHari; }
}
