package tech.codegarage.recyclebin.model.realm;

import java.io.FileInputStream;
import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class RecoveryFileInfo extends RealmObject{

    private String originFileName;
    @PrimaryKey
    private String originFilePath;
    private String originMd5File;
    @Ignore
    private FileInputStream originFileInputStream;
    private int originFileLength = -1;
    private String recoveryMD5FileName;
    private String recoveryFilePath;
    private Date deletedDate;
    private boolean isFile = true;
    private RealmList<Tag> tags;

    public RecoveryFileInfo() {
    }

    public RecoveryFileInfo(String originFileName, String originFilePath, String originMd5File, FileInputStream originFileInputStream, int originFileLength, String recoveryMD5FileName, String recoveryFilePath, Date deletedDate, boolean isFile, RealmList<Tag> tags) {
        this.originFileName = originFileName;
        this.originFilePath = originFilePath;
        this.originMd5File = originMd5File;
        this.originFileInputStream = originFileInputStream;
        this.originFileLength = originFileLength;
        this.recoveryMD5FileName = recoveryMD5FileName;
        this.recoveryFilePath = recoveryFilePath;
        this.deletedDate = deletedDate;
        this.isFile = isFile;
        this.tags = tags;
    }

    public String getOriginFileName() {
        return originFileName;
    }

    public void setOriginFileName(String originFileName) {
        this.originFileName = originFileName;
    }

    public String getOriginFilePath() {
        return originFilePath;
    }

    public void setOriginFilePath(String originFilePath) {
        this.originFilePath = originFilePath;
    }

    public String getOriginMd5File() {
        return originMd5File;
    }

    public void setOriginMd5File(String originMd5File) {
        this.originMd5File = originMd5File;
    }

    public FileInputStream getOriginFileInputStream() {
        return originFileInputStream;
    }

    public void setOriginFileInputStream(FileInputStream originFileInputStream) {
        this.originFileInputStream = originFileInputStream;
    }

    public int getOriginFileLength() {
        return originFileLength;
    }

    public void setOriginFileLength(int originFileLength) {
        this.originFileLength = originFileLength;
    }

    public String getRecoveryMD5FileName() {
        return recoveryMD5FileName;
    }

    public void setRecoveryMD5FileName(String recoveryMD5FileName) {
        this.recoveryMD5FileName = recoveryMD5FileName;
    }

    public String getRecoveryFilePath() {
        return recoveryFilePath;
    }

    public void setRecoveryFilePath(String recoveryFilePath) {
        this.recoveryFilePath = recoveryFilePath;
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    public boolean isFile() {
        return isFile;
    }

    public void setFile(boolean file) {
        isFile = file;
    }

    public RealmList<Tag> getTags() {
        return tags;
    }

    public void setTags(RealmList<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "RecoveryFileInfo{" +
                "originFileName='" + originFileName + '\'' +
                ", originFilePath='" + originFilePath + '\'' +
                ", originMd5File='" + originMd5File + '\'' +
                ", originFileInputStream=" + originFileInputStream +
                ", originFileLength=" + originFileLength +
                ", recoveryMD5FileName='" + recoveryMD5FileName + '\'' +
                ", recoveryFilePath='" + recoveryFilePath + '\'' +
                ", deletedDate=" + deletedDate +
                ", isFile=" + isFile +
                ", tags=" + tags +
                '}';
    }
}