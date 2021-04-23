package something.about.hatay.hazine.enter_things;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;
import something.about.hatay.hazine.enter;
import yemek.ucuz_yemek.R;

public class confirm_email extends AppCompatActivity {
    EditText onay_email_edittext;
    Button onay_emaili_gonder_button;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_email);

        tanımla();


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

// TODO: 2019-11-11 Onaylama işlemi tek satırlık kod bunu deneyerek bulacağız.


        onay_emaili_gonder_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (firebaseAuth.getCurrentUser() != null ){

                    firebaseUser.sendEmailVerification();
                    Intent enter_intent = new Intent(getApplicationContext(), enter.class);
                    startActivity(enter_intent);


                    confirm_email.this.finish();


                    Toast.makeText(confirm_email.this, "E-mailinizi kontrol ediniz.", Toast.LENGTH_SHORT).show();

                }else {

                    Intent enter_intent = new Intent(getApplicationContext(), enter.class);
                    startActivity(enter_intent);
                    confirm_email.this.finish();

                    Toast.makeText(confirm_email.this, "Lütfen hesabınızla giriş yapıp tekrar deneyin.", Toast.LENGTH_SHORT).show();


                }




            }
        });


    }

    private void tanımla() {

        onay_email_edittext = (EditText) findViewById(R.id.onay_email_edittext);
        onay_emaili_gonder_button = (Button) findViewById(R.id.onay_emaili_gonder_button);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent enter_intent = new Intent(getApplicationContext(), enter.class);
        startActivity(enter_intent);
        confirm_email.this.finish();

    }
}
