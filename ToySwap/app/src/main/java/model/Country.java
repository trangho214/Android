package model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TrangHo on 16-01-2015.
 */
public class Country {
    @SerializedName("Id")
    public String id;
    @SerializedName("Name")
    public String name;
    @SerializedName("States")
    public List<State> states;

//
//
//    public Country(String id, String name)
//    {
//        this.id = id;
//        this.name = name;
//        states = new ArrayList<State>();
//    }

}
