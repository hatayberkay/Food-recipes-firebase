package something.about.hatay.hazine;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import yemek.ucuz_yemek.R;

public class splash extends AppCompatActivity  implements Animation.AnimationListener{

    ImageView splash_logo_imageview;
    TextView define_splash_textview ;

    Animation artir;
    Animation büyült;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_main);

tanımla();

        artir= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.kucuk);
       büyült= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.buyult);


        artir.setAnimationListener(this);
        büyült.setAnimationListener(this);
        splash_logo_imageview.setAnimation(artir);



        overridePendingTransition(R.anim.enter_anim,R.anim.exit_anim);

      //  define_splash_textview.setAnimation(kucuk);




    }

    private void tanımla() {
        splash_logo_imageview = (ImageView)findViewById(R.id.splash_logo_imageview);
        define_splash_textview = (TextView)findViewById(R.id.define_splash_textview);

    }


    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == artir) {

            splash_logo_imageview.setAnimation(büyült);



        }


        if (animation == büyült){

            Intent i = new Intent(getApplicationContext(),enter.class);
            splash.this.finish();
            startActivity(i);

        }


    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
