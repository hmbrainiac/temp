package com.farmarket.farmarket.Misc;

/**
 * Created by admin on 19/02/2018.
 */

        import android.app.Application;

        import io.realm.Realm;
        import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    private static MyApplication mInstance;
    public RealmConfiguration config;
    @Override
    public void onCreate() {

        super.onCreate();
        Realm.init(getApplicationContext());

        config = new RealmConfiguration.Builder()
                .schemaVersion(1) // Must be bumped when the schema changes
                .migration(new MyMigration()) // Migration to run
                .build();
        Realm.setDefaultConfiguration(config);

    }

    public static synchronized MyApplication getInstance()
    {
        return mInstance;
    }
}