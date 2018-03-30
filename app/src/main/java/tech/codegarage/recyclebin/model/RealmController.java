package tech.codegarage.recyclebin.model;


import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class RealmController {

    private static String TAG = RealmController.class.getSimpleName();
    private static RealmController instance;
    private final Realm realm;
    private onRealmDataChangeListener onRealmDataChangeListener = null;

    public interface onRealmDataChangeListener<T extends RealmObject> {
        public void onInsert(T realmModel);

        public void onUpdate(T realmModel);

        public void onDelete(T realmModel);
    }

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

    public RealmController.onRealmDataChangeListener getOnRealmDataChangeListener() {
        return onRealmDataChangeListener;
    }

    public void setOnRealmDataChangeListener(RealmController.onRealmDataChangeListener onRealmDataChangeListener) {
        this.onRealmDataChangeListener = onRealmDataChangeListener;
    }

    public Realm getRealm() {
        return realm;
    }

    //Refresh the realm istance
    public void refresh() {
        realm.refresh();
    }

    //clear all objects from Tag.class
    public void clearAllTags() {
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
    public Tag getTag(Tag tag) {
        return realm.where(Tag.class).equalTo("name", tag.getName()).findFirst();
    }

    public boolean isTagExist(Tag tag) {
        if (realm.where(Tag.class).equalTo("name", tag.getName()).findFirst() != null) {
            return true;
        } else {
            return false;
        }
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

    public void setTags() {
        ArrayList<Tag> tags = new ArrayList<>();
        Tag tag = new Tag("Video");
        tags.add(tag);
        tag = new Tag("Audio");
        tags.add(tag);
        tag = new Tag("Image");
        tags.add(tag);
        tag = new Tag("Document");
        tags.add(tag);

        for (Tag mTag : tags) {
            if (!isTagExist(mTag)) {
                getRealm().beginTransaction();
                getRealm().copyToRealm(mTag);
                getRealm().commitTransaction();

                if (isTagExist(mTag)) {
                    if (onRealmDataChangeListener != null) {
                        onRealmDataChangeListener.onInsert(mTag);
                    }
                }

//                inputTag.addChangeListener(new RealmObjectChangeListener<RealmModel>() {
//                    @Override
//                    public void onChange(RealmModel realmModel, @Nullable ObjectChangeSet changeSet) {
//                        if (!changeSet.isDeleted() && realmModel != null) {
//                            if (onRealmDataChangeListener != null) {
//                                Tag insertedTag = (Tag)realmModel;
//                                onRealmDataChangeListener.onInsert(insertedTag);
//                            }
//                        }
//                    }
//                });
            } else {
                Log.d(TAG, "Tag data already exist.");
            }
        }
    }

    public void destroyRealm() {
        realm.close();
    }
}