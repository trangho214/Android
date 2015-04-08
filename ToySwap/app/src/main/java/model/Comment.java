package model;

/**
 * Created by TrangHo on 07-11-2014.
 */
public class Comment {
    int id;
    int userId;
    int postId;
    String comment;
    boolean isPrivate;
    String createdDate;

    public Comment(int id, int userId, int postId, String comment, boolean isPrivate, String createdDate)
    {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.comment = comment;
        this.isPrivate = isPrivate;
        this.createdDate = createdDate;
    }

    public Comment(int userId, int postId, String comment, boolean isPrivate)
    {
        this.id = 0;
        this.userId = userId;
        this.postId = postId;
        this.comment = comment;
        this.isPrivate = isPrivate;
        this.createdDate = "0";
    }

}
