package something.about.hatay.hazine.enter_things;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import something.about.hatay.hazine.enter;
import yemek.ucuz_yemek.R;

public class forget_password extends AppCompatActivity {
FirebaseAuth firebaseAuth;
    EditText email_reset_edittext;
    Button gonder_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        tanımla();


        firebaseAuth = FirebaseAuth.getInstance();



        gonder_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e_mail_string = email_reset_edittext.getText().toString();
                if (!e_mail_string.isEmpty())

                {
                    reset_mail_password();
                    Intent i = new Intent(getApplicationContext(),enter.class);
                    startActivity(i);
                    forget_password.this.finish();



                }

                if (e_mail_string.isEmpty()){

                    Toast.makeText(forget_password.this, "Lütfen E- mail adresinizi yazınız.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void tanımla() {

        email_reset_edittext = (EditText)findViewById(R.id.email_reset_forget_password_edittext);
        gonder_button = (Button)findViewById(R.id.gonder_forget_password_button);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent enter_intent = new Intent(getApplicationContext(), enter.class);
        startActivity(enter_intent);
        forget_password.this.finish();

    }



    private void reset_mail_password() {
        String e_mail_string = email_reset_edittext.getText().toString();
        try {
            FirebaseAuth.getInstance().sendPasswordResetEmail(e_mail_string).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(forget_password.this, "Sıfırlama mailiniz başarıyla gönderildi.", Toast.LENGTH_SHORT).show();
                        email_reset_edittext.setText("");

                    }else {
                        Toast.makeText(forget_password.this, "Sıfırlama mailiniz başarısız oldu.", Toast.LENGTH_SHORT).show();
                        email_reset_edittext.setText("");
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(forget_password.this, "Sıfırlama mailiniz başarısız oldu.", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(),enter.class);
            startActivity(i);
            forget_password.this.finish();
        }


    }
}
