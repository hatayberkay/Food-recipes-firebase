package something.about.hatay.hazine.playground;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import yemek.ucuz_yemek.R;

public class second_playground extends AppCompatActivity {
    custom_listview_second custom_listview_second;

    ListView second_playground_listview;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    ArrayList<String> third_anahtar_array = new ArrayList<>();

    ArrayList<String> second_foto_string_array = new ArrayList<>();


    private static final String TAG = "second_playground";


    InterstitialAd mInterstitialAd;


    @Override
    protected void onResume() {
        super.onResume();
        requestNewInterstitial();
    }

    @Override
    protected void onStart() {
        super.onStart();


        requestNewInterstitial();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_playground);

        tanımla();



        mInterstitialAd = new InterstitialAd(getApplicationContext());


        mInterstitialAd.setAdUnitId("Sample İd"); //reklam id

        requestNewInterstitial();








        custom_listview_second = new custom_listview_second();

        Bundle bundle = getIntent().getExtras();

        final String anahtar_first_string = bundle.getString("anahtar_first");


        LayoutAnimationController lac = new LayoutAnimationController(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate), 0.5f); //0.5f == time between appearance of listview items.
        second_playground_listview.setLayoutAnimation(lac);


        second_playground_listview.startLayoutAnimation();


        yerine_koy(anahtar_first_string);


        // TODO: 2020-02-09  Reklam koyuldu. 

        second_playground_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                if (mInterstitialAd.isLoaded()) { //reklam yüklenmişse
                    mInterstitialAd.show(); //reklam gösteriliyor
                }else{


                    databaseReference = firebaseDatabase.getReference("içerik/" + anahtar_first_string + "/" + third_anahtar_array.get(position).toString() );
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {








                            Intent second_playground_intent = new Intent(getApplicationContext(),third_playground.class);
                            second_playground_intent.putExtra("third_anahtar", third_anahtar_array.get(position).toString());
                            second_playground_intent.putExtra("anahtar_first", anahtar_first_string);
                            startActivity(second_playground_intent);




                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

                mInterstitialAd.setAdListener(new AdListener() { //reklamımıza listener ekledik ve kapatıldığında haberimiz olacak
                    @Override
                    public void onAdClosed() {



                        databaseReference = firebaseDatabase.getReference("içerik/" + anahtar_first_string + "/" + third_anahtar_array.get(position).toString() );
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {








                                Intent second_playground_intent = new Intent(getApplicationContext(),third_playground.class);
                                second_playground_intent.putExtra("third_anahtar", third_anahtar_array.get(position).toString());
                                second_playground_intent.putExtra("anahtar_first", anahtar_first_string);
                                startActivity(second_playground_intent);




                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });





                    }
                });







            }
        });

    }

    private void yerine_koy(String anahtar_first_string) {


        databaseReference = firebaseDatabase.getReference("içerik/" + anahtar_first_string);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                third_anahtar_array.clear();
                second_foto_string_array.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String second_text_string = snapshot.child("second_text_string").getValue(String.class);

                    String second_foto_string = snapshot.child("second_foto_string").getValue(String.class);

                    if (second_text_string != null) {

                        third_anahtar_array.add(second_text_string);
                    }

                    if (second_foto_string != null) {

                        second_foto_string_array.add(second_foto_string);

                        Log.i(TAG, "qweqwewqeqwe: " + second_foto_string_array);


                    }


                }

                custom_listview_second.notifyDataSetChanged();


                second_playground_listview.setAdapter(custom_listview_second);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void tanımla() {
        second_playground_listview = (ListView) findViewById(R.id.second_playground_listview);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


    class custom_listview_second extends BaseAdapter {
        @Override
        public int getCount() {
            return third_anahtar_array.size();

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
            convertView = getLayoutInflater().inflate(R.layout.custom_listview_second, null);
            TextView isim_textview = (TextView) convertView.findViewById(R.id.secondss_object_textview);
            ImageView second_object_imagview = (ImageView) convertView.findViewById(R.id.second_object_imagview);


            second_object_imagview.setImageBitmap(StringToBitMap(second_foto_string_array.get(position)));
            isim_textview.setText(third_anahtar_array.get(position).toString());


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


    private void requestNewInterstitial() { //Test cihazı ekliyoruz Admob dan ban yememek için
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent main_playground_intent = new Intent(getApplicationContext(),main_playground.class);

        startActivity(main_playground_intent);
        second_playground.this.finish();



    }
}
