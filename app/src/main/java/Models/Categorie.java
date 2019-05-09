package Models;

public class Categorie {
    int id;
    String title,media;

    public Categorie(int id, String title, String media) {
        this.id = id;
        this.title = title;
        this.media = media;
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
}
