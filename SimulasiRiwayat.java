// Simulasi soal 3b(iii): 4 kunjungan, mundur 2 kali, maju 1 kali.
// Bisa dijalankan sendiri: java SimulasiRiwayat  (atau lewat menu 20 di Main)
public class SimulasiRiwayat {
    public static void jalankan() {
        RiwayatKunjungan riwayat = new RiwayatKunjungan();
        String[] catatan = {
            "Demam dan batuk, diberi paracetamol",
            "Kontrol tekanan darah, hasil normal",
            "Nyeri lambung, diberi antasida",
            "Gatal pada kulit, diberi salep"
        };

        System.out.println("Menambahkan 4 kunjungan:");
        for (int i = 0; i < catatan.length; i++) {
            riwayat.tambahKunjungan(catatan[i]);
            System.out.print("  Setelah tambah kunjungan " + (i + 1) + " -> ");
            riwayat.tampilkanSaatIni();
        }

        System.out.println("Navigasi mundur 2 kali:");
        for (int i = 1; i <= 2; i++) {
            riwayat.sebelumnya();
            System.out.print("  Mundur ke-" + i + " -> ");
            riwayat.tampilkanSaatIni();
        }

        System.out.println("Navigasi maju 1 kali:");
        riwayat.berikutnya();
        System.out.print("  Maju ke-1 -> ");
        riwayat.tampilkanSaatIni();
    }

    public static void main(String[] args) {
        jalankan();
    }
}
