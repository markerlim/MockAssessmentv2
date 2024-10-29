package task01;

public class Application {
    private String app;
    private String category;
    private Float rating;

    public Application(){

    }
    public Application(String app, String category, Float rating) {
        this.app = app;
        this.category = category;
        this.rating = rating;
    }

    public String getApp() {
        return app;
    }
    public void setApp(String app) {
        this.app = app;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public Float getRating() {
        return rating;
    }
    public void setRating(Float rating) {
        this.rating = rating;
    }

}
