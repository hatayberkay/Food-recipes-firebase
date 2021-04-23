package something.about.hatay.hazine.model;

public class second_object {
    String second_foto_string;
    String second_text_string;


    public String getSecond_foto_string() {
        return second_foto_string;
    }

    public void setSecond_foto_string(String second_foto_string) {
        this.second_foto_string = second_foto_string;
    }

    public String getSecond_text_string() {
        return second_text_string;
    }

    public void setSecond_text_string(String second_text_string) {
        this.second_text_string = second_text_string;
    }


    @Override
    public String toString() {
        return "second_object{" +
                "second_foto_string='" + second_foto_string + '\'' +
                ", second_text_string='" + second_text_string + '\'' +
                '}';
    }
}
