package tech.codegarage.recyclebin.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Tag extends RealmObject {
    // If you are using GSON, field names should not be obfuscated.
    // Add either the proguard rule in proguard-rules.pro or the @SerializedName annotation.

    private long id;

    @PrimaryKey
    private String name;

    public Tag(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tag() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}