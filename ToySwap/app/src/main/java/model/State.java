package model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TrangHo on 16-01-2015.
 */
public class State {
    @SerializedName("Id")
    public int id;
    @SerializedName("Name")
    public String name;
    @SerializedName("Cities")
    public List<City> cities;

    public State(int id, String name)
    {
        this.id = id;
        this.name = name;
        cities = new ArrayList<City>();
    }
}
