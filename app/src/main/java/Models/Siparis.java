package Models;

public class Siparis {
    String tarih;
    String tutar;
    int id;
    String adres;
    String urun;

    public Siparis(String tarih, String tutar, int id, String adres, String urun) {
        this.tarih = tarih;
        this.tutar = tutar;
        this.id = id;
        this.adres = adres;
        this.urun = urun;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getTutar() {
        return tutar;
    }

    public void setTutar(String tutar) {
        this.tutar = tutar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getUrun() {
        return urun;
    }

    public void setUrun(String urun) {
        this.urun = urun;
    }
}
