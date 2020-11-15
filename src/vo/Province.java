package vo;

public class Province {
    private int provinceCode;
    private String provinceName;

    public Province() {
    }

    public Province(int provinceCode, String provinceName) {
        this.provinceCode = provinceCode;
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    @Override
    public String toString() {
        return "Province{" +
                "provinceCode=" + provinceCode +
                ", provinceName='" + provinceName + '\'' +
                '}';
    }
}
