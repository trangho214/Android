package utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by TrangHo on 12-03-2015.
 */
public class SharedPreferencesManager {
    static  int PRIVATE_MODE = 0;
    private static final String PROJECT_NAME = "INCOMING_SMS";
    public static String ALARM_CODE = "ALARM_CODE";

    //to get the current index in Sound list in RAW folder to find out name of sound file to set it to Button in Splash
    public static String SOUND_INDEX = "SOUND_INDEX";

    //Save path of sound to play sound when service is fired.
    public static String SOUND_PATH = "SOUND_PATH";
    public static void saveString(Context context, String saveString, String prefName)
    {
        SharedPreferences pref = context.getSharedPreferences(PROJECT_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(prefName, saveString);
        editor.commit();
    }

    public static String getString(Context context, String prefName)
    {
        SharedPreferences pref = context.getSharedPreferences(PROJECT_NAME,PRIVATE_MODE);
        return pref.getString(prefName,"default");
    }
    public static void saveInt(Context context, int value, String prefName)
    {
        SharedPreferences pref = context.getSharedPreferences(PROJECT_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(prefName, value);
        editor.commit();
    }

    public static int getInt(Context context, String prefName)
    {
        SharedPreferences pref = context.getSharedPreferences(PROJECT_NAME,PRIVATE_MODE);
        return pref.getInt(prefName,0);
    }

}
