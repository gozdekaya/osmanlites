package Models;

import java.util.List;

public class KategoriUrun {

    public int id;
    public String title;
    public String media;
    public List<Product> products;

    public KategoriUrun(int id, String title, String media, List<Product> products) {
        this.id = id;
        this.title = title;
        this.media = media;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
