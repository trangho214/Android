package softs.hnt.com.toyswap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import model.Post;

/**
 * Created by TrangHo on 24-10-2014.
 */
public class CommonMethodClass {
    static String[] specialSet = new String[]{"Ø","ø","Å","å","Æ","æ"};
    static String[] normalSet = new String[]{"OE","oe","AA","aa","AE","ae"};

    static Bitmap decodeFile(String filePath) {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 1024;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;

        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return  BitmapFactory.decodeFile(filePath, o2);

    }

    static void shrinkBitmap(String file, int width, int height, Bitmap bitmap, ImageView imageView){
        //Reduce the size of bitmap
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file, bmpFactoryOptions);

        int heightRatio = (int)Math.ceil(bmpFactoryOptions.outHeight/(float)height);
        int widthRatio = (int)Math.ceil(bmpFactoryOptions.outWidth/(float)width);

        if (heightRatio > 1 || widthRatio > 1)
        {
            if (heightRatio > widthRatio)
            {
                bmpFactoryOptions.inSampleSize = heightRatio;
            } else {
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }

        bmpFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
        imageView.setImageBitmap(bitmap);
    }

  public static List<Post> getPosts(String json)
    {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Post>>() {}.getType();
        List<Post> posts = gson.fromJson(json, listType);
        return posts;

    }
    static  String specialLetterToNormal(String input)
    {
        for (int i = 0; i<specialSet.length; i++)
        {
            if (input.contains(specialSet[i])) {
                input = input.replace(specialSet[i], normalSet[i]);
            }
        }
        return input;
    }

    static String normalLetterToSpecial(String input)
    {
        for(int i = 0;i<normalSet.length; i++ )
        {
            if(input.contains(normalSet[i]))
            {
                input = input.replace(normalSet[i],specialSet[i]);
            }
        }
        return input;
    }
    static String getText(Activity activity,int id)
    {
        return  ((EditText)activity.findViewById(id)).getText().toString();
    }
}
