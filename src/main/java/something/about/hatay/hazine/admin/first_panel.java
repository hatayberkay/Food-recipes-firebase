package something.about.hatay.hazine.admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import something.about.hatay.hazine.playground.main_playground;
import yemek.ucuz_yemek.R;

public class first_panel extends AppCompatActivity {
    ImageView first_panel_imageview;
    TextView first_panel_textview;
    EditText first_panel_baslık_edittext;
    Button first_panel__baslık_ac_button;
    public static final int GALLERY_REQUEST = 1;
    Bitmap profil_foto_bitmap;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_panel);

        tanımla();


        profil_foto_bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.arkaya);


        // TODO: 2019-11-26 Default olarak imageview a logoyu koy. 

        first_panel_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent = new Intent();
                ıntent.setType("image/*");
                ıntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(ıntent, GALLERY_REQUEST);

            }
        });


        first_panel__baslık_ac_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String baslık_string = first_panel_baslık_edittext.getText().toString();


                databaseReference = firebaseDatabase.getReference("içerik/" + baslık_string);


                HashMap first_panel_hasmap = new HashMap();

                first_panel_hasmap.put("text_string", baslık_string);



                    first_panel_hasmap.put("foto_string", imagetostring());




                databaseReference.updateChildren(first_panel_hasmap);

                Intent main_playground_intent = new Intent(getApplicationContext(), main_playground.class);
                startActivity(main_playground_intent);
                first_panel.this.finish();


            }
        });


    }


    private void tanımla() {

        first_panel_imageview = (ImageView) findViewById(R.id.first_panel_imageview);
        first_panel_textview = (TextView) findViewById(R.id.first_panel_textview);
        first_panel_baslık_edittext = (EditText) findViewById(R.id.first_panel_baslık_edittext);
        first_panel__baslık_ac_button = (Button) findViewById(R.id.first_panel__baslık_ac_button);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        first_panel.this.finish();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {   //gallery için 1
            Uri path = data.getData();

            first_panel_imageview.setImageURI(path);



            try {
                profil_foto_bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), path);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }


    public String imagetostring() {
        ByteArrayOutputStream ByteArrayOutputStream = new ByteArrayOutputStream();

        profil_foto_bitmap.compress(Bitmap.CompressFormat.JPEG, 50, ByteArrayOutputStream);
        byte[] byt = ByteArrayOutputStream.toByteArray();
        String imagetoString = Base64.encodeToString(byt, Base64.DEFAULT);




        return imagetoString;
    }

}
