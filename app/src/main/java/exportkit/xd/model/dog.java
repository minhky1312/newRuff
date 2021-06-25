package exportkit.xd.model;

public class dog {
    private int id;
    private String idchu;
    private String title;
    private String mota;
    private String gia;
    private String diachi;
    private String loai;
    private String age;
    private float can_nang;
    private String gioitinh;
    private boolean trang_thai;
    private String hinhanh;

    public dog() {
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

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public float getCan_nang() {
        return can_nang;
    }

    public void setCan_nang(float can_nang) {
        this.can_nang = can_nang;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public boolean isTrang_thai() {
        return trang_thai;
    }

    public void setTrang_thai(boolean trang_thai) {
        this.trang_thai = trang_thai;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getIdchu() {
        return idchu;
    }

    public void setIdchu(String idchu) {
        this.idchu = idchu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public dog(int id, String idchu, String title, String mota, String gia, String diachi, String loai, String age, float can_nang, String gioitinh, boolean trang_thai, String hinhanh) {
        this.id = id;
        this.idchu = idchu;
        this.title = title;
        this.mota = mota;
        this.gia = gia;
        this.diachi = diachi;
        this.loai = loai;
        this.age = age;
        this.can_nang = can_nang;
        this.gioitinh = gioitinh;
        this.trang_thai = trang_thai;
        this.hinhanh = hinhanh;
    }

    @Override
    public String toString() {
        return "dog{" +
                "id=" + id +
                ", idchu='" + idchu + '\'' +
                ", title='" + title + '\'' +
                ", mota='" + mota + '\'' +
                ", gia='" + gia + '\'' +
                ", diachi='" + diachi + '\'' +
                ", loai='" + loai + '\'' +
                ", age='" + age + '\'' +
                ", can_nang=" + can_nang +
                ", gioitinh='" + gioitinh + '\'' +
                ", trang_thai=" + trang_thai +
                ", hinhanh='" + hinhanh + '\'' +
                '}';
    }
}
