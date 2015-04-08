package softs.hnt.com.toyswap;

import java.util.ArrayList;
import java.util.List;

import model.City;
import model.Post;

/**
 * Created by TrangHo on 18-11-2014.
 */
public class DataClass {
    private static DataClass instance;
    private List<Post> extraSmall_Posts = new ArrayList<Post>();
    private List<Post> small_Posts = new ArrayList<Post>();
    private List<Post> medium_Posts = new ArrayList<Post>();
    private List<Post> myPosts = new ArrayList<Post>();

    private DataClass(){}

    public static DataClass getInstance()
    {
        if(instance == null)
            instance = new DataClass();
        return instance;
    }

    public List<Post> getExtraSmall_Posts() {
        return extraSmall_Posts;
    }

    public void setExtraSmall_Posts(List<Post> extraSmall_Posts) {
        this.extraSmall_Posts = extraSmall_Posts;
    }

    public List<Post> getMyPosts() {
        return myPosts;
    }

    public void setMyPosts(List<Post> myPosts) {
        this.myPosts = myPosts;
    }

    public List<Post> getSmall_Posts() {
        return small_Posts;
    }

    public void setSmall_Posts(List<Post> small_Posts) {
        this.small_Posts = small_Posts;
    }

    public List<Post> getMedium_Posts() {
        return medium_Posts;
    }
    public void setMedium_Posts(List<Post> medium_Posts) {
        this.medium_Posts = medium_Posts;
    }
}
