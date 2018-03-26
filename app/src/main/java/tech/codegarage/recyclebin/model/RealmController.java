package tech.codegarage.recyclebin.model;


import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {
        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {
        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {
        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {
        return instance;
    }

    public Realm getRealm() {
        return realm;
    }

    //Refresh the realm istance
    public void refresh() {
        realm.refresh();
    }

    //clear all objects from Tag.class
    public void clearAll() {
        realm.beginTransaction();
        realm.delete(Tag.class);
        realm.commitTransaction();
    }

    //find all objects in the Tag.class
    public List<Tag> getTags() {
        return realm.copyFromRealm(getResultTags());
    }

    public RealmResults<Tag> getResultTags() {
        return realm.where(Tag.class).findAll();
    }

    //query a single item with the given id
    public Tag getTag(String name) {
        return realm.where(Tag.class).equalTo("name", name).findFirst();
    }

    //check if Tag.class is empty
    public boolean hasTags() {
        return realm.where(Tag.class).findAll().size() > 0;
    }

    //query example
    public RealmResults<Tag> queryedTags() {
        return realm.where(Tag.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();
    }
}