package softs.hnt.com.denpin;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ToggleButton;




public class MyActivity extends Activity {
    SharedPreferences sharedPreferences;
    final static String REF_KEY = "REF_KEY";
    final static String IS_SOUND_ON = "ISSOUNDON";

    ToggleButton btnSwitch;
    ImageView imgLight;
    private Camera camera;

//    TextView tvOnOff;
    private boolean isFlashOn, isSoundOn = true;
    private boolean hasFlash;
    Camera.Parameters params;
    MediaPlayer mp;
    ImageButton btnSound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d("onCreate", "onCreate");
        ActionBar actionBar = getActionBar();

        setContentView(R.layout.activity_my);

        // flash switch button
        btnSwitch = (ToggleButton) findViewById(R.id.btnToggle);
        btnSwitch.setOnClickListener(global_Click);
        btnSwitch.setBackgroundColor(Color.TRANSPARENT);
        btnSwitch.setTextColor(Color.BLACK);
        btnSwitch.setText(R.string.turn_off);
        btnSound = (ImageButton)findViewById(R.id.btnSound);
        btnSound.setOnClickListener(global_Click);
        imgLight = (ImageView)findViewById(R.id.imgLight);
        imgLight.setVisibility(View.INVISIBLE);
//        tvOnOff = (TextView)findViewById(R.id.tvOnOff);
        btnSwitch.setChecked(false);




        // First check if device is supporting flashlight or not
        hasFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!hasFlash) {
            // device doesn't support flash
            // Show alert message and close the application
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setTitle("Error");
            dialog.setMessage("Your device doesn't support flash light");
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            dialog.create();
            return;
        }

        // get the camera
      //  getCamera();

        // displaying button image


        // Switch button click event to toggle flash on/off
    }

    View.OnClickListener global_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnToggle:
                    if (isFlashOn)
                        // turn off flash
                        turnOffFlash();
                    else
                        // turn on flash
                        turnOnFlash();

                    break;
                case R.id.btnSound:
                    if (isSoundOn)
                        soundOff();
                    else
                        soundOn();
                    break;
            }
        }
    };

    private void soundOn()
    {
        isSoundOn = true;
        btnSound.setBackgroundResource(R.drawable.sound_on_icon);
    }
    private void soundOff()
    {
        isSoundOn = false;
        btnSound.setBackgroundResource(R.drawable.sound_mute_icon);
    }


    // Get the camera
    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
                Log.e("Camera Error. Failed to Open. Error: ", e.getMessage());
            }
        }
    }


    // Turning On flash
    private void turnOnFlash() {
        getCamera();
        if (!isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            // play sound
            playSound();
            imgLight.setVisibility(View.VISIBLE);
            btnSwitch.setText(R.string.turn_on);
            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
//            tvOnOff.setText(R.string.turn_off);
            isFlashOn = true;


            // changing button/switch image
        }

    }


    // Turning Off flash
    private void turnOffFlash() {
        if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            playSound();
            imgLight.setVisibility(View.INVISIBLE);
            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;
//            tvOnOff.setText(R.string.turn_on);
            camera.release();
            camera = null;
            // changing button/switch image
        }
    }


    // Playing sound
    // will play button toggle sound on flash on / off
    private void playSound(){
        if(isSoundOn) {
            mp = MediaPlayer.create(this, R.raw.switch_sound);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    mp.release();
                }
            });
            mp.start();
        }

    }

    /*
     * Toggle switch button images
     * changing image states to on / off
     * */


    @Override
    protected void onDestroy()
    {

        super.onDestroy();
        Log.d("onDestroy", "onDestroy");
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    @Override
    protected void onPause() {

        super.onPause();
        sharedPreferences = getSharedPreferences(REF_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_SOUND_ON,isSoundOn);
        editor.commit();
        Log.d("onPause", "onPause");
        // on pause turn off the flash
//        turnOffFlash();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.d("onRestart", "onRestart");
    }

    @Override
    protected void onResume() {

        super.onResume();
        sharedPreferences = getSharedPreferences(REF_KEY,MODE_PRIVATE);

        if(sharedPreferences.contains(IS_SOUND_ON))
        {
            isSoundOn = sharedPreferences.getBoolean(IS_SOUND_ON,true);
            if(!isSoundOn)
                btnSound.setBackgroundResource(R.drawable.sound_mute_icon);
        }

        Log.d("onResume", "onResume");
        // on resume turn on the flash
//        if(!isFlashOn)
//            turnOnFlash();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("onStart", "onStart");
        // on starting the app get the camera params
      //  getCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("onStop", "onStop");
        // on stop release the camera
//        if (camera != null) {
//            camera.release();
//            camera = null;
//        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.my,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
}
