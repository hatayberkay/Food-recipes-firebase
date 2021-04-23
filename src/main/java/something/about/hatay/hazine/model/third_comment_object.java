package something.about.hatay.hazine.model;

public class third_comment_object {

    String comment_isim_string;
    String comment_string;


    public String getComment_isim_string() {
        return comment_isim_string;
    }

    public void setComment_isim_string(String comment_isim_string) {
        this.comment_isim_string = comment_isim_string;
    }

    public String getComment_string() {
        return comment_string;
    }

    public void setComment_string(String comment_string) {
        this.comment_string = comment_string;
    }


    @Override
    public String toString() {
        return "third_comment_object{" +
                "comment_isim_string='" + comment_isim_string + '\'' +
                ", comment_string='" + comment_string + '\'' +
                '}';
    }
}
