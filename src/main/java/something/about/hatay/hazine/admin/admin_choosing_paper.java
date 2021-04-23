package something.about.hatay.hazine.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import yemek.ucuz_yemek.R;

public class admin_choosing_paper extends AppCompatActivity {
TextView admin_text_textview;
Button uc_sayfa_button , iki_sayfa_button ,bir_sayfa_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sayfa_secimi);

        tanımla();


        admin_text_textview.setText("Admin Paneli");


        bir_sayfa_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent first_panel_intent = new Intent(getApplicationContext(),first_panel.class);
                startActivity(first_panel_intent);




            }
        });

        iki_sayfa_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent first_panel_intent = new Intent(getApplicationContext(),second_panel.class);
                startActivity(first_panel_intent);


            }
        });

        uc_sayfa_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent first_panel_intent = new Intent(getApplicationContext(),third_panel.class);
                startActivity(first_panel_intent);
            }
        });

    }

    private void tanımla() {
        admin_text_textview = (TextView)findViewById(R.id.admin_text_textview);
        bir_sayfa_button = (Button)findViewById(R.id.bir_sayfa_button);
        iki_sayfa_button = (Button)findViewById(R.id.iki_sayfa_button);
        uc_sayfa_button = (Button)findViewById(R.id.uc_sayfa_button);


    }
}
