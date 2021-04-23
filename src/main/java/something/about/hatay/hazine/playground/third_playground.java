package something.about.hatay.hazine.playground;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import something.about.hatay.hazine.model.third_comment_object;
import yemek.ucuz_yemek.R;

public class third_playground extends AppCompatActivity  {
    Custom_listview_comment custom_listview_comment;


ListView comment_litview;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private static final String TAG = "third_playground";

  TextView  third_textview;
  ImageView third_imageview , mesaj_at_imageview , kalem_imageview;

    ArrayList<String> third_foto_array = new ArrayList<>();

    ArrayList<String> third_text_array = new ArrayList<>();

    ArrayList<String> adress_array = new ArrayList<>();

    ArrayList<third_comment_object> third_comment_object_array = new ArrayList<>();

FirebaseAuth firebaseAuth;
FirebaseUser firebaseUser;

    InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_playground);

        tanımla();

        custom_listview_comment = new Custom_listview_comment();

       mInterstitialAd = new InterstitialAd(getApplicationContext());


        mInterstitialAd.setAdUnitId("Sample ad ID"); //reklam id

        requestNewInterstitial();


        Bundle bundle = getIntent().getExtras();

        final String anahtar_first_string = bundle.getString("anahtar_first");
        final String anahtar_second_string = bundle.getString("third_anahtar");










