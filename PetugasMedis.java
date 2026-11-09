// Abstract class: state & perilaku yang sama untuk Dokter dan Perawat
public abstract class PetugasMedis implements Autentikasi, Terjadwal {
    private final String id;
    private final String nama;
    private final String password;      // private: tidak bisa diakses subclass maupun luar
    private final String[] jadwal;
    private boolean sudahLogin = false;

    protected PetugasMedis(String id, String nama, String password, String[] jadwal) {
        this.id = id;
        this.nama = nama;
        this.password = password;
        this.jadwal = jadwal;
    }

    public String getId()   { return id; }
    public String getNama() { return nama; }

    @Override
    public boolean login(String username, String password) {
        sudahLogin = this.id.equals(username) && this.password.equals(password);
        return sudahLogin;
    }

    @Override
    public void logout() { sudahLogin = false; }

    @Override
    public boolean isLogin() { return sudahLogin; }

    @Override
    public void lihatJadwal() {
        System.out.println("Jadwal " + nama + ":");
        for (int i = 0; i < jadwal.length; i++) {
            System.out.println("  - " + jadwal[i]);
        }
    }
}
