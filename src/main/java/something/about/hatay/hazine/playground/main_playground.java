package something.about.hatay.hazine.playground;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import something.about.hatay.hazine.admin.admin_choosing_paper;
import something.about.hatay.hazine.enter;
import something.about.hatay.hazine.enter_things.new_username;
import something.about.hatay.hazine.enter_things.registration;
import something.about.hatay.hazine.model.main_object;
import yemek.ucuz_yemek.R;

public class main_playground extends AppCompatActivity {
    GridView main_gridview;
    Button yeni_veri_gir_button;
    custom_main_playground custom_main_playground;

    ArrayList<main_object> main_objects = new ArrayList<>();

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

    private static final String TAG = "main_playground";

    InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_playground);

        tanımla();

        custom_main_playground = new custom_main_playground();


        LayoutAnimationController lac = new LayoutAnimationController(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha), 0.5f); //0.5f == time between appearance of listview items.
        main_gridview.setLayoutAnimation(lac);

        yeni_veri_gir_button.setVisibility(View.INVISIBLE);




        mInterstitialAd = new InterstitialAd(getApplicationContext());



        mInterstitialAd.setAdUnitId("Sample add İd"); //reklam id



        requestNewInterstitial();





        // hatayberkay1234@gmail.com

// TODO: 2019-11-27 Misafir olup olmadıgını anlarız.
        if (firebaseAuth.getCurrentUser() == null){

    databaseReference = firebaseDatabase.getReference("içerik/");


            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    main_objects.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        main_object main_objects_items = snapshot.getValue(main_object.class);

                        main_objects.add(main_objects_items);

                        Log.i(TAG, "onDataChange: " + main_objects_items);
                    }


                    custom_main_playground.notifyDataSetChanged();


                    main_gridview.setAdapter(custom_main_playground);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            new AlertDialog.Builder(this)
                    .setTitle("Başlık")
                    .setIcon(R.drawable.arkaya)
                    .setMessage("Uye olmayı düşünür müydük?")
                    .setNegativeButton("Belki daha sonra", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();


                            Toast.makeText(main_playground.this, "Siz bilirsiniz.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setPositiveButton("Tabiki", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            
                            Intent uye_ol_intent = new Intent(getApplicationContext(), registration.class);
                            startActivity(uye_ol_intent);
                            main_playground.this.finish();
                            
                            
                        }
                    })
                    
                   .show();



            

        }else {

            String[] separated = firebaseUser.getEmail().split("@");
            final String firebase_usernames = separated[0];


            databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler" + "/isim");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String uyelik_string = (String) dataSnapshot.getValue();


                    if (uyelik_string == null){

          Intent uye_ol_intent = new Intent(getApplicationContext(), new_username.class);
                        startActivity(uye_ol_intent);
                        main_playground.this.finish();
                    }


                    else  if (uyelik_string.equals("yok")) {

                        Intent uye_ol_intent = new Intent(getApplicationContext(), new_username.class);
                        startActivity(uye_ol_intent);
                        main_playground.this.finish();
                    }
                    else {



                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            // TODO: 2019-11-27 Admin gmaili.    emre18aydemir@gmail.com
            if (firebase_usernames.equals("hatayberkay1234")) {

                yeni_veri_gir_button.setVisibility(View.VISIBLE);

            }


            databaseReference = firebaseDatabase.getReference("içerik/");


            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    main_objects.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        main_object main_objects_items = snapshot.getValue(main_object.class);

                        main_objects.add(main_objects_items);

                        Log.i(TAG, "onDataChange: " + main_objects_items);
                    }


                    custom_main_playground.notifyDataSetChanged();


                    main_gridview.setAdapter(custom_main_playground);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        }


// TODO: 2020-02-09 Reklam koyuldu.

        main_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {



                requestNewInterstitial();
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();

                } else {
                    Log.d("saaa", "The interstitial wasn't loaded yet.");




                    Intent second_playground_intent = new Intent(getApplicationContext(), second_playground.class);
                    second_playground_intent.putExtra("anahtar_first", main_objects.get(position).getText_string());
                    startActivity(second_playground_intent);
                }


                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        // Load the next interstitial.


                        Intent second_playground_intent = new Intent(getApplicationContext(), second_playground.class);
                        second_playground_intent.putExtra("anahtar_first", main_objects.get(position).getText_string());
                        startActivity(second_playground_intent);
                    }

                });






            }
        });


        yeni_veri_gir_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Intent i = new Intent(getApplicationContext(), admin_choosing_paper.class);
                startActivity(i);


            }
        });

    }

    private void tanımla() {

        main_gridview = (GridView) findViewById(R.id.main_gridview);
        yeni_veri_gir_button = (Button) findViewById(R.id.yeni_veri_gir_button);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    }


    class custom_main_playground extends BaseAdapter {
        @Override
        public int getCount() {
            return main_objects.size();

        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.custom_main_playground, null);
            ImageView imageview = (ImageView) convertView.findViewById(R.id.imageview_bir);
            TextView isim_textview = (TextView) convertView.findViewById(R.id.isim_textview);

            imageview.setImageBitmap(StringToBitMap(main_objects.get(position).getFoto_string()));
            isim_textview.setText(main_objects.get(position).getText_string());


            return convertView;
        }
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle("Çıkmak istiyor musunuz?")
                .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .setMessage("Oturumunuz kapatılacaktır.")
                .setNeutralButton(
                        "Evet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                firebaseAuth.signOut();

                                Intent enter_intent = new Intent(getApplicationContext(), enter.class);
                                startActivity(enter_intent);
                                main_playground.this.finish();

                            }
                        }).show();


    }

    private void  requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()

                .build();

        mInterstitialAd.loadAd(adRequest);
    }

}
