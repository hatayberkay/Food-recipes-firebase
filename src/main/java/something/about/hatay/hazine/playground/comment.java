package something.about.hatay.hazine.playground;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import yemek.ucuz_yemek.R;

public class comment extends AppCompatActivity {
    ListView comment_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        tanımla();


    }

    private void tanımla() {

        comment_listview = (ListView)findViewById(R.id.comment_listview);


    }


}
