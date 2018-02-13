package com.farmarket.farmarket;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Animation animTranslate  = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.animationfile1);
        animTranslate.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) { }

            @Override
            public void onAnimationRepeat(Animation arg0) { }

            @Override
            public void onAnimationEnd(Animation arg0) {
                /*
                LoginBox.setVisibility(View.VISIBLE);
                Animation animFade  = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade);
                LoginBox.startAnimation(animFade);
                */
                /*
                Realm realm = Realm.getDefaultInstance();
                RealmResults<User> users= realm.where(User.class).findAll();
                Intent intent;
                if(users.isEmpty())
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                else
                    intent = new Intent(SplashActivity.this, MainActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
                */
                Intent intent;

                    intent = new Intent(SplashActivity.this, MainSelectionActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });
        TextView imgLogo = (TextView) findViewById(R.id.appName);
        imgLogo.startAnimation(animTranslate);

//        new BackgroundTask().execute();
    }

    private class BackgroundTask extends AsyncTask {
        Intent intent;
        private static final int SPLASH_TIME = 4080;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

           // intent = new Intent(SplashActivity.this, LoginActivity.class);
        }

        @Override
        protected Object doInBackground(Object[] params) {

            /*  Use this method to load background
            * data that your app needs. */
            try {
                Thread.sleep(SPLASH_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
//          Pass your loaded data here using Intent
//          intent.putExtra("data_key", "");
         //   startActivity(intent);
          //  finish();
        }
    }

}
