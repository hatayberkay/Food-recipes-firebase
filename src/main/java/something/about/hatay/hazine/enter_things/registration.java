package something.about.hatay.hazine.enter_things;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import something.about.hatay.hazine.enter;
import yemek.ucuz_yemek.R;

public class registration extends AppCompatActivity {
    EditText email_edittext, sifre_edittext, sifre_tekrar_edittext;
    Button üye_ol_buttonum;


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uye_ol);

        tanımla();


        // hatayberkay1234@gmail.com


        firebaseAuth = FirebaseAuth.getInstance();

        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();

        üye_ol_buttonum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                uye_ol_islemleri();


            }
        });


    }

    private void uye_ol_islemleri() {

        final String email_edittext_string = email_edittext.getText().toString();
        final String sifre_edittext_string = sifre_edittext.getText().toString();
        final String sifre_tekrar_edittext_string = sifre_tekrar_edittext.getText().toString();


        if (sifre_edittext_string.equals(sifre_tekrar_edittext_string)) {
            firebaseAuth.createUserWithEmailAndPassword(email_edittext_string, sifre_edittext_string).addOnCompleteListener(registration.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {


                    if (task.isSuccessful()) {


                        String[] separated = email_edittext_string.split("@");
                        final String firebase_usernames = separated[0];


                        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler");


                        HashMap kullanıcı_kaydı_hashmap = new HashMap();

                        kullanıcı_kaydı_hashmap.put("e_mail", firebase_usernames);
                        kullanıcı_kaydı_hashmap.put("isim", "yok");


                     //   kullanıcı_kaydı_hashmap.put("token", firebaseUser.getIdToken(true));

                        databaseReference.updateChildren(kullanıcı_kaydı_hashmap);

                        try {
                           // firebaseUser.sendEmailVerification();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Intent i = new Intent(getApplicationContext(), enter.class);
                        startActivity(i);
                        registration.this.finish();


                    } else {
                        Toast.makeText(registration.this, "Fail", Toast.LENGTH_SHORT).show();
                    }

                }

            });
        } else {

            Toast.makeText(registration.this, "sifreler esit degil", Toast.LENGTH_SHORT).show();
        }


    }

    private void tanımla() {
        email_edittext = (EditText) findViewById(R.id.email_edittext);
        sifre_edittext = (EditText) findViewById(R.id.sifre_edittext);
        sifre_tekrar_edittext = (EditText) findViewById(R.id.sifre_tekrar_edittext);
        üye_ol_buttonum = (Button) findViewById(R.id.üye_ol_buttonum);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent enter_intent = new Intent(getApplicationContext(), enter.class);
        startActivity(enter_intent);
        registration.this.finish();

    }
}
