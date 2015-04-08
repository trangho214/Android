package model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TrangHo on 02-11-2014.
 */
public class Result {
    @SerializedName("WasSuccessful")
    public int wasSuccessful;
    @SerializedName("Exception")
    public String exception;
    public Result(){};
    public Result(int wasSuccessful, String exception)
    {
        this.wasSuccessful = wasSuccessful;
        this.exception = exception;
    }

    public int getWasSuccessful() {
        return wasSuccessful;
    }

    public String getException() {
        return exception;
    }

    public void setException(String message) {
        this.exception = message;
    }

    public void setWasSuccessful(int wasSuccessful) {
        this.wasSuccessful = wasSuccessful;
    }
}
