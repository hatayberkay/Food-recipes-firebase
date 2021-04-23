package something.about.hatay.hazine.model;

public class main_object {
    String foto_string;
    String text_string;


    public String getFoto_string() {
        return foto_string;
    }

    public void setFoto_string(String foto_string) {
        this.foto_string = foto_string;
    }

    public String getText_string() {
        return text_string;
    }

    public void setText_string(String text_string) {
        this.text_string = text_string;
    }


    @Override
    public String toString() {
        return "main_object{" +
                "foto_string='" + foto_string + '\'' +
                ", text_string='" + text_string + '\'' +
                '}';
    }
}
