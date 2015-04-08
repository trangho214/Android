package softs.hnt.com.toyswap;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import model.Group;
import model.Post;
import model.Result;

/**
 * Created by TrangHo on 23-10-2014.
 */
public class CreatePost extends CommonActivity  {
    public final static int PICKUP_CODE =1;
    public final static int TAKEAPHOTO_CODE =2;

    int currentUserId;
    String imageUrl ="DEFAULT", title, description; int groupId;
    ImageView ivPhoto;
    Bitmap bitmap;
    RelativeLayout loRule;
    Button btnRule, btnPickup, btnTakeAPhoto, btnCreate;
    TextView txtCaution;
    EditText edTitle, edDescription;
    Spinner spGroup;
//    List<String> groupList;
//    ArrayAdapter<String> arrayAdapter;

    List<Group> groupList;
    ArrayAdapter<Group> arrayAdapter;
    Gson gson;

    //Used for editing post.
    Post currentPost;


    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUserId = getIntent().getIntExtra("currentUserId",0);
        printLog("On Create", "Oncreate");
        setContentView(R.layout.create_post);

        init();
        spinnerWork();
        if(currentUserId == 0)
        {
            currentPost = (Post)getIntent().getSerializableExtra("postItem");
            if(currentPost!= null)
            {
                btnCreate.setText("Save");
                ifEdit();
            }
        }

    }

    private void init()
    {
        gson = new Gson();
        loRule = (RelativeLayout)findViewById(R.id.loRule_CreatePost);
        ivPhoto = (ImageView)findViewById(R.id.ivPhoto_CreatePost);
        btnRule = (Button)findViewById(R.id.bntRule_CreatePost);
        btnPickup = (Button)findViewById(R.id.btnPickup_CreatePost);
        btnCreate = (Button)findViewById(R.id.btnCreate_CreatePost);
        btnTakeAPhoto = (Button)findViewById(R.id.btnTakeAPhoto_CreatePost);
        edTitle = (EditText)findViewById(R.id.edTitle_CreatePost);
        edDescription = (EditText)findViewById(R.id.edDescription_CreatePost);
        txtCaution = (TextView)findViewById(R.id.txtCaution_CreatePost);
        edDescription.setOnTouchListener(onTouch);
        ivPhoto.setOnClickListener(onClick);
        btnPickup.setOnClickListener(onClick);
        btnTakeAPhoto.setOnClickListener(onClick);
        btnRule.setOnClickListener(onClick);
        btnCreate.setOnClickListener(onClick);
    }
    private void ifEdit()
    {
        edTitle.setText(currentPost.title);
        edDescription.setText(currentPost.description);
        spGroup.setSelection(currentPost.groupId-1);
        imageUrl = currentPost.imageUrl;
        currentUserId = currentPost.userId;
        AQuery aq = new AQuery(CreatePost.this);
        aq.id(R.id.ivPhoto_CreatePost).image(currentPost.imageUrl);
    }
    private void collectData()
    {
        title = edTitle.getText().toString();
        description = edDescription.getText().toString();
        groupId = (int)spGroup.getSelectedItemId() +1;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //Inflate question_menu to actionbar.
        getMenuInflater().inflate(R.menu.question_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_question:
                openCloseRuleLayout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    View.OnTouchListener onTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (v.getId() == R.id.edDescription_CreatePost) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
            }
            return false;
        }
    };

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.bntRule_CreatePost:
                    openCloseRuleLayout();
                    break;
                case R.id.btnPickup_CreatePost:
                    startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),PICKUP_CODE);
                    break;
                case R.id.btnTakeAPhoto_CreatePost:
                    dispatchTakePictureIntent();
                    break;
                case R.id.btnCreate_CreatePost:
                  //  Post post = new Post("Post from App","one year old","http://trangho214-001-site1.smarterasp.net/Picture/Post/image_54636f15ad154.jpg",30,1);
                    collectData();
                    if(currentPost ==null) {
                        Post post = new Post(title, description, imageUrl, currentUserId, groupId);
                        doPostJob(UserAction.createPost, post).execute();
                    }
                    else {
                        Post post = new Post(currentPost.id,title, description, imageUrl, currentUserId, groupId);
                        doPostJob(UserAction.updatePost, post).execute();
                    }

            }
        }
    };

    private HttpHandler doPostJob(UserAction userAction, Post post)
    {
        return  new HttpHandler(CreatePost.this,userAction, post,"Uploading...")
        {
            @Override
            public void doOnResponse(String result)
            {
             //   txtCaution.setText(result);
                Result r = gson.fromJson(result, Result.class);
                if(r.wasSuccessful ==1)
                {
                    Intent i = new Intent(CreatePost.this, DrawerActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            }
        };
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                //send a url with to tell to system where it should save the photo, after taken.
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, TAKEAPHOTO_CODE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case TAKEAPHOTO_CODE:
                    galleryAddPic();
                    break;
                case PICKUP_CODE:
                    if (data != null) {
                        //       Uri selectedUri = data.getData();
                        getRealPathFromURIVoid(data.getData());
                        Log.d("URI file", data.getData().toString());
                    }
                    break;
            }
        }
    }

    private String handleImageUrl(String imageUrl)
    {
        //TODO: handle imageURL in case of editing image (both editing Post and overwriting image.)
        if(!imageUrl.equals("DEFAULT"))
        {
            int index = imageUrl.lastIndexOf("/");
            return imageUrl.substring(index +1, imageUrl.length());

        }
        return imageUrl;
    }
    public void getRealPathFromURIVoid( Uri contentUri) {
        Cursor cursor = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        try {
            cursor = getContentResolver().query(contentUri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (cursor.moveToFirst()) {
                //it holds image.
                mCurrentPhotoPath = cursor.getString(column_index);
                    bitmap =CommonMethodClass.decodeFile(mCurrentPhotoPath);
                ivPhoto.setImageBitmap(bitmap);
              //  CommonMethodClass.shrinkBitmap(mCurrentPhotoPath, 480, 800, bitmap, ivPhoto);
                new HttpHandler(this,UserAction.uploadImage, bitmap,handleImageUrl(imageUrl), "Image uploading"){
                    @Override
                    public void doOnResponse(String result) {
                     //   txtCaution.setText(result);
                        imageUrl = result;
                    }
                }.execute();
            }
        } catch (Exception ex) {
            Toast.makeText(this, "cannot load this image", Toast.LENGTH_LONG).show();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
        bitmap =CommonMethodClass.decodeFile(mCurrentPhotoPath);
        ivPhoto.setImageBitmap(bitmap);
        new HttpHandler(CreatePost.this, UserAction.uploadImage, bitmap,handleImageUrl(imageUrl),"Image uploading"){
            @Override
            public void doOnResponse(String result)
            {
              imageUrl = result;
              //  txtCaution.setText(result);
            }
        }.execute();
//        CommonMethodClass.shrinkBitmap(mCurrentPhotoPath, 320, 640, bitmap, ivPhoto);
//        iGetAddress.getAddress(new String[]{f.getName(), String.valueOf(f.length()), printDate1, mCurrentPhotoPath});
    }
    //not finished yet
    File image;
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
        //the photo should be named by username + count()

        //EDIT THE BELOW LINE LATER WITH COUNT FROM SharePreferences
        //     String imageFileName = "JPEG_" + timeStamp + ++imageCount + "_";
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        // Save a file: path for use with ACTION_VIEW intents
         image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    //where people can see the rule of creating a post.
    private void openCloseRuleLayout()
    {
        if(loRule.getVisibility()== View.GONE)
            loRule.setVisibility(View.VISIBLE);
        else
            loRule.setVisibility(View.GONE);
    }

    private void spinnerWork()
    {
        spGroup = (Spinner)findViewById(R.id.spGroup_CreatePost);
        String groupString = SharePrefManager.getFromSharePref(CreatePost.this, SharePrefManager.GROUP);
        Type groupListType = new TypeToken<List<Group>>(){}.getType();
        groupList = new ArrayList<Group>();
        groupList = gson.fromJson(groupString, groupListType);
        arrayAdapter = new ArrayAdapter<Group>(this, android.R.layout.simple_spinner_item, groupList);

//        groupList = Arrays.asList(getResources().getStringArray(R.array.age_group));
//        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,groupList);
       // arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGroup.setAdapter(arrayAdapter);

    }
    private void printLog(String tag, String message)
    {
        Log.d("Print " +tag, message);
    }

    @Override
    protected void onResume() {
        super.onResume();
        printLog("Create Post onResume", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        printLog("Create Post onPause", "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        printLog("Create Post onDestroy", "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        printLog("Create Post onRestart", "onRestart");
    }
}

