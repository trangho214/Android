package model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TrangHo on 16-01-2015.
 */
public class City {
    @SerializedName("Id")
    public int id;
    @SerializedName("Name")
    public String name;
    @SerializedName("Districts")
    public List<District> districts;

    public City(int id, String name)
    {

        this.id = id;
        this.name = name;
        districts = new ArrayList<District>();
    }

    @Override
    public String toString() {
        return name;
    }
}
