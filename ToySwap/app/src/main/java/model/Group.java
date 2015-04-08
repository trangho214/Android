package model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TrangHo on 06-10-2014.
 */
public class Group {
    @SerializedName("Id")
    public int id;
    @SerializedName("Name")
    public String name;

    @Override
    public String toString() {
        return name;
    }
}
