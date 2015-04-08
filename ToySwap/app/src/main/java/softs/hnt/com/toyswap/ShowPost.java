package softs.hnt.com.toyswap;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.androidquery.AQuery;
import java.util.Locale;

import model.Post;
import model.UserInfo;

/**
 * Created by TrangHo on 16-10-2014.
 */
public class ShowPost extends CommonActivity {
    ImageView ivImage, ivEmail, ivAddress, ivPhone;
    TextView txtTitle, txtDescription;
    TextView txtOwner,txtAddress, txtPhone, txtEmail;
    int[] ivIds = new int[]{R.id.ivImage_ShowPost, R.id.ivIconAddress_ShowPost, R.id.ivIconPhone_ShowPost, R.id.ivIconEmail_ShowPost};
    ImageView[] ivs = new ImageView[ivIds.length];
    //   CompoundInfo cpInfoAddress, cpInfoEmail, cpInfoPhone;
    AQuery aq;
    Post post;
    UserInfo currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        post = (Post)getIntent().getSerializableExtra("postItem");

        super.onCreate(savedInstanceState);
        currentUser = SharePrefManager.getUser(this);
        aq = new AQuery(this);
        setContentView(R.layout.show_post);
        ivImage = (ImageView)findViewById(R.id.ivImage_ShowPost);
        ivImage.setOnClickListener(onClick);
        txtTitle = (TextView)findViewById(R.id.txtTitle_ShowPost);
        txtDescription = (TextView)findViewById(R.id.txtDescription_ShowPost);
        txtAddress = (TextView)findViewById(R.id.txtContentAddress_ShowPost);
        txtPhone = (TextView)findViewById(R.id.txtContentPhone_ShowPost);
        txtEmail = (TextView)findViewById(R.id.txtContentEmail_ShowPost);
        txtOwner =(TextView)findViewById(R.id.txtPostOwner_ShowPost);
        ivInit();
        txtTitle.setText(post.title);
        txtDescription.setText(post.description);
        txtOwner.setText("Owner: " + post.userInfo.userName);
        txtEmail.setText(":  "+post.userInfo.email);
        txtAddress.setText(":  "+post.userInfo.address);
        txtPhone.setText(":  "+post.userInfo.phoneNumber);
        aq.id(R.id.ivImage_ShowPost).image(post.imageUrl);
        Location locationA = new Location("point A");
        if(currentUser!= null) {
            locationA.setLatitude(currentUser.latitude);
            locationA.setLongitude(currentUser.longitude);
        }
        Location locationB = new Location("point B");
        locationB.setLatitude(post.userInfo.latitude);
        locationB.setLongitude(post.userInfo.longitude);
        float distance = locationA.distanceTo(locationB) ;
    }

    public double CalculationByDistance(double initialLat, double initialLong, double finalLat, double finalLong){
    /*PRE: All the input values are in radians!*/

        double latDiff = finalLat - initialLat;
        double longDiff = finalLong - initialLong;
        double earthRadius = 6371; //In Km if you want the distance in km

        double distance = 2*earthRadius*Math.asin(Math.sqrt(Math.pow(Math.sin(latDiff/2.0),2)+Math.cos(initialLat)*Math.cos(finalLat)*Math.pow(Math.sin(longDiff/2),2)));

        return distance;

    }
    private void ivInit()
    {
        for(int i = 0; i< ivIds.length; i++)
        {
            findViewById(ivIds[i]).setOnClickListener(onClick);
        }
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId())
            {
                case R.id.ivIconEmail_ShowPost:
                    sendEmail();
                    break;
                case R.id.ivIconAddress_ShowPost:
                    startMap();
                    break;
                case R.id.ivIconPhone_ShowPost:
                    makeCall();
                    break;
            }
        }
    };
    private void startMap()
    {
        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f(%s)&daddr=%f,%f (%s)", currentUser.latitude, currentUser.longitude, "Home Sweet Home", post.userInfo.latitude, post.userInfo.longitude, "Where the party is at");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        mapIntent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        try
        {
            startActivity(mapIntent);
        }
        catch(ActivityNotFoundException ex)
        {
            try
            {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(unrestrictedIntent);
            }
            catch(ActivityNotFoundException innerEx)
            {
                Toast.makeText(ShowPost.this, "Please install a maps application", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void sendEmail()
    {
        String[] TO = {post.userInfo.email};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, post.title);
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ShowPost.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void makeCall()
    {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setPackage("com.android.phone");
        intent.setData(Uri.parse("tel:23909396"));// + post.userInfo.phoneNumber.toString().trim()));
        try{
            startActivity(intent);
        }
        catch (ActivityNotFoundException ex)
        {
            Toast.makeText(ShowPost.this, "Call faild, please try again later", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
