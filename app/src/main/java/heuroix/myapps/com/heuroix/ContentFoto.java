package heuroix.myapps.com.heuroix;

public class ContentFoto {

    private String id_content;
    private String id_user;
    private String judul;
    private String gambar;
    private String deskripsi;
    private String date_created;
    private String nama;
    private String email;
    private String userimage;

    public ContentFoto(String id_content, String id_user, String judul, String gambar, String deskripsi, String date_created, String nama, String email, String userimage) {
        this.id_content = id_content;
        this.id_user = id_user;
        this.judul = judul;
        this.gambar = gambar;
        this.deskripsi = deskripsi;
        this.date_created = date_created;
        this.nama = nama;
        this.email = email;
        this.userimage = userimage;
    }

    public String getId_content() {
        return id_content;
    }

    public String getId_user() {
        return id_user;
    }

    public String getJudul() {
        return judul;
    }

    public String getGambar() {
        return gambar;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getDate_created() {
        return date_created;
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }

    public String getUserimage() {
        return userimage;
    }
}
