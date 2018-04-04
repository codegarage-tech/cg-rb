package tech.codegarage.recyclebin.model.realm;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.reversecoder.library.storage.SessionManager;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import tech.codegarage.recyclebin.application.RecycleBinApp;
import tech.codegarage.recyclebin.enumeration.TagType;
import tech.codegarage.recyclebin.model.DataTag;

import static tech.codegarage.recyclebin.util.AllConstants.SESSION_DATA_TAGS;

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

    public static RealmController with(Context context) {
        if (instance == null) {
            instance = new RealmController((Application) context.getApplicationContext());
        }
        return instance;
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

    public void refresh() {
        realm.refresh();
    }

    public void destroyRealm() {
        if (!realm.isClosed()) {
            realm.close();
        }
    }

    /***************
     * Tag handler *
     ***************/
    public void clearAllTags() {
        realm.beginTransaction();
        realm.delete(Tag.class);
        realm.commitTransaction();
    }

    public List<Tag> getTags() {
        return realm.copyFromRealm(getResultTags());
    }

    public RealmResults<Tag> getResultTags() {
        return realm.where(Tag.class).findAll();
    }

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

    public boolean hasTags() {
        return realm.where(Tag.class).findAll().size() > 0;
    }

    public RealmResults<Tag> queryedTags() {
        return realm.where(Tag.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();
    }

    public boolean setTags() {
        ArrayList<Tag> tags = new ArrayList<>();
        Tag mTag;
        for (TagType tagType : TagType.values()) {
            Log.d(TAG, "tag is: " + tagType.name());
            mTag = new Tag(tagType.name());
            if (!isTagExist(mTag)) {
                Log.d(TAG, tagType.name() + " is not exist.");
                getRealm().beginTransaction();
                getRealm().copyToRealm(mTag);
                getRealm().commitTransaction();
            }

            if (isTagExist(mTag)) {
                Log.d(TAG, "Tag exist: " + mTag.toString());
                tags.add(mTag);

                if (onRealmDataChangeListener != null) {
                    onRealmDataChangeListener.onInsert(mTag);
                    Log.d(TAG, "Tag is listening: " + mTag.toString());
                }
            }
        }

        //Save tags into session
        DataTag dataTag = new DataTag(tags);
        SessionManager.setStringSetting(RecycleBinApp.getGlobalContext(), SESSION_DATA_TAGS, DataTag.convertFromObjectToString(dataTag));

        return true;
    }

    /*************************
     * Recovery file handler *
     *************************/
    public void setRecoveryFileInfo(RecoveryFileInfo recoveryFileInfo){
        if (!isRecoveryFileInfoExist(recoveryFileInfo)) {
            Log.d(TAG, recoveryFileInfo.getOriginFileName() + " is not exist.");
            getRealm().beginTransaction();
            getRealm().copyToRealm(recoveryFileInfo);
            getRealm().commitTransaction();
        }

        if (isRecoveryFileInfoExist(recoveryFileInfo)) {
            Log.d(TAG, "RecoveryFileInfo exist: " + recoveryFileInfo.toString());

            if (onRealmDataChangeListener != null) {
                onRealmDataChangeListener.onInsert(recoveryFileInfo);
                Log.d(TAG, "RecoveryFileInfo is listening: " + recoveryFileInfo.toString());
            }
        }
    }

    public RecoveryFileInfo getRecoveryFileInfo(RecoveryFileInfo recoveryFileInfo) {
        return realm.where(RecoveryFileInfo.class).equalTo("originFilePath", recoveryFileInfo.getOriginFilePath()).findFirst();
    }

    public boolean isRecoveryFileInfoExist(RecoveryFileInfo recoveryFileInfo) {
        if (realm.where(RecoveryFileInfo.class).equalTo("originFilePath", recoveryFileInfo.getOriginFilePath()).findFirst() != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasRecoveryFileInfo() {
        return realm.where(RecoveryFileInfo.class).findAll().size() > 0;
    }
}