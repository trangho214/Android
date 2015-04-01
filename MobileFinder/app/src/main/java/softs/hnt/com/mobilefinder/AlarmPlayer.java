package softs.hnt.com.mobilefinder;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import utility.SharedPreferencesManager;

/**
 * Created by TrangHo on 11-03-2015.
 */
public class AlarmPlayer extends ActionBarActivity {
    MediaPlayer mp;
    EditText edCode;
    Button btnStop;
    String alarm_code;
    String sound_path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_player);
        init();
    }

    private void init()
    {
        btnStop = (Button)findViewById(R.id.btnStop);
        btnStop.setOnClickListener(onClick);
        edCode = (EditText)findViewById(R.id.edCode_AlarmPlayer);
        alarm_code = SharedPreferencesManager.getString(this, SharedPreferencesManager.ALARM_CODE);
        sound_path = SharedPreferencesManager.getString(this, SharedPreferencesManager.SOUND_PATH);
        if(sound_path.equals("default"))
        {
            sound_path = "android.resource://"+getPackageName()+"/raw/alarm1";
        }
        Uri uri = Uri.parse(sound_path);
        mp = new MediaPlayer();
        try {
            mp.setDataSource(this,uri);
            mp.prepare();
            mp.setLooping(true);
            mp.setVolume(1.0f, 1.0f);
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //mp = MediaPlayer.create(this, R.raw.alarm4);
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String code = edCode.getText().toString();
            Toast toast;
            if(code == null || code =="" || code.equals(""))
            {
                toast = Toast.makeText(AlarmPlayer.this,getResources().getString(R.string.enter_code_to_stop),Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            else
            {
                if(!alarm_code.equals(code))
                {
                    toast = Toast.makeText(AlarmPlayer.this,getResources().getString(R.string.code_is_wrong),Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else
                {
                    mp.stop();
                    mp.release();
                    finish();
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        if(mp!= null)
        {
            mp.release();
            mp = null;
        }
        super.onDestroy();
    }
}
