package tech.codegarage.recyclebin.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class Tag extends RealmObject {
    // If you are using GSON, field names should not be obfuscated.
    // Add either the proguard rule in proguard-rules.pro or the @SerializedName annotation.

    @PrimaryKey
    private String name;

    public Tag(String name) {
        this.name = name;
    }

    public Tag() {
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
                "name='" + name + '\'' +
                '}';
    }
}