import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    private static String baca(String prompt) throws IOException {
        System.out.print(prompt);
        String s = in.readLine();
        return s == null ? null : s.trim();
    }

    private static Integer angka(String s) {
        try { return Integer.valueOf(s); } catch (Exception e) { return null; }
    }

    public static void main(String[] args) throws IOException {
        Dokter dr = new Dokter("D001", "dr. Budi Santoso", "rahasia1",
                new String[]{"Senin 08.00-12.00", "Rabu 13.00-17.00"});
        Perawat ns = new Perawat("N001", "Ns. Sari Lestari", "rahasia2",
                new String[]{"Senin 07.00-14.00", "Kamis 07.00-14.00"});
        Pasien pasien = new Pasien("P001", "Andi Wijaya");
        PetugasMedis aktif = null;   // petugas yang sedang login
        ModulPenjadwalan modul = new ModulPenjadwalan(dr);

        System.out.println("SISTEM INFORMASI RUMAH SAKIT SEHAT MEDIKA");
        System.out.println("Pasien: " + pasien.getNama() + " (" + pasien.getNoPasien() + ")");

        while (true) {
            System.out.println();
            System.out.println("Petugas aktif: " + (aktif == null ? "(belum login)" : aktif.getNama()));
            System.out.println("1. Login sebagai Dokter");
            System.out.println("2. Login sebagai Perawat");
            System.out.println("3. Logout");
            System.out.println("4. Lihat jadwal");
            System.out.println("5. Mulai kunjungan baru");
            System.out.println("6. Catat tanda vital (Perawat)");
            System.out.println("7. Tulis diagnosis (Dokter)");
            System.out.println("8. Tulis resep (Dokter)");
            System.out.println("9. Lihat riwayat kunjungan");
            System.out.println("10. Info penyimpanan riwayat");
            System.out.println("11. IGD: tambah pasien ke antrian");
            System.out.println("12. IGD: panggil pasien berikutnya");
            System.out.println("13. IGD: tampilkan urutan layanan");
            System.out.println("14. Hitung total slot rawat jalan");
            System.out.println("15. Cari slot dokter pada tanggal tertentu");
            System.out.println("16. Dokter cuti mendadak");
            System.out.println("17. Navigasi riwayat: kunjungan sebelumnya");
            System.out.println("18. Navigasi riwayat: kunjungan berikutnya");
            System.out.println("19. Navigasi riwayat: tampilkan kunjungan saat ini");
            System.out.println("20. Simulasi navigasi riwayat (4 kunjungan, mundur 2x, maju 1x)");
            System.out.println("0. Keluar");
            String pilih = baca("Pilih menu: ");
            if (pilih == null || pilih.equals("0")) {
                System.out.println("Keluar dari sistem.");
                break;
            }

            try {
                if (pilih.equals("1") || pilih.equals("2")) {
                    PetugasMedis calon = pilih.equals("1") ? dr : ns;
                    String user = baca("Username: ");
                    String pass = baca("Password: ");
                    dr.logout();
                    ns.logout();
                    aktif = null;
                    if (user != null && pass != null && calon.login(user, pass)) {
                        aktif = calon;
                        System.out.println("Login berhasil. Selamat datang, " + calon.getNama() + ".");
                    } else {
                        System.out.println("Login gagal: username atau password salah.");
                    }

                } else if (pilih.equals("3")) {
                    if (aktif == null) {
                        System.out.println("Belum ada petugas yang login.");
                    } else {
                        System.out.println(aktif.getNama() + " telah logout.");
                        aktif.logout();
                        aktif = null;
                    }

                } else if (pilih.equals("4")) {
                    if (aktif == null) System.out.println("Silakan login terlebih dahulu.");
                    else aktif.lihatJadwal();

                } else if (pilih.equals("5")) {
                    if (aktif == null) {
                        System.out.println("Silakan login terlebih dahulu.");
                    } else {
                        String tgl = baca("Tanggal kunjungan (mis. 2026-11-09): ");
                        RekamMedis rm = new RekamMedis("RM-" + (pasien.jumlahKunjungan() + 1), tgl);
                        pasien.tambahKunjungan(rm);
                        System.out.println("Kunjungan " + rm.getIdRekam() + " (" + tgl + ") ditambahkan.");
                    }

                } else if (pilih.equals("6") || pilih.equals("7") || pilih.equals("8")) {
                    RekamMedis rm = pasien.kunjunganTerakhir();
                    if (rm == null) {
                        System.out.println("Belum ada kunjungan. Pilih menu 5 terlebih dahulu.");
                    } else if (pilih.equals("6")) {
                        String isi = baca("Tanda vital: ");
                        ns.catatTandaVital(rm, isi);
                        System.out.println("Tanda vital dicatat pada " + rm.getIdRekam() + ".");
                    } else if (pilih.equals("7")) {
                        String isi = baca("Diagnosis: ");
                        dr.tulisDiagnosis(rm, isi);
                        System.out.println("Diagnosis ditulis pada " + rm.getIdRekam() + ".");
                    } else {
                        String isi = baca("Resep: ");
                        dr.tulisResep(rm, isi);
                        System.out.println("Resep ditulis pada " + rm.getIdRekam() + ".");
                    }

                } else if (pilih.equals("9")) {
                    if (aktif == null) {
                        System.out.println("Silakan login terlebih dahulu.");
                    } else if (pasien.jumlahKunjungan() == 0) {
                        System.out.println("Belum ada kunjungan.");
                    } else {
                        System.out.println("Riwayat kunjungan " + pasien.getNama() + ":");
                        pasien.cetakRiwayat(aktif);
                    }

                } else if (pilih.equals("10")) {
                    System.out.println("Jumlah kunjungan: " + pasien.jumlahKunjungan()
                            + ", kapasitas array: " + pasien.kapasitasRiwayat()
                            + ", jumlah pembesaran array: " + pasien.pembesaranRiwayat());

                } else if (pilih.equals("11") || pilih.equals("12") || pilih.equals("13")
                        || pilih.equals("14") || pilih.equals("15") || pilih.equals("16")) {
                    if (aktif == null) {
                        System.out.println("Silakan login terlebih dahulu.");
                    } else if (pilih.equals("11")) {
                        String nama = baca("Nama pasien IGD: ");
                        Integer tingkat = angka(baca("Tingkat (1 = kritis, 2 = sedang, 3 = ringan): "));
                        if (tingkat == null || tingkat < 1 || tingkat > 3) System.out.println("Tingkat harus 1, 2, atau 3.");
                        else modul.tambahPasienIGD(nama, tingkat);
                    } else if (pilih.equals("12")) {
                        modul.panggilPasienIGD();
                    } else if (pilih.equals("13")) {
                        modul.tampilkanUrutanIGD();
                    } else if (pilih.equals("14")) {
                        Integer n = angka(baca("Jumlah hari (n): "));
                        Integer k = angka(baca("Slot per hari (k): "));
                        if (n == null || k == null || n < 0 || k < 0) System.out.println("Input tidak valid.");
                        else modul.hitungTotalSlot(n, k);
                    } else if (pilih.equals("15")) {
                        String id = baca("ID dokter (D001-D050): ");
                        Integer tgl = angka(baca("Tanggal (1-30): "));
                        if (tgl == null || tgl < 1 || tgl > 30) System.out.println("Tanggal harus 1 sampai 30.");
                        else modul.cariSlot(id, tgl);
                    } else {
                        String id = baca("ID dokter (D001-D050): ");
                        Integer dari = angka(baca("Cuti dari tanggal (1-30): "));
                        Integer sampai = angka(baca("Sampai tanggal (1-30): "));
                        if (dari == null || sampai == null || dari < 1 || sampai > 30 || dari > sampai)
                            System.out.println("Rentang tanggal tidak valid.");
                        else modul.dokterCuti(id, dari, sampai);
                    }

                } else if (pilih.equals("17") || pilih.equals("18") || pilih.equals("19")) {
                    if (aktif == null) {
                        System.out.println("Silakan login terlebih dahulu.");
                    } else {
                        RiwayatKunjungan nav = pasien.getNavigasi();
                        if (nav.jumlahKunjungan() == 0) {
                            System.out.println("Belum ada kunjungan. Pilih menu 5 terlebih dahulu.");
                        } else {
                            if (pilih.equals("17") && !nav.sebelumnya())
                                System.out.println("Sudah di kunjungan paling awal.");
                            if (pilih.equals("18") && !nav.berikutnya())
                                System.out.println("Sudah di kunjungan paling akhir.");
                            nav.tampilkanSaatIni();
                            pasien.cetakRekam(nav.nomorSaatIni(), aktif);
                        }
                    }

                } else if (pilih.equals("20")) {
                    SimulasiRiwayat.jalankan();

                } else {
                    System.out.println("Menu tidak dikenal.");
                }
            } catch (SecurityException e) {
                System.out.println("Ditolak: " + e.getMessage());
            }
        }
    }
}
