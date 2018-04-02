package tech.codegarage.recyclebin.enumeration;

public enum TagType {
    ALL("All"), IMAGE("Image"), AUDIO("Audio"), VIDEO("Video"), DOCUMENT("Document"), ZIP("Zip"), APK("Apk"), OTHER("Other");

    String tagName;

    TagType(String tagName) {
        this.tagName = tagName;
    }
}