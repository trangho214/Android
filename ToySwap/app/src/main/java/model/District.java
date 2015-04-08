package model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TrangHo on 16-01-2015.
 */
public class District {
    @SerializedName("Id")
    public int id;
    @SerializedName("Name")
    public String name;

    public District(int id, String name)
    {
        this.id = id;
        this.name= name;
    }

    @Override
    public String toString() {
        return name;
    }
}
