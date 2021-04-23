package something.about.hatay.hazine.model;

public class third_object {
    String third_foto_string;
    String third_text_string;


    public String getThird_foto_string() {
        return third_foto_string;
    }

    public void setThird_foto_string(String third_foto_string) {
        this.third_foto_string = third_foto_string;
    }

    public String getThird_text_string() {
        return third_text_string;
    }

    public void setThird_text_string(String third_text_string) {
        this.third_text_string = third_text_string;
    }


    @Override
    public String toString() {
        return "third_object{" +
                "third_foto_string='" + third_foto_string + '\'' +
                ", third_text_string='" + third_text_string + '\'' +
                '}';
    }
}
