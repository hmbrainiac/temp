package com.farmarket.farmarket.Misc;

/**
 * Created by admin on 19/02/2018.
 */

        import android.app.Application;

        import io.realm.Realm;
        import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    private static MyApplication mInstance;
    public RealmConfiguration realmConfiguration;
    @Override
    public void onCreate() {

        super.onCreate();
        Realm.init(getApplicationContext());

       // Realm.setDefaultConfiguration(realmConfiguration);

    }

    public static synchronized MyApplication getInstance()
    {
        return mInstance;
    }
}