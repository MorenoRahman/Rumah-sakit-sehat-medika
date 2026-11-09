SISTEM INFORMASI RUMAH SAKIT SEHAT MEDIKA - LO 1, LO 2, LO 3 (satu folder)
==========================================================================

Syarat : JDK 11 atau lebih baru (cek: javac -version)

CARA MENJALANKAN (Terminal)
---------------------------
  cd RS-Sehat-Medika
  javac *.java
  java Main                 <- program utama (menu interaktif)
  java SimulasiRiwayat      <- simulasi soal 3b(iii) saja (4 kunjungan, mundur 2x, maju 1x)

CATATAN VS CODE
---------------
Buka folder RS-Sehat-Medika ini saja (File > Open Folder). Jangan buka folder induk yang
masih berisi folder LO 1 / LO 2 lama, karena class bernama sama akan bentrok dan muncul
error "cannot be resolved to a type". Jalankan perintah java dengan huruf besar: Main.

AKUN
----
  Dokter  : D001 / rahasia1
  Perawat : N001 / rahasia2

MENU
----
  1-10  LO 1  login, jadwal, kunjungan, tanda vital, diagnosis, resep, riwayat, info array
  11-13 LO 2a antrian IGD (heap prioritas vs FIFO)
  14    LO 2b total slot rawat jalan (rekursif vs iteratif)
  15-16 LO 2c cari slot dokter (array 3D vs hash table), dokter cuti mendadak
  17-19 LO 3  navigasi riwayat kunjungan (sebelumnya / berikutnya / saat ini)
  20    LO 3b simulasi navigasi (4 kunjungan, mundur 2x, maju 1x)

TES OTOMATIS (opsional)
-----------------------
  java Main < skenario_uji.txt
Menjalankan seluruh alur dari file input tanpa mengetik manual.
