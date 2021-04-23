package something.about.hatay.hazine.admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import something.about.hatay.hazine.playground.main_playground;
import yemek.ucuz_yemek.R;

public class second_panel extends AppCompatActivity {
TextView second_panel_alt_baslık_textview , second_panel_galeri_textview , second_panel_alt_konu_textview;
Spinner second_panel_alt_baslık_spinner;
ImageView second_panel_imageview;
EditText second_panel_konu_edittext;
Button second_panel_alt_baslık_button;
Bitmap profil_foto_bitmap;
    public static final int GALLERY_REQUEST = 2;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private static final String TAG = "second_panel";

    ArrayList<String> second_panel_array = new ArrayList<>();
    ArrayAdapter adapter;

    String first_panel_adresi_string = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_panel);

        tanımla();



        profil_foto_bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.arkaya);

        databaseReference = firebaseDatabase.getReference("içerik/" );

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String items_of_spinner = snapshot.child("text_string").getValue(String.class);

                    second_panel_array.add(items_of_spinner);


                 //   Log.i(TAG, "onDataChange: " + items_of_spinner);

                    adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1,  second_panel_array);

                    second_panel_alt_baslık_spinner.setAdapter(adapter);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        second_panel_galeri_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent = new Intent();
                ıntent.setType("image/*");
                ıntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(ıntent, GALLERY_REQUEST);

            }
        });




        second_panel_alt_baslık_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        String alt_baslık_string = second_panel_konu_edittext.getText().toString();

        String spinner_string =     second_panel_alt_baslık_spinner.getSelectedItem().toString();


        databaseReference = firebaseDatabase.getReference("içerik/" + spinner_string + "/" + alt_baslık_string);

                HashMap first_panel_hasmap = new HashMap();

                first_panel_hasmap.put("second_text_string", alt_baslık_string);
                first_panel_hasmap.put("second_foto_string", imagetostring());


                databaseReference.updateChildren(first_panel_hasmap);

                Intent main_playground_intent = new Intent(getApplicationContext(), main_playground.class);
                startActivity(main_playground_intent);
                second_panel.this.finish();


            }
        });

    }

    private void tanımla() {

        second_panel_alt_baslık_textview = (TextView)findViewById(R.id.second_panel_alt_baslık_textview);
        second_panel_galeri_textview = (TextView)findViewById(R.id.second_panel_galeri_textview);
        second_panel_alt_konu_textview = (TextView)findViewById(R.id.second_panel_alt_konu_textview);


        second_panel_alt_baslık_spinner = (Spinner) findViewById(R.id.second_panel_alt_baslık_spinner);

        second_panel_imageview = (ImageView) findViewById(R.id.second_panel_imageview);

        second_panel_konu_edittext = (EditText) findViewById(R.id.second_panel_konu_edittext);

        second_panel_alt_baslık_button = (Button) findViewById(R.id.second_panel_alt_baslık_button);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        second_panel.this.finish();
    }

    public String imagetostring() {
        ByteArrayOutputStream ByteArrayOutputStream = new ByteArrayOutputStream();

        profil_foto_bitmap.compress(Bitmap.CompressFormat.JPEG, 50, ByteArrayOutputStream);
        byte[] byt = ByteArrayOutputStream.toByteArray();
        String imagetoString = Base64.encodeToString(byt, Base64.DEFAULT);

        return imagetoString;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {   //gallery için 1
            Uri path = data.getData();

            second_panel_imageview.setImageURI(path);


            try {
                profil_foto_bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), path);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }



    }


}
