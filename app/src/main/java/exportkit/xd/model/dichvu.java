package exportkit.xd.model;

public class dichvu {
    private String gia;
    private String title;
    private String mota;
    private String hinhanh;
    private int id;

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public dichvu(String gia, String title, String mota, String hinhanh, int id) {
        this.gia = gia;
        this.title = title;
        this.mota = mota;
        this.hinhanh = hinhanh;
        this.id = id;
    }

    public dichvu() {
    }
}
