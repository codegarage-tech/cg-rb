package tech.codegarage.recyclebin.util;

import android.app.ActivityManager;
import android.content.Context;
import android.os.FileObserver;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import tech.codegarage.recyclebin.enumeration.TagType;
import tech.codegarage.recyclebin.model.realm.Tag;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class FileManager {

    public static boolean isNullOrEmpty(String myString) {
        return myString == null ? true : myString.length() == 0 || myString.equalsIgnoreCase("null") || myString.equalsIgnoreCase("");
    }

    public static String getTagName(Class<?> cls) {
        return cls.getSimpleName();
    }

    public static String printFileObserverMessage(int event, String path) {
        String message = "No message";
        switch (event) {
            case FileObserver.ATTRIB:
                message = "event = " + "File Attrib" + "\npath = " + path;
                break;
            case FileObserver.CREATE:
                message = "event = " + "File Created" + "\npath = " + path;
                break;
            case FileObserver.DELETE:
                message = "event = " + "File Deleted" + "\npath = " + path;
                break;
            case FileObserver.DELETE_SELF:
                message = "event = " + "File Deleted Self" + "\npath = " + path;
                break;
            case FileObserver.MODIFY:
                message = "event = " + "File Modified" + "\npath = " + path;
                break;
            case FileObserver.MOVED_FROM:
                message = "event = " + "File Moved From" + "\npath = " + path;
                break;
            case FileObserver.MOVED_TO:
                message = "event = " + "File Moved To" + "path = " + path;
                break;
            case FileObserver.MOVE_SELF:
                message = "event = " + "File Moved Self" + "\npath = " + path;
                break;
        }

        Log.d("FileRecoveryObserver: ", message);

        return message;
    }

    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static String getFileExtension(String filePath) {

        final char EXTENSION_SEPARATOR = '.';
        final char UNIX_SEPARATOR = '/';
        final char WINDOWS_SEPARATOR = '\\';

        if (filePath != null) {

            int lastUnixPos = filePath.lastIndexOf(UNIX_SEPARATOR);
            int lastWindowsPos = filePath.lastIndexOf(WINDOWS_SEPARATOR);

            int extensionPos = filePath.lastIndexOf(EXTENSION_SEPARATOR);
            int lastSeparator = Math.max(lastUnixPos, lastWindowsPos);

            int index = lastSeparator > extensionPos ? -1 : extensionPos;
            if (index == -1) {
                return "";
            } else {
                return filePath.substring(index + 1);
            }
        }
        return null;
    }

    public static boolean isDesiredFileType(String filePath, TagType tagType) {
        switch (tagType) {
            case IMAGE:
                if (filePath.endsWith(".png") || filePath.endsWith(".jpg")
                        || filePath.endsWith(".jpeg") || filePath.endsWith(".gif")) {
                    return true;
                }
                break;
            case AUDIO:
                if (filePath.endsWith(".mp3") || filePath.endsWith(".aac")
                        || filePath.endsWith(".amr") || filePath.endsWith(".m4r")) {
                    return true;
                }
                break;
            case VIDEO:
                if (filePath.endsWith(".mp4") || filePath.endsWith(".flv")
                        || filePath.endsWith(".avi") || filePath.endsWith(".mkv")
                        || filePath.endsWith(".wmv") || filePath.endsWith(".webm")
                        || filePath.endsWith(".3gp") || filePath.endsWith(".dat")
                        || filePath.endsWith(".swf") || filePath.endsWith(".mov")
                        || filePath.endsWith(".vob") || filePath.endsWith(".mpg")
                        || filePath.endsWith(".mpeg") || filePath.endsWith(".mpeg1")
                        || filePath.endsWith(".mpeg2") || filePath.endsWith(".mpeg3")
                        || filePath.endsWith(".mpeg3") || filePath.endsWith(".m4v")) {
                    return true;
                }
                break;
            case DOCUMENT:
                if (filePath.endsWith(".pdf") || filePath.endsWith(".txt")
                        || filePath.endsWith(".doc") || filePath.endsWith(".docx")
                        || filePath.endsWith(".xls") || filePath.endsWith(".xlsx")
                        || filePath.endsWith(".ppt") || filePath.endsWith(".pptx")
                        || filePath.endsWith(".odt") || filePath.endsWith(".ini")
                        || filePath.endsWith(".java") || filePath.endsWith(".html")
                        || filePath.endsWith(".htm") || filePath.endsWith(".css")
                        || filePath.endsWith(".cpp") || filePath.endsWith(".cs")
                        || filePath.endsWith(".php") || filePath.endsWith(".xml")
                        || filePath.endsWith(".ods") || filePath.endsWith(".csv")
                        || filePath.endsWith(".db") || filePath.endsWith(".srt")
                        || filePath.endsWith(".js") || filePath.endsWith(".php")) {
                    return true;
                }
                break;
            case APK:
                if (filePath.endsWith(".apk")) {
                    return true;
                }
                break;
            case ZIP:
                if (filePath.endsWith(".zip")) {
                    return true;
                }
                break;
            case OTHER:
                if ((!isDesiredFileType(filePath, TagType.IMAGE)) && (!isDesiredFileType(filePath, TagType.AUDIO))
                        && (!isDesiredFileType(filePath, TagType.VIDEO)) && (!isDesiredFileType(filePath, TagType.DOCUMENT))
                        && (!isDesiredFileType(filePath, TagType.ZIP)) && (!isDesiredFileType(filePath, TagType.APK))) {
                    return true;
                }
        }
        return false;
    }

    public static TagType getTagType(String filePath) {
        TagType tagType = TagType.OTHER;
        if (isDesiredFileType(filePath, TagType.IMAGE)) {
            tagType = TagType.IMAGE;
        } else if (isDesiredFileType(filePath, TagType.AUDIO)) {
            tagType = TagType.AUDIO;
        } else if (isDesiredFileType(filePath, TagType.VIDEO)) {
            tagType = TagType.VIDEO;
        } else if (isDesiredFileType(filePath, TagType.DOCUMENT)) {
            tagType = TagType.DOCUMENT;
        } else if (isDesiredFileType(filePath, TagType.ZIP)) {
            tagType = TagType.ZIP;
        } else if (isDesiredFileType(filePath, TagType.APK)) {
            tagType = TagType.APK;
        } else if (isDesiredFileType(filePath, TagType.OTHER)) {
            tagType = TagType.OTHER;
        }
        return tagType;
    }

    public static Tag getFileTag(String filePath) {
        Tag tag = null;
        switch (getTagType(filePath)) {
            case ALL:
                tag = new Tag(TagType.ALL.name());
                break;
            case IMAGE:
                tag = new Tag(TagType.IMAGE.name());
                break;
            case AUDIO:
                tag = new Tag(TagType.AUDIO.name());
                break;
            case VIDEO:
                tag = new Tag(TagType.VIDEO.name());
                break;
            case DOCUMENT:
                tag = new Tag(TagType.DOCUMENT.name());
                break;
            case ZIP:
                tag = new Tag(TagType.ZIP.name());
                break;
            case APK:
                tag = new Tag(TagType.APK.name());
                break;
            case OTHER:
                tag = new Tag(TagType.OTHER.name());
                break;
        }

        return tag;
    }

    public static String convertInputStreamToString(InputStream inputStream) {

        String strInputStream = "";
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            strInputStream = sb.toString();
        } catch (Exception ex) {
            strInputStream = "";
            ex.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return strInputStream;
    }

    public static InputStream convertStringToInputStream(String text) {
        InputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(text.getBytes("UTF-8"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return inputStream;
    }
}