// TODO: 2019-11-30 Sayfaya girince göstersin.
     /*   if (mInterstitialAd.isLoaded()) { //reklam yüklenmişse
            mInterstitialAd.show(); //reklam gösteriliyor


        }else{


        }
*/



        if (firebaseAuth.getCurrentUser() == null){


            mesaj_at_imageview.setVisibility(View.INVISIBLE);
            kalem_imageview.setVisibility(View.INVISIBLE);

        }



        üst_kısım(anahtar_first_string , anahtar_second_string);

   

        kalem_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                custom_dialog_isleri(v);




            }
            private void custom_dialog_isleri(View v) {
                final Dialog dialog = new Dialog(v.getContext());

                dialog.setContentView(R.layout.custom_yorum_yaz);


                TextView yorum_yaz_textview = (TextView) dialog.findViewById(R.id.yorum_yaz_textview);
                ImageView yorum_imageview = (ImageView) dialog.findViewById(R.id.yorum_imageview);



                final EditText yorum_alanı_edittext = (EditText) dialog.findViewById(R.id.yorum_alanı_edittext);
                Button paylaş_button = (Button) dialog.findViewById(R.id.paylaş_button);




                paylaş_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        // TODO: 2019-11-26 İsminle yorum yapmak.


                        String[] separated = firebaseUser.getEmail().split("@");
                        final String firebase_usernames = separated[0];



                        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler/" +"isim" );



                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                String kullanıcı_ismi = (String) dataSnapshot.getValue();

                                databaseReference = firebaseDatabase.getReference("içerik/" + anahtar_first_string + "/" + anahtar_second_string + "/"+
                                        adress_array.get(0).toString() + "/yorumlar/").push();

                                HashMap yorum_koy_hasmap = new HashMap();

                                yorum_koy_hasmap.put("comment_isim_string" , kullanıcı_ismi);
                                yorum_koy_hasmap.put("comment_string" , yorum_alanı_edittext.getText().toString());


                                databaseReference.updateChildren(yorum_koy_hasmap);

                                dialog.dismiss();




                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });







                    }
                });

                dialog.show();
            }
        });


        mesaj_at_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: 2019-11-30 Mesaj at tıklayınca göstersin.
           //    Intent mesaj_at_intent = new Intent(getApplicationContext(),message_to_admin.class);
             //  startActivity(mesaj_at_intent);




                String message = "Karnını doyurmak için birebir.Tavsiye ederim. Google play : Ucuz yemek." ;
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);

                startActivity(Intent.createChooser(share, "Paylaş beni :)"));



             /*   if (mInterstitialAd.isLoaded()) { //reklam yüklenmişse
                    mInterstitialAd.show(); //reklam gösteriliyor

                }else{

                }


                mInterstitialAd.setAdListener(new AdListener() { //reklamımıza listener ekledik ve kapatıldığında haberimiz olacak
                    @Override
                    public void onAdClosed() {

                        Intent who_are = new Intent(getApplicationContext(), message_to_admin.class);
                        startActivity(who_are);
                    }
                });*/


            }
        });


        comment_litview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {






            }
        });

    }
    private void üst_kısım(final String anahtar_first_string , final String anahtar_second_string ) {



        databaseReference = firebaseDatabase.getReference("içerik/" + anahtar_first_string + "/" + anahtar_second_string );

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                third_foto_array.clear();
                third_text_array.clear();




                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {



                    String third_text_string = snapshot.child("third_text_string").getValue(String.class);

                    String third_foto_string = snapshot.child("third_foto_string").getValue(String.class);




                    if (third_text_string != null) {

                        third_text_array.add(third_text_string);

                    }

                    if (third_foto_string != null) {

                        third_foto_array.add(third_foto_string);



                    }



                    String the_key_string = snapshot.getKey();

                    adress_array.add(the_key_string);





                    Log.i(TAG, "wwwww: " + third_text_array);


                }


                if (third_text_array.size() > 0) {
                    third_textview.setText(third_text_array.get(0).toString());

                    third_imageview.setImageBitmap(StringToBitMap(third_foto_array.get(0).toString()));

                }


                // TODO: 2019-11-27 İçerik boşsa yorum yapılamasın diye koyduk.
                if (third_textview.getText().toString().contains("Neo4j")){


                    mesaj_at_imageview.setVisibility(View.INVISIBLE);
                    kalem_imageview.setVisibility(View.INVISIBLE);



                }



                yorum_koyma(anahtar_first_string,anahtar_second_string);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




    private void yorum_koyma(String anahtar_first_string,String anahtar_second_string ) {


        databaseReference = firebaseDatabase.getReference("içerik/" + anahtar_first_string + "/" + anahtar_second_string + "/"+
                adress_array.get(0).toString()   + "/yorumlar/");



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                third_comment_object_array.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){



                    Log.i(TAG, "cvcvccvcvcv: " + snapshot.getValue());

                    third_comment_object third_comment_objects = snapshot.getValue(third_comment_object.class);

                    third_comment_object_array.add(third_comment_objects);



                }

                custom_listview_comment.notifyDataSetChanged();


                comment_litview.setAdapter(custom_listview_comment);

                final Animation slayt_asagi= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.asagi);


                comment_litview.startAnimation(slayt_asagi);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private void tanımla() {
      comment_litview = (ListView)findViewById(R.id.comment_litview);
        third_textview = (TextView) findViewById(R.id.third_textview);
        third_imageview = (ImageView) findViewById(R.id.third_imageview);
        mesaj_at_imageview = (ImageView) findViewById(R.id.mesaj_at_imageview);
        kalem_imageview = (ImageView) findViewById(R.id.kalem_imageview);






        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        
        
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

    }

  


    class Custom_listview_comment extends BaseAdapter {


        @Override
        public int getCount() {
            return third_comment_object_array.size();
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
            convertView = getLayoutInflater().inflate(R.layout.custom_listview_comment,null);

            TextView isim_textview = (TextView) convertView.findViewById(R.id.kullanıcı_adı);
            TextView comment_comment_textview = (TextView) convertView.findViewById(R.id.comment_comment_textview);


         isim_textview.setText(third_comment_object_array.get(position).getComment_isim_string());
          comment_comment_textview.setText(third_comment_object_array.get(position).getComment_string());

            return convertView ;
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
        super.onBackPressed();
        Intent third_intent = new Intent(getApplicationContext(),main_playground.class);
        startActivity(third_intent);
        third_playground.this.finish();



    }


    private void  requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()

                .build();

        mInterstitialAd.loadAd(adRequest);
    }



}

//text.setVisibility(View.INVISIBLE);

               /* ViewGroup.LayoutParams params = comment_litview.getLayoutParams();
                params.height = (300);
                params.width = (400);
                comment_litview.setLayoutParams(params);*/
//  text.setMovementMethod(new ScrollingMovementMethod());