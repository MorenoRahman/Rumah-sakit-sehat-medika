// Kemampuan "login" yang dimiliki semua petugas medis
public interface Autentikasi {
    boolean login(String username, String password);
    void logout();
    boolean isLogin();
}
