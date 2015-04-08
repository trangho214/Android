package softs.hnt.com.toyswap;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import model.UserInfo;

/**
 * Created by TrangHo on 25-10-2014.
 */
public class SharePrefManager {
    // Shared pref mode
   static int PRIVATE_MODE = 0;
    // Sharedpref file name
    private static final String PREF_NAME = "TOYSWAP";
    public static final String USER = "USER";
    public static final String FAVOURITE = "FAVOURITE";
    private static final String DEFAULT = "DEFAULT";
    public static final String FIRSTlAUNCH = "FIRSTLAUNCH";
    private static final String PLACEFILE = "place.txt";
    public static final String GROUP = "GROUP";

    public SharePrefManager(){};

    public static boolean isFirstLaunch(Context context)
    {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        return pref.getBoolean(FIRSTlAUNCH,true);
    }

    public static void saveFirstLaunch(Context context)
    {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(FIRSTlAUNCH, false);
        editor.commit();
    }

      public static void savePlaceToFile(Context context, String text)
    {
        try {
            FileOutputStream fo = context.openFileOutput(PLACEFILE, PRIVATE_MODE);
            fo.write(text.getBytes());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPlaceFromFile(Context context)
    {
        try {
            FileInputStream fi = context.openFileInput(PLACEFILE);
            InputStreamReader isr = new InputStreamReader(fi);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine())!= null)
            {
                sb = sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //after user sucessed with login or signup. saveUser is called to save the userinfo into SharedPreferences.
//    public static void saveUser(Context context, UserInfo user)
//    {
//        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
//        SharedPreferences.Editor edit = pref.edit();
//        Gson gson = new Gson();
//        String jsonUser = gson.toJson(user, UserInfo.class);
//        edit.putString(USER, jsonUser);
//        edit.commit();
//    }

    //getUser is called in Splash to check if the user have login before or not.
    public static UserInfo getUser(Context context)
    {
        Gson gson = new Gson();
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        if(pref.contains(USER)) {
            String json = pref.getString(USER, DEFAULT);
            return (UserInfo) gson.fromJson(json, UserInfo.class);
        }
        return null;
    }

    public static String getFromSharePref(Context context, String prefKey)
    {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
            return  pref.getString(prefKey, null);
    }
    //save the pref key fx USER/FAVOURITE/GROUP... in main SharedPreferences.
    public static void saveToSharePref(Context context, String prefKey, String jsonString)
    {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(prefKey,jsonString);
        edit.commit();

    }

    //remove the pref key fx USER/FAVOURITE/GROUP... in main SharedPreferences.
    public static void removeFromSharePref(Context context, String prefKey)
    {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharedPreferences.Editor edit = pref.edit();
        edit.remove(prefKey);
        edit.commit();
    }


    public static void SaveFavourite(Context context,List<Integer> favourties)
    {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharedPreferences.Editor edit = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(favourties,List.class);
        edit.putString(FAVOURITE, json);
        edit.commit();
    }

    public static List<Integer> getFavourite(Context context)
    {
        Gson gson = new Gson();
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        if(pref.contains(FAVOURITE))
        {
            String json = pref.getString(FAVOURITE,DEFAULT);
            if(json.equals(DEFAULT))
                return null;
            else
                return gson.fromJson(json,List.class);
        }
        return null;
    }

}
