package tech.codegarage.recyclebin.util;

import android.os.Environment;

import java.io.File;

public class AllConstants {

    //Database
    public static final String REALM_NAME = "recyclebin.realm";
    public static final int REALM_SCHEMA_VERSION = 0;

    //Session
    public static final String SESSION_IS_FIRST_TIME = "SESSION_IS_FIRST_TIME";

    //Recovery service
    public static final String HIDDEN_FOLDER_NAME = ".uffJhal";
    public static final String HIDDEN_DIRECTORY = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + HIDDEN_FOLDER_NAME;
    public static final int BUFFER_SIZE = 1024;

    //Intent
    public static final int EXTRA_ACTION_START = 0;
    public static final int EXTRA_ACTION_STOP = 1;

    public static final String KEY_INTENT_EXTRA_ACTION = "KEY_INTENT_EXTRA_ACTION";
    public static final String KEY_INTENT_EXTRA_DIR_PATH = "KEY_INTENT_EXTRA_DIR_PATH";
    public static final String KEY_INTENT_EXTRA_MASK = "KEY_INTENT_EXTRA_MASK";
    public static final String KEY_INTENT_EXTRA_UPDATE = "KEY_INTENT_EXTRA_UPDATE";

    public static final String INTENT_FILTER_ACTIVITY_UPDATE = "INTENT_FILTER_ACTIVITY_UPDATE";
}
