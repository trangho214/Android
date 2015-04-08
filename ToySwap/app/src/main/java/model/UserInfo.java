package model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TrangHo on 06-10-2014.
 */
public class UserInfo implements Serializable{
    @SerializedName("Id")
    public int id;
    @SerializedName("UserName")
    public String userName;
    @SerializedName("Password")
    public String password;
    @SerializedName("Address")
    public String address;
    @SerializedName("Email")
    public String email;
    @SerializedName("PhoneNumber")
    public String phoneNumber;
    @SerializedName("Latitude")
    public double latitude;
    @SerializedName("Longitude")
    public double longitude;
    @SerializedName("Distance")
    public int distance;
    @SerializedName("DistrictId")
    public int districtId;
    @SerializedName("IsShowEmail")
    public  boolean isShowEmail;
    @SerializedName("IsShowPhoneNumber")
    public boolean isShowPhoneNumber;
    @SerializedName("UserImageURL")
    public String userImageURL;
    //    @SerializedName("Posts")
    public  List<Post> posts;
    public UserInfo()
    {
        posts = new ArrayList<Post>();
    }
    public UserInfo(int id, String userName, String password, String email,
                    String phoneNumber, String address, double longitude, double latitude,
                    boolean isShowEmail, boolean isShowPhoneNumber,String userImageURL, int districtId)
    {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isShowEmail = isShowEmail;
        this.isShowPhoneNumber = isShowPhoneNumber;
        this.userImageURL = userImageURL;
        this.districtId = districtId;

    }

    public UserInfo(int id, String userName, String password, String email,
                    String phoneNumber, String address,boolean isShowEmail, boolean isShowPhoneNumber,
                    String userImageURL,int districtId)
    {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.latitude = 0;
        this.longitude = 0;
        this.isShowEmail = isShowEmail;
        this.isShowPhoneNumber = isShowPhoneNumber;
        this.userImageURL = userImageURL;
        this.districtId = districtId;
    }
}
