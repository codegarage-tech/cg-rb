package tech.codegarage.recyclebin.model;

import java.util.ArrayList;

import tech.codegarage.recyclebin.model.realm.Tag;

/**
 * @author Md. Rashadul Alam
 * Email: rashed.droid@gmail.com
 */
public class DataTag extends BaseResponse {

    private ArrayList<Tag> data = new ArrayList<>();

    public DataTag(ArrayList<Tag> data) {
        this.data = data;
    }

    public ArrayList<Tag> getData() {
        return data;
    }

    public void setData(ArrayList<Tag> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "data=" + data +
                '}';
    }
}