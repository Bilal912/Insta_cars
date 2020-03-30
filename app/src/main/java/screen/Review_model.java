package screen;

public class Review_model {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    String id;
    String date;

    public Review_model() {
    }

    public Review_model(String id, String date, String star, String message, String car_id, String user_id, String title, String user_name) {
        this.id = id;
        this.date = date;
        this.star = star;
        this.message = message;
        this.car_id = car_id;
        this.user_id = user_id;
        this.title = title;
        this.user_name = user_name;
    }

    String star;
    String message;
    String car_id;
    String user_id;
    String title;
    String user_name;

}
