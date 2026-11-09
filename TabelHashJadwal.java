// Hash table buatan sendiri (separate chaining), key gabungan "idDokter|tanggal"
public class TabelHashJadwal {
    private static class Entri {
        String key;
        boolean[] slot;
        Entri next;
        Entri(String key, boolean[] slot, Entri next) { this.key = key; this.slot = slot; this.next = next; }
    }

    private final Entri[] bucket;
    private int jumlah = 0;
    public int perbandinganTerakhir = 0;     // jumlah perbandingan key pada operasi terakhir

    public TabelHashJadwal(int jumlahBucket) {
        bucket = new Entri[jumlahBucket];
    }

    public int indeksBucket(String key) {
        int h = 0;
        for (int i = 0; i < key.length(); i++) {
            h = h * 31 + key.charAt(i);
        }
        return (h & 0x7fffffff) % bucket.length;
    }

    public static String buatKey(String idDokter, int tanggal) {
        return idDokter + "|" + tanggal;
    }

    public void put(String key, boolean[] slot) {
        int b = indeksBucket(key);
        for (Entri e = bucket[b]; e != null; e = e.next) {
            if (e.key.equals(key)) { e.slot = slot; return; }
        }
        bucket[b] = new Entri(key, slot, bucket[b]);
        jumlah++;
    }

    // Rata-rata O(1) (1 + faktor beban), terburuk O(n)
    public boolean[] get(String key) {
        perbandinganTerakhir = 0;
        for (Entri e = bucket[indeksBucket(key)]; e != null; e = e.next) {
            perbandinganTerakhir++;
            if (e.key.equals(key)) return e.slot;
        }
        return null;
    }

    public boolean remove(String key) {
        int b = indeksBucket(key);
        Entri sebelum = null;
        for (Entri e = bucket[b]; e != null; sebelum = e, e = e.next) {
            if (e.key.equals(key)) {
                if (sebelum == null) bucket[b] = e.next; else sebelum.next = e.next;
                jumlah--;
                return true;
            }
        }
        return false;
    }

    public int ukuran()      { return jumlah; }
    public int jumlahBucket(){ return bucket.length; }
}
