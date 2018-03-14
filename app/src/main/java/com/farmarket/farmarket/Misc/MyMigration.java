package com.farmarket.farmarket.Misc;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;

/**
 * Created by admin on 14/03/2018.
 */

public class MyMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        if(oldVersion < 1)
        {
            RealmConfiguration configuration = new RealmConfiguration.Builder().build();
            Realm.deleteRealm(configuration);
        }
    }
}
