package something.about.hatay.hazine;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import something.about.hatay.hazine.enter_things.confirm_email;
import something.about.hatay.hazine.enter_things.forget_password;
import something.about.hatay.hazine.enter_things.new_username;
import something.about.hatay.hazine.enter_things.registration;
import something.about.hatay.hazine.playground.main_playground;
import yemek.ucuz_yemek.R;

public class enter extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    TextView misafir_textview, e_mail_yazısı;
    LinearLayout enter_linear_layout;

    EditText email_edittext, parola_edittext;

    Button giris_yap_button, üye_ol_button;

    TextView sifreni_mi_unuttun_textview, onay_mailini_tekrar_gonder_textview;

    private static final int RC_SIGN_IN = 1001;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private SignInButton signInButton;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);

        tanımla();

        gorsel_effect();
// hatayberkay1234@gmail.com


        if (firebaseAuth.getCurrentUser() != null ){

            Intent uye_ol_intent = new Intent(getApplicationContext(),main_playground.class);
            startActivity(uye_ol_intent);
            enter.this.finish();

        }


        onay_mailini_tekrar_gonder_textview.setVisibility(View.INVISIBLE);

        signInButton = (SignInButton) findViewById(R.id.sign_in_button);


        mAuth = FirebaseAuth.getInstance();

        //Google Sign in Options Yapılandırması
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(enter.this, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();





        giris_yap_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                giris_yap_button_islemleri();


            }


        });


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();

            }
        });


        misafir_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent uye_ol_intent = new Intent(getApplicationContext(), main_playground.class);
                startActivity(uye_ol_intent);
                enter.this.finish();



            }
        });


        üye_ol_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent uye_ol_intent = new Intent(getApplicationContext(), registration.class);
                startActivity(uye_ol_intent);
                enter.this.finish();
            }
        });


        sifreni_mi_unuttun_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent forget_password_intent = new Intent(getApplicationContext(), forget_password.class);
                startActivity(forget_password_intent);
                enter.this.finish();


            }
        });


        onay_mailini_tekrar_gonder_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent confirm_email_intent = new Intent(getApplicationContext(), confirm_email.class);
                startActivity(confirm_email_intent);
                enter.this.finish();

            }
        });

    }


    private void tanımla() {

        enter_linear_layout = (LinearLayout) findViewById(R.id.enter_linear_layout);
        misafir_textview = (TextView) findViewById(R.id.misafir_textview);


        e_mail_yazısı = (TextView) findViewById(R.id.e_mail_yazısı);


        email_edittext = (EditText) findViewById(R.id.email_edittext);
        parola_edittext = (EditText) findViewById(R.id.parola_edittext);


        giris_yap_button = (Button) findViewById(R.id.giris_yap_button);
        üye_ol_button = (Button) findViewById(R.id.üye_ol_button);


        sifreni_mi_unuttun_textview = (TextView) findViewById(R.id.sifreni_mi_unuttun_textview);
        onay_mailini_tekrar_gonder_textview = (TextView) findViewById(R.id.onay_mailini_tekrar_gonder_textview);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }


    private void giris_yap_button_islemleri() {

        final String email_string = email_edittext.getText().toString();
        String sifre_string = parola_edittext.getText().toString();

        if (!email_string.isEmpty() && !sifre_string.isEmpty() ) {
            firebaseAuth.signInWithEmailAndPassword(email_string, sifre_string).addOnCompleteListener(enter.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        String[] separated = email_string.split("@");
                        final String firebase_usernames = separated[0];





                        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler" + "/isim");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String uyelik_string = (String) dataSnapshot.getValue();


                                if (uyelik_string.equals("yok")) {

                                    Intent uye_ol_intent = new Intent(getApplicationContext(), new_username.class);
                                    startActivity(uye_ol_intent);
                                    enter.this.finish();
                                } else {
                                    Intent uye_ol_intent = new Intent(getApplicationContext(), main_playground.class);
                                    startActivity(uye_ol_intent);
                                    enter.this.finish();


                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }else {

                        Toast.makeText(enter.this, "Girdiğiniz bilgileri kontrol ediniz.", Toast.LENGTH_SHORT).show();
                        
                    }

                }
            });
        } else {

            Toast.makeText(this, "Bilgilerinizi kontrol ediniz.", Toast.LENGTH_SHORT).show();

        }


    }


    private void gorsel_effect() {
        SpannableString spannableStringObject = new SpannableString("Misafir olarak gir.");
        spannableStringObject.setSpan(new UnderlineSpan(), 0, spannableStringObject.length(), 0);

        misafir_textview.setText(spannableStringObject);


        AnimationDrawable animationDrawable = (AnimationDrawable) enter_linear_layout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }


    //Google ile Oturum acma islemleri
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In basarili oldugunda Firebase ile yetkilendir
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            } else {
                // Google Sign In hatası.
                //Log.e(TAG, "Google Sign In hatası.");
            }

        }
    }

    //GoogleSignInAccount nesnesinden ID token'ı alıp, bu Firebase güvenlik referansını kullanarak
// Firebase ile yetkilendirme işlemini gerçekleştiriyoruz
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        // Log.d(TAG, "firebaseAuthWithGooogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //             Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());


                        if (!task.isSuccessful()) {
                            //               Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(getApplicationContext(), "Yetkilendirme hatası.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(getApplicationContext(), main_playground.class));
                            finish();
                        }

                    }
                });
    }

}
