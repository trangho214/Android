package model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by TrangHo on 06-10-2014.
 */
public class Post implements Serializable {
    @SerializedName("Id")
    public int id;
    @SerializedName("Title")
    public String title;
    @SerializedName("Description")
    public  String description;
    @SerializedName("CreatedDate")
    public String createdDate;
    @SerializedName("ImageURL")
    public  String imageUrl;
    @SerializedName("UserId")
    public int userId;
    @SerializedName("GroupId")
    public  int groupId;
    @SerializedName("User")
    public UserInfo userInfo;

    public Post( String title, String description, String imageUrl, int useId, int groupId)
    {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.userId = useId;
        this.groupId = groupId;
        createdDate = "";
        userInfo = null;

    }
    public Post( int id,String title, String description, String imageUrl, int useId, int groupId)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.userId = useId;
        this.groupId = groupId;
        createdDate = "";
        userInfo = null;
    }
}
