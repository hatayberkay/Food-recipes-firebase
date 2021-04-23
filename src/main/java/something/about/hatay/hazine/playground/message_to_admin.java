package something.about.hatay.hazine.playground;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import yemek.ucuz_yemek.R;

public class message_to_admin extends AppCompatActivity {
  Button mesaj_at__baslık_ac_button;
    EditText mesaj_at_baslık_edittext ;
   TextView mesaj_at_textview ;
    ImageView mesaj_at_imageview ;

    Bitmap profil_foto_bitmap;
    public static final int GALLERY_REQUEST = 1;
    public static final int email_request = 2;
    Uri uri = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesaj_at);


// TODO: 2019-11-26 mesaj atma işlemleri gelicek. 

        tanımla();

        profil_foto_bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.arkaya);



        mesaj_at_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent ıntent = new Intent();
                ıntent.setType("image/*");
                ıntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(ıntent, GALLERY_REQUEST);
            }
        });




mesaj_at__baslık_ac_button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {



        Intent intent = new Intent(Intent.ACTION_SEND );
        intent.putExtra(Intent.EXTRA_EMAIL , new String[]{"your_mail_adress@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT , "I have a question for you?");
        intent.putExtra(Intent.EXTRA_TEXT , mesaj_at_baslık_edittext.getText().toString());
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("message/rfc822");
        startActivityForResult(Intent.createChooser(intent , "Choose Your Account : ") , email_request);





    }
});


    }

    private void tanımla() {

mesaj_at__baslık_ac_button = (Button)findViewById(R.id.mesaj_at__baslık_ac_button);
mesaj_at_baslık_edittext = (EditText)findViewById(R.id.mesaj_at_baslık_edittext);
mesaj_at_textview = (TextView)findViewById(R.id.mesaj_at_textview);
        mesaj_at_imageview = (ImageView)findViewById(R.id.mesaj_at_imageview);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();


        Intent geri_don_intent = new Intent(getApplicationContext(),main_playground.class);
        startActivity(geri_don_intent);
        message_to_admin.this.finish();

    }



    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {   //gallery için 1
            Uri path = data.getData();

          uri = path;

            mesaj_at_imageview.setImageURI(path);

            try {
                profil_foto_bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), path);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (requestCode == 2 ){


            Intent i = new Intent(getApplicationContext(),main_playground.class);
            startActivity(i);
            message_to_admin.this.finish();
        }


    }

}
