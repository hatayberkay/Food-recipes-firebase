package something.about.hatay.hazine.enter_things;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import something.about.hatay.hazine.enter;
import something.about.hatay.hazine.playground.main_playground;
import yemek.ucuz_yemek.R;

public class new_username extends AppCompatActivity {
EditText kullanıcı_adı_secimi_edittext;
Button kullanıcı_adı_belirleme_button;

FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;

FirebaseAuth firebaseAuth;
FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_username);

        tanımla();


        kullanıcı_adı_belirleme_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String yeni_kullanıcı_adı_string = kullanıcı_adı_secimi_edittext.getText().toString();

                String[] separated = firebaseUser.getEmail().toString().split("@");
                final String firebase_usernames = separated[0] ;

                databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler");


                HashMap kullanıcı_kaydı_hashmap = new HashMap();

                kullanıcı_kaydı_hashmap.put("e_mail", firebase_usernames);
                kullanıcı_kaydı_hashmap.put("isim", yeni_kullanıcı_adı_string);



                databaseReference.updateChildren(kullanıcı_kaydı_hashmap);

                if (firebaseAuth.getCurrentUser() != null && firebaseUser.isEmailVerified() == true){

                    Intent enter_intent = new Intent(getApplicationContext(), main_playground.class);
                    startActivity(enter_intent);
                    new_username.this.finish();

                }else if (firebaseAuth.getCurrentUser() != null && firebaseUser.isEmailVerified() == false){
                    Intent enter_intent = new Intent(getApplicationContext(), enter.class);
                    startActivity(enter_intent);
                    new_username.this.finish();


                }



            }
        });


    }

    private void tanımla() {
    kullanıcı_adı_secimi_edittext = (EditText)findViewById(R.id.kullanıcı_adı_secimi_edittext);
    kullanıcı_adı_belirleme_button = (Button)findViewById(R.id.kullanıcı_adı_belirleme_button);


    firebaseAuth = FirebaseAuth.getInstance();
    firebaseUser = firebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent enter_intent = new Intent(getApplicationContext(), enter.class);
        startActivity(enter_intent);
        new_username.this.finish();

    }
}
