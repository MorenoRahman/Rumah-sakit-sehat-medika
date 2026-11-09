// LO 3: riwayat kunjungan yang bisa dinavigasi maju-mundur.
// Doubly Linked List buatan sendiri (tanpa java.util.LinkedList).
public class RiwayatKunjungan {
    private static class Node {
        final int nomor;          // urutan kunjungan (1, 2, 3, ...)
        final String catatan;
        Node prev;
        Node next;
        Node(int nomor, String catatan) { this.nomor = nomor; this.catatan = catatan; }
    }

    private Node head;            // kunjungan paling awal
    private Node tail;            // kunjungan paling akhir
    private Node saatIni;         // pointer "saat ini"
    private int jumlah = 0;

    // O(1): tambah di akhir, lalu pindahkan pointer "saat ini" ke kunjungan baru
    public void tambahKunjungan(String catatan) {
        jumlah++;
        Node baru = new Node(jumlah, catatan);
        if (tail == null) {
            head = baru;
            tail = baru;
        } else {
            tail.next = baru;
            baru.prev = tail;
            tail = baru;
        }
        saatIni = baru;
    }

    // O(1): mundur satu kunjungan. false jika sudah di kunjungan paling awal / riwayat kosong
    public boolean sebelumnya() {
        if (saatIni == null || saatIni.prev == null) return false;
        saatIni = saatIni.prev;
        return true;
    }

    // O(1): maju satu kunjungan. false jika sudah di kunjungan paling akhir / riwayat kosong
    public boolean berikutnya() {
        if (saatIni == null || saatIni.next == null) return false;
        saatIni = saatIni.next;
        return true;
    }

    // O(1)
    public void tampilkanSaatIni() {
        if (saatIni == null) {
            System.out.println("Riwayat kunjungan masih kosong.");
        } else {
            System.out.println("Kunjungan ke-" + saatIni.nomor + " dari " + jumlah + ": " + saatIni.catatan);
        }
    }

    public int jumlahKunjungan() { return jumlah; }
    public int nomorSaatIni()    { return saatIni == null ? 0 : saatIni.nomor; }
}
