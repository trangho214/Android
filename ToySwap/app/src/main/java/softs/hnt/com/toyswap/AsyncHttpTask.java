package softs.hnt.com.toyswap;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by TrangHo on 03-11-2014.
 */
public class AsyncHttpTask extends AsyncTask<Object, String, String> {
    private ProgressDialog progressDialog;
    HttpHandler httpHandler;
    String dialogMessage;
    //TODO: do HTTP job. Make abstract medthod/class to handle result in onPostExecute
    final static String URL = "http://trangho214-001-site1.smarterasp.net/toyswapservice.svc/";

    public AsyncHttpTask(HttpHandler httpHandler)
    {
        this.httpHandler = httpHandler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(httpHandler.getContext());
        progressDialog.setMessage("Preparing...");
        progressDialog.show();
    }
    //Params in doInBackground will be pass in execute as parameters when calling execute of AsyncHttpTask
    @Override
    protected String doInBackground(Object... params) {

        UserAction userAction = (UserAction)params[0];
        dialogMessage = (String)params[params.length-1];
        publishProgress(dialogMessage);
        switch (userAction)
        {
            case createUser:
            case updateUser:
            case updatePost:
            case createPost:
                return userActionPost(userAction, params[1]);
            case forgetPassword:
            case changePassword:
            case deletePost:
            case postsByGroup:
            case getAllPlaces:
            case getAllPosts:
            case getAllGroups:
            case login:
                return userActionGet(userAction, params);
            case uploadImage:
                try {
                    return userActionUploadImage(userAction, params);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return "";
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        progressDialog.setMessage(values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        httpHandler.doOnResponse(s);
    }

    //parameter: useraction,bitmap, oldName(= DEFAULT if new post), message
    private String userActionUploadImage(UserAction action, Object... params) throws IOException {
//        http://stackoverflow.com/questions/21306720/uploading-image-from-android-to-php-server
        //if oldUrl ="DEFAULT" server will understand it is a new post.
        String URL = "http://trangho214-001-site1.smarterasp.net/Picture/Post/upload.php";
        Bitmap bitmap = (Bitmap)params[1];
        String oldUrl = (String)params[2];
        Log.d("ImageURL after handle", oldUrl);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("image",encodedImage));
        nameValuePairs.add(new BasicNameValuePair("oldName",oldUrl));
//        nameValuePairs.add(new BasicNameValuePair("oldName","image_54635b90279bb.jpg"));

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(URL);
        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpResponse response = httpclient.execute(httppost);
        InputStream inputStream = response.getEntity().getContent();
        return convertInputStreamToString(inputStream);
    }
    private String userActionPost(UserAction action, Object entity){
        Gson gson = new Gson();
        StringEntity se = null;
        try {
            se = new StringEntity(gson.toJson(entity));
            Log.d("object to json",gson.toJson(entity));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpPost request = new HttpPost(URL + action);
        // TODO: WHAT IS HEADER????????????????
        request.addHeader("content-type", "application/x-www-form-urlencoded");
        request.setEntity(se);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            HttpResponse response = httpClient.execute(request);
            InputStream inputStream = response.getEntity().getContent();
            if(inputStream!= null)
            {
                return convertInputStreamToString(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String userActionGet(UserAction action, Object... params)
    {
        String getURL = null;
        switch (params.length)
        {
            case 2:
                //get request without parameter
                getURL = URL + action;
                break;
            case 3:
                //Get request with one parameter
                getURL = URL + action + "/" + params[1];
                break;
            default:
                //get request with two parameters and more
                getURL = URL + action + "/";
                for(int i = 1; i<params.length-2; i++)
                {
                    getURL += params[i] + ",";
                }
                getURL = getURL + params[params.length-2];
                break;
        }
        HttpGet request =new  HttpGet(getURL);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpResponse response = httpClient.execute(request);
            return convertInputStreamToString(response.getEntity().getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    private  String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        Log.d("Respone ", result);
        return result;
    }
}
