package tech.codegarage.recyclebin.model;

import android.util.Log;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import tech.codegarage.recyclebin.util.MD5Manager;

public class Tag extends RealmObject {
    // If you are using GSON, field names should not be obfuscated.
    // Add either the proguard rule in proguard-rules.pro or the @SerializedName annotation.

    private String name;
    @PrimaryKey
    private String md5;

    public Tag(String name) {
        this.name = name;
        this.md5 = MD5Manager.getMD5ByString(toString());
    }

    public Tag() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", md5='" + this.hashCode() + '\'' +
                '}';
    }
}