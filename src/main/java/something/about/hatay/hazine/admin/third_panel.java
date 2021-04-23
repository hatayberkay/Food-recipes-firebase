package something.about.hatay.hazine.admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class third_panel extends AppCompatActivity {
    TextView third_panel_son_textview, third_panel_mutlak_textview, third_panel_galeri_textview;

    Spinner third_panel_son_spinner, third_panel_en_son_spinner;

    ImageView third_panel_imageview;

    EditText third_panel_metin_edittext;

    Button third_panel_son_button;

    Bitmap profil_foto_bitmap;

    public static final int GALLERY_REQUEST = 3;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayAdapter adapter;

    ArrayAdapter adapter_last;

    private static final String TAG = "third_panel";

    ArrayList<String> third_panel_array = new ArrayList<>();

    ArrayList<String> third_panel_en_son_array = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_panel);

        tanımla();




        profil_foto_bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.arkaya);

        third_panel_en_son_spinner.setVisibility(View.INVISIBLE);

        third_panel_galeri_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent = new Intent();
                ıntent.setType("image/*");
                ıntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(ıntent, GALLERY_REQUEST);

            }
        });

        // TODO: 2019-11-13 Normal bir şekilde ilk spinnera itemleri koy.

        databaseReference = firebaseDatabase.getReference("içerik/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String items_of_spinner = snapshot.child("text_string").getValue(String.class);

                    third_panel_array.add(items_of_spinner);


                    //   Log.i(TAG, "onDataChange: " + items_of_spinner);

                    adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, third_panel_array);

                    third_panel_son_spinner.setAdapter(adapter);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // TODO: 2019-11-13 En son spinnera itemleri koy. Burada ilk ve son itemi çıkardık object isimlerini item olarak görmesin diye.

        third_panel_son_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                third_panel_en_son_spinner.setVisibility(View.VISIBLE);

                String secilmis_spinner_string = third_panel_son_spinner.getSelectedItem().toString();


                databaseReference = firebaseDatabase.getReference("içerik/" + secilmis_spinner_string + "/");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        third_panel_en_son_array.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                            Log.i(TAG, "merhaba: " + snapshot.getKey());


                            third_panel_en_son_array.add(snapshot.getKey());


                            // FIXME: 2019-11-13 Tedbir olarak koyulmuştur.
/*

                            if (third_panel_en_son_array.get(0).toString().equals("foto_string")) {


                                third_panel_en_son_array.remove("foto_string");

                            }

*/

                            third_panel_en_son_array.remove("text_string");
                            third_panel_en_son_array.remove("foto_string");


                            adapter_last = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, third_panel_en_son_array);

                            third_panel_en_son_spinner.setAdapter(adapter_last);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // TODO: 2019-11-13 Button işleri.
        third_panel_son_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String alt_baslık_string = third_panel_son_spinner.getSelectedItem().toString();

                String en_alt_baslık_string = third_panel_en_son_spinner.getSelectedItem().toString();

                databaseReference = firebaseDatabase.getReference("içerik/" + alt_baslık_string + "/" + en_alt_baslık_string + "/").push();

                HashMap first_panel_hasmap = new HashMap();

                first_panel_hasmap.put("third_foto_string", imagetostring());
                first_panel_hasmap.put("third_text_string", third_panel_metin_edittext.getText().toString());


                databaseReference.updateChildren(first_panel_hasmap);

                Intent main_playground_intent = new Intent(getApplicationContext(), main_playground.class);
                startActivity(main_playground_intent);
                third_panel.this.finish();


            }
        });


    }

    private void tanımla() {

        third_panel_son_textview = (TextView) findViewById(R.id.third_panel_son_textview);
        third_panel_mutlak_textview = (TextView) findViewById(R.id.third_panel_mutlak_textview);
        third_panel_galeri_textview = (TextView) findViewById(R.id.third_panel_galeri_textview);

        third_panel_son_spinner = (Spinner) findViewById(R.id.third_panel_son_spinner);
        third_panel_en_son_spinner = (Spinner) findViewById(R.id.third_panel_en_son_spinner);

        third_panel_imageview = (ImageView) findViewById(R.id.third_panel_imageview);

        third_panel_metin_edittext = (EditText) findViewById(R.id.third_panel_metin_edittext);

        third_panel_son_button = (Button) findViewById(R.id.third_panel_son_button);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        third_panel.this.finish();
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

            third_panel_imageview.setImageURI(path);


            try {
                profil_foto_bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), path);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

}
