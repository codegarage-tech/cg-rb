package tech.codegarage.recyclebin.application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static tech.codegarage.recyclebin.util.AllConstants.REALM_NAME;
import static tech.codegarage.recyclebin.util.AllConstants.REALM_SCHEMA_VERSION;

public class RecycleBinApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Realm Database
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(REALM_NAME)
                .schemaVersion(REALM_SCHEMA_VERSION)
//                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}